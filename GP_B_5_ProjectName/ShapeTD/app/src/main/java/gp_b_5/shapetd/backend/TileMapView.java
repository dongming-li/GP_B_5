package gp_b_5.shapetd.backend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import gp_b_5.shapetd.R;

/**
 * Created by zachary on 10/7/17.
 */

public class TileMapView extends PanZoomView{

    static public final int DefaultNumSquaresAlongSide = 34;
    static public final int DefaultNumSquaresAlongCanvas = 100;
    static public final float CanvasSizeMultiplier = (float) DefaultNumSquaresAlongCanvas / (float) DefaultNumSquaresAlongSide;
    static public final int NumRedBlueTypes = 4;     // Used with simple squares demo; types: blank, red, blue


    // Variables that control placement and translation of the canvas.
    // Initial values are for debugging on 480 x 320 screen. They are reset in onDrawPz.
    private float mMaxCanvasWidth = 34;
    private float mMaxCanvasHeight = 34;
    private float mHalfMaxCanvasWidth = 17;
    private float mHalfMaxCanvasHeight = 17;
    private float mOriginOffsetX = 320;
    private float mOriginOffsetY = 320;
    private float mSquareWidth = 32;         // use float for more accurate placement
    private float mSquareHeight = 32;

    private Rect  mDestRect;
    private RectF mDestRectF;

    static private final int [] mUnselectedImageIds = {R.drawable.generic_cell, R.drawable.generic_path, R.drawable.tower_circle,
            R.drawable.tower_hexagon, R.drawable.tower_pentagon, R.drawable.towe_triangle, R.drawable.generic_enemy};
    static private final int [] mSelectedImageIds = {R.drawable.generic_cell, R.drawable.generic_path, R.drawable.tower_circle,
            R.drawable.tower_hexagon, R.drawable.tower_pentagon, R.drawable.towe_triangle, R.drawable.generic_enemy};

    private Bitmap [] mBitmaps;              // WATCH OUT! Do not set this variable to null.
    // setupToDraw method sets this variable and that method is called
    // from a constructor.

    private int [] [] mGrid;                 // 0 .. NumRedBlueTypes-1
    private int [] [] mGridSelect;           // 0 = normal; 1 = selected

    private Bitmap [] mSelectedBitmaps;

    /**
     * Constructors for the view.
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TileMapView (Context context) {
        super (context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TileMapView (Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TileMapView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

/**
 */
// Properties

/* Property NumSquaresAlongCanvas */
    /**
     * This variable holds the value of the NumSquaresAlongCanvas property.
     */

    private int pNumSquaresAlongCanvas = DefaultNumSquaresAlongCanvas;

    /**
     * Get the number of squares to be displayed on the canvas. The canvas shows all the squares on the game
     * board and can be panned and zoomed.
     *
     * @return int
     */

    public int getNumSquaresAlongCanvas ()
    {
        //if (pNumSquaresAlongCanvas == null) {}
        return pNumSquaresAlongCanvas;
    } // end getNumSquaresAlongCanvas

    /**
     * Set the number of squares to be displayed on the canvas. The canvas shows all the squares on the game
     *
     * @param newValue int
     */

    public void setNumSquaresAlongCanvas (int newValue)
    {
        pNumSquaresAlongCanvas = newValue;
    } // end setNumSquaresAlongCanvas
/* end Property NumSquaresAlongCanvas */

/* Property NumSquaresAlongSide */
    /**
     * This variable holds the value of the NumSquaresAlongSide property.
     */

    private int pNumSquaresAlongSide = DefaultNumSquaresAlongSide;

    /**
     * Get the number of squares to be displayed along the side of the view.
     * The number is actually the number of squares to display along the shortest dimension (width, height).
     * If the view is wider than it is tall, squares will be sized to fit the width.
     *
     * @return int
     */

    public int getNumSquaresAlongSide ()
    {
        //if (pNumSquaresAlongSide == null) {}
        return pNumSquaresAlongSide;
    } // end getNumSquaresAlongSide

    /**
     * Set the number of squares to be displayed along the side of the view.
     * Set the value of the NumSquaresAlongSide property.
     *
     * @param newValue int
     */

    public void setNumSquaresAlongSide (int newValue)
    {
        pNumSquaresAlongSide = newValue;
    } // end setNumSquaresAlongSide
/* end Property NumSquaresAlongSide */

/* Property TouchListener */
    /**
     * This variable holds the value of the TouchListener property.
     */

    protected TileMapTouchListener pTouchListener;

    /**
     * Get the object that is listening to touches on this view.
     *
     * @return GameBoardTouchListener
     */

    public TileMapTouchListener getTouchListener ()
    {
        //if (pTouchListener == null) {}
        return pTouchListener;
    } // end getTouchListener

    /**
     * Set the object that is listening to touches on this view.
     *
     * @param newValue GameBoardTouchListener
     */

    public void setTouchListener (TileMapTouchListener newValue)
    {
        pTouchListener = newValue;
    } // end setTouchListener
/* end Property TouchListener */

/**
 */
// Methods

    /**
     * Clear all selections in the grid.
     *
     * @return int - number selected before clearing
     */

    public int clearSelections ()
    {
        int count = 0;
        for (int j = 0; j < pNumSquaresAlongCanvas; j++) {
            for (int i = 0; i < pNumSquaresAlongCanvas; i++) {
                if (mGridSelect [j][i] > 0) {
                    mGridSelect [j][i] = 0;
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Draw squares in the visible portion of the view and the area outside view, as determined
     * by the square counts (see getNumSquaresAlongSide and getNumSquaresAlongCanvas).
     * Squares are all the same size. The size is chosen so that the shortest dimension (width, height) holds
     * the numbers specified for the side.
     *
     * <p> The canvas object should already be set up for drawing. That means that all translations and scaling
     * operations have already been done.
     *
     * <p> The bitmaps to be displayed come from arrays stored in mUnselectedImageIds and mSelectedImageIds.
     * The grid being displayed is in mGrid, which holds and index number into the image id arrays.
     *
     * @param canvas Canvas
     * @return void
     */

    public void drawOnCanvas (Canvas canvas) {

        float fx, fy;

        Paint paint = new Paint();

        // Get up the bitmaps to be displayed. Get the grid.
        Bitmap [] bitmaps = getBitmapsArray ();        // also could do "bitmaps = mBitmaps".
        Bitmap [] selectedBitmaps = mSelectedBitmaps;
        int [] [] grid = getGridArray ();

        //
        // Draw squares to fill the grid.
        //
        RectF dest1 = mDestRectF;
        float dx = 0, dy = 0;
        for (int j = 0; j < pNumSquaresAlongCanvas; j++) {
            dx = 0;
            for (int i = 0; i < pNumSquaresAlongCanvas; i++) {
                int bindex = grid [j][i];
                boolean isSelected = (mGridSelect [j][i] > 0);

                Bitmap b1 = isSelected ? selectedBitmaps [bindex]
                        : bitmaps [bindex];
                dest1.offsetTo (dx, dy);
                canvas.drawBitmap (b1, null, dest1, paint);

 /*               if (AppConfig.DEBUG)
                    if (j == 9) {
                        int dxi = (int) Math.round (dx);
                        int dyi = (int) Math.round (dy);
                        Log.d (Constants.LOG_NAME, "Square i-j: " + i + "-" + j + " at x, y : " + dxi + " " + dyi);
                    }*/
                dx = dx + mSquareWidth;
            }
            dy = dy + mSquareHeight;
        }

/*        if (AppConfig.DEBUG) {
            // Draw a circle at the focus point so it's clear if scaling is working.
            // Do this last so it shows on top of everything else.
            fx = mFocusX;
            fy = mFocusY;
            if (mScaleDetector.isInProgress()) paint.setColor (Color.WHITE);
            else paint.setColor (Color.YELLOW);
            canvas.drawCircle (fx, fy, 2, paint);
        }*/

        //Log.d ("Multitouch", "Focus " + mFocusX + " " + mFocusY + " " + fx + " " + fy);
    }

    /**
     * Get an array of bitmaps, constructed using decodeResource on the image ids in mUnselectedImageIds.
     * After calling this method, both mBitmaps and mSelectedBitmaps are ready to use.
     *
     * @return Bitmap []
     */

    Bitmap [] getBitmapsArray () {
        // Set up the bitmaps and grid for the red and blue squares.
        if (mBitmaps == null) {
            mBitmaps = new Bitmap [NumRedBlueTypes];
            mSelectedBitmaps = new Bitmap [NumRedBlueTypes];
            for (int j = 0; j < NumRedBlueTypes; j++) {
                int rid = mUnselectedImageIds [j];
                Bitmap b1 = BitmapFactory.decodeResource (mContext.getResources(), rid);
                mBitmaps [j] = b1;
                int rid2 = mSelectedImageIds [j];
                Bitmap b2 = BitmapFactory.decodeResource (mContext.getResources(), rid2);
                mSelectedBitmaps [j] = b2;
            }
        }
        return mBitmaps;
    }

    /**
     * Get 2-d grid of integers that indicate which bitmap is displayed at that point.
     *
     * @return int [] []
     */

    int [] [] getGridArray () {
        if (mGrid == null) {
            mGrid = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
            mGridSelect = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
            for (int i = 0; i < pNumSquaresAlongCanvas; i++)
                for (int j = 0; j < pNumSquaresAlongCanvas; j++) {
                    int index = 0;

                    mGrid [i][j] = index;
                    mGridSelect [i] [j] = 0;

                }
        }
        return mGrid;
    }

    /**
     * Return the value of the grid at the point indicated.
     * The result is -1 if the point is not valid.
     *
     * The result is a value between 0 and the max index value minus 1.
     *
     * @param x int - 1..numColumns
     * @param y int - 1..numRows
     * @return int - 0 .. maxIndex
     */

    public int gridValue (int x, int y)
    {
        int ix = x - 1;
        int iy = y - 1;
        if ((ix < 0) || (ix >= pNumSquaresAlongCanvas)) return -1;
        if ((iy < 0) || (iy >= pNumSquaresAlongCanvas)) return -1;
        return mGrid [iy] [ix];
    } // end gridValue

    /**
     * Return the true if the grid element is selected.
     *
     * @param x int - 1..numColumns
     * @param y int - 1..numRows
     * @return boolean
     */

    public boolean isSelected (int x, int y)
    {
        int ix = x - 1;
        int iy = y - 1;
        if ((ix < 0) || (ix >= pNumSquaresAlongCanvas)) return false;
        if ((iy < 0) || (iy >= pNumSquaresAlongCanvas)) return false;
        return (mGridSelect [iy] [ix] > 0);
    } // end gridValue

    /**
     * onDrawPz
     */

    @Override public void onDrawPz(Canvas canvas) {

        canvas.save();

        // Get the width and height of the view.
        int viewH = getHeight (), viewW = getWidth ();

        boolean isLandscape = (viewW > viewH);
        float shortestWidth = isLandscape ? viewH : viewW;

        // Set width and height to be used for the squares.
        mSquareWidth  = (float) shortestWidth / (float) pNumSquaresAlongSide;
        mSquareHeight = (float) shortestWidth / (float) pNumSquaresAlongSide;

        float numSquaresAlongX = isLandscape ? (viewW / mSquareWidth) : pNumSquaresAlongSide;
        float numSquaresAlongY = isLandscape ? pNumSquaresAlongSide   : (viewH / mSquareHeight);

        // We start out knowing how many squares will be displayed
        // along a side and how many along the whole canvas.
        // The canvas is centered in the view so half of what
        // remains to be displayed can be used to calculate the
        // origin offset values.
        mMaxCanvasWidth  = pNumSquaresAlongCanvas * mSquareWidth;
        mMaxCanvasHeight = pNumSquaresAlongCanvas * mSquareHeight;
        mHalfMaxCanvasWidth  = mMaxCanvasWidth / 2.0f;
        mHalfMaxCanvasHeight = mMaxCanvasHeight / 2.0f;

        float totalOffscreenSquaresX = pNumSquaresAlongCanvas - numSquaresAlongX;
        if (totalOffscreenSquaresX < 0f) totalOffscreenSquaresX = 0f;
        float totalOffscreenSquaresY = pNumSquaresAlongCanvas - numSquaresAlongY;
        if (totalOffscreenSquaresY < 0f) totalOffscreenSquaresY = 0f;
        mOriginOffsetX = totalOffscreenSquaresX / 2.0f * mSquareWidth;
        mOriginOffsetY = totalOffscreenSquaresY / 2.0f * mSquareHeight;

        // The canvas is translated by the amount we have
        // scrolled and the standard amount to move the origin
        // of the canvas up and left so the region is centered
        // in the view. (Note: mPosX and mPosY are defined in PanZoomView.)
        float x = 0, y = 0;
        mPosX0 = mOriginOffsetX;
        mPosY0 = mOriginOffsetY;
        x = mPosX - mPosX0;
        y = mPosY - mPosY0;
        canvas.translate (x, y);

        /*if (AppConfig.DEBUG) Log.d (Constants.LOG_NAME, "canvas.translate " + " x: " + x + " y: " + y);*/

        // The focus point for zooming is the center of the
        // displayable region. That point is defined by half
        // the canvas width and height.
        mFocusX = mHalfMaxCanvasWidth;
        mFocusY = mHalfMaxCanvasHeight;
        canvas.scale (mScaleFactor, mScaleFactor, mFocusX, mFocusY);

        // Set up the grid  and grid selection variables.
        if (mGrid == null)
            mGrid = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
        if (mGridSelect == null)
            mGridSelect = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];

        // Set up the rectangles we use for drawing, if not done already.
        // Set width and height to be used for the rectangle to be drawn.
        Rect dest = mDestRect;
        if (dest == null) {
            int ih = (int) Math.floor (mSquareHeight);
            int iw = (int) Math.floor (mSquareWidth);
            dest = new Rect (0, 0, iw, ih);
            mDestRect = dest;
        }
        RectF dest1 = mDestRectF;
        if (dest1 == null) {
            dest1 = new RectF ();
            dest1.left = dest.left; dest1.top = dest.top;
            dest1.right = mSquareWidth; dest1.bottom = mSquareHeight;
            mDestRectF = dest1;
        }

        // Do the drawing operation for the view.
        drawOnCanvas (canvas);

        canvas.restore();

    }

    /**
     * This method is called when supportsOnTouchDown is true.
     * It can be used when subclasses want to perform a simple action when a touch occurs.
     * <p> The default action is to do nothing. Subclasses should override this method.
     *
     * @param downX float - x value of the down action
     * @param downY float - y value of the down action
     * @return void
     */

    public void onTouchDown (float downX, float downY) {
        super.onTouchDown (downX, downY);

        TileMapTouchListener listener = getTouchListener ();
        if (listener == null) return;
        listener.onTouchDown ();
    }

    /**
     * This method is called when supportsOnTouchUp is true and panning and scaling are not in progress.
     * It can be used when subclasses want to support a simple touch action in addition to scaling.
     * <p> The default action is call the TouchListener set for the view.
     * Note that the y arguments precede the x arguments, just as they do in the Sst2k layer.
     *
     * @param downX float - x value of the down action
     * @param downY float - y value of the down action
     * @param upX float - x value of the up action
     * @param upY float - x value of the up action
     * @return void
     */

    public void onTouchUp (float downX, float downY, float upX, float upY) {
        /*if (AppConfig.DEBUG) Log.d (Constants.LOG_NAME, " upX: " + upX + " upY: " + upY);*/

        // Save the time in case we are going to call the listener.
        // We need to know the difference between a long press and a regular press.
        long nDownTime = mDownTime;
        mDownTime = 0l;

        // Figure out if this was a long press.
        long nUpTime = System.nanoTime ();
        boolean isLongPress = (nUpTime - nDownTime) > mLongPressTimeOut;

        //Log.d (Constants.LOG_UI, "Check long press up,down,timeout: " + nUpTime + ", " + nDownTime
        //                         + ", " + mLongPressTimeOut + " diff: " + (nUpTime - nDownTime));

        // Convert view coordinates to canvas coordinates and, eventually,
        // to index values for the cells being displayed.

        // If the scale is 1, it is easy. When the scale is one, we know that exactly
        // pNumSquaresAlongSide are on the screen. The rest are off the screen.
        // Since that has already been accounted for, in the origin offset values,
        // simply add the x or y arg values to the origin offsets and divide
        // by the square width to get index values.
        float fx = (mOriginOffsetX + upX - mPosX) / mSquareWidth;
        float fy = (mOriginOffsetY + upY - mPosY) / mSquareHeight;
        float dfx = (mOriginOffsetX * mScaleFactor + downX - mPosX) / mSquareWidth;
        float dfy = (mOriginOffsetY * mScaleFactor + downY - mPosY) / mSquareHeight;
        float fx2 = fx, dfx2 = dfx;
        float fy2 = fy, dfy2 = dfy;

        if (mScaleFactor == 1.0f) {
            // Use the four float values already computed. Convert them to int values. See below.
        } else {
            // If scaling is on, we have to adjust the up and down points by the scale factor and
            // we have to account for the points that do not show up in the visible view.

            // 1. Figure out how many squares are showing on the screen. We need that to convert view
            // coordinates to index numbers.
            float scaledSqWidth = (mScaleFactor * mSquareWidth);
            float scaledSqHeight = (mScaleFactor * mSquareHeight);

            // 2 (new method)
            // We zoom around the center point of the canvas.
            // First we need to figure out where the point is, in view coordinates. Look at the unzoomed values we have saved.
            // The focus point never changes as we zoom. Use its x and y values to determine how many squares are showing on the screen.
            // If you know the number of squares visible left and above the focus point, you can figure out how many squares are
            // offscreen. (Remember we know the focus point is half of the canvas width and height.)
            float vFocusX = mFocusX - mOriginOffsetX;
            float vFocusY = mFocusY - mOriginOffsetY;
            float numSquaresToLeft = vFocusX / scaledSqWidth;
            float numSquaresAbove  = vFocusY / scaledSqHeight;
            float numSquaresToCenter = (float) pNumSquaresAlongCanvas / 2f;
            float numSquaresOffscreenLeft  = numSquaresToCenter - numSquaresToLeft;
            float numSquaresOffscreenAbove = numSquaresToCenter - numSquaresAbove;
            float vsqX = (upX - mPosX) / scaledSqWidth;
            float vsqY = (upY - mPosY) / scaledSqHeight;
            fx2 = vsqX + numSquaresOffscreenLeft;
            fy2 = vsqY + numSquaresOffscreenAbove;
            dfx2 = (downX - mPosX) / scaledSqWidth + numSquaresOffscreenLeft;
            dfy2 = (downY - mPosY) / scaledSqHeight + numSquaresOffscreenAbove;

        }

        float x2 = 0, y2 = 0;
        x2 = mPosX - mPosX0;
        y2 = mPosY - mPosY0;


        // We want integer index values to call the listener.
        // Use floor to round down. Do not need to add one since origin offset includes whole square.
        //
        int sUpX = (int) Math.floor (fx2) + 1;
        int sUpY = (int) Math.floor (fy2) + 1;
        int sDownX = (int) Math.floor (dfx2) + 1;
        int sDownY = (int) Math.floor (dfy2) + 1;
        //int sUpX2 = (int) Math.floor (fx) + 1;
        //int sUpY2 = (int) Math.floor (fy) + 1;



        // Next check to see if there is a listener for these events.
        // If there is not, there is nothing else to do.
        TileMapTouchListener listener = getTouchListener ();
        if (listener == null) return;

        // Tell the listener about the Up event. Again note: sector x is really y on screen
        if(isLongPress){
            listener.onLongTouchUp (sDownX, sDownY, sUpX, sUpY);
        }
        else listener.onTouchUp (sDownX, sDownY, sUpX, sUpY);
    }

    /**
     * Return the resource id of the sample image. Note that this class always returns 0, indicating
     * that there is no sample drawable.
     *
     * @return int
     */

    public int sampleDrawableId () {
        return 0;
    }

    /**
     * Set the value of the grid at the point indicated.
     * If the point (x,y) is not valid, nothing changes on the grid, and no error is signaled.
     *
     * <p> A call should be made to the invalidate method after calling this method.
     *
     * @param x int - 1..numColumns
     * @param y int - 1..numRows
     * @param value int - value between 0 and max index.
     * @return void
     */

    public void setGridValue (int x, int y, int value)
    {
        int ix = x - 1;
        int iy = y - 1;
        if ((ix < 0) || (ix >= pNumSquaresAlongCanvas)) return;
        if ((iy < 0) || (iy >= pNumSquaresAlongCanvas)) return;
        mGrid [iy][ix] = value;
    }

    /**
     * This method performs whatever set up is necessary to do drawing.
     * <p> It is called by the constructor. That means you should be careful about what initialization you do
     * for instance variables. They are not set when this code runs.
     *
     * @return void
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setupToDraw (Context context, AttributeSet attrs, int defStyle) {
        super.setupToDraw (context, attrs, defStyle);

        getBitmapsArray ();

   /*
   // Set up the bitmaps and grid for the red and blue squares.
   if (mBitmaps == null) {
      mBitmaps = new Bitmap [NumRedBlueTypes];
      mSelectedBitmaps = new Bitmap [NumRedBlueTypes];
      for (int j = 0; j < NumRedBlueTypes; j++) {
          int rid = mUnselectedImageIds [j];
          Bitmap b1 = BitmapFactory.decodeResource (mContext.getResources(), rid);
          mBitmaps [j] = b1;
          int rid2 = mSelectedImageIds [j];
          Bitmap b2 = BitmapFactory.decodeResource (mContext.getResources(), rid2);
          mSelectedBitmaps [j] = b2;
      }
   }
   */

    }

    /**
     * Return true if this view wants to receive onTouchDown calls.
     * Those calls are made only if this method returns true. (See setupToDraw.)
     *
     * @return boolean
     */

    public boolean supportsOnTouchDown () {
        return true;
    }

    /**
     * Return true if this view wants to receive onTouchUp calls.
     * Those calls are made only if this method returns true
     * and if scaling is not in progress.
     *
     * @return boolean
     */

    public boolean supportsOnTouchUp () {
        return true;
    }

    /**
     * Return true if panning is supported.
     *
     * @return boolean
     */

    public boolean supportsPan () {
        return true;
    }

    /**
     * Return true if scaling is done around the focus point of the pinch.
     *
     * @return boolean
     */

    public boolean supportsScaleAtFocusPoint () {
        return false;
    }

    /**
     * Return true if pinch zooming is supported.
     *
     * @return boolean
     */

    public boolean supportsZoom () {
        return true;
    }

    /**
     * Change the selection state of the square at the point given.
     *
     * <p> A call should be made to the invalidate method after calling this method.
     *
     * @param x int - 1..numColumns
     * @param y int - 1..numRows
     * @return int - the new selection state, where 1 means selected.
     */

    public int toggleSelection (int x, int y)
    {
        int ix = x - 1;
        int iy = y - 1;
        if ((ix < 0) || (ix >= pNumSquaresAlongCanvas)) return 0;
        if ((iy < 0) || (iy >= pNumSquaresAlongCanvas)) return 0;
        int oldValue = mGridSelect [iy] [ix];
        int newValue = (oldValue == 0) ? 1 : 0;
        mGridSelect [iy][ix] = newValue;
        return newValue;
    }

    /**
     * Set all the values on the grid being displayed in the view.
     *
     * <p> The grid argument is a two dimension array, with the first index being the x (column) index
     * and the second, the y (row) index.
     * The grid given is assumed to be the correct size for the view. No check is made.
     *
     * <p> A call should be made to the invalidate method after calling this method.
     *
     * @param grid [][]
     * @return void
     */

    public void updateGrid (int grid [][])
    {
        // Set up the grid  and grid selection variables.

        if (mGrid == null) mGrid = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
        if (mGridSelect == null) mGridSelect = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
/*8
        if (mGridNew == null){
            mGridNew = new Cell [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
            for (int i = 0; i < pNumSquaresAlongCanvas; i++){
                for (int j = 0; j<pNumSquaresAlongCanvas; j++){
                    mGridNew[j][i] = new Cell(0, i, j);
                }
            }
        }
        for (int i = 0; i < pNumSquaresAlongCanvas; i++)
        //if (mGridSelect == null) mGridSelect = new int [pNumSquaresAlongCanvas] [pNumSquaresAlongCanvas];
        **/

        // Set values of internal grid.
        for (int y = 0; y < pNumSquaresAlongCanvas; y++) {
            for (int x = 0; x < pNumSquaresAlongCanvas; x++) {

                mGrid [y][x] = grid [y][x];

                //mGridNew [y][x] = new Cell(grid[y][x].getTerrainID(), x, y);

            }
        }
    }

   /** public Cell getCell(int x, int y){
        int ix = x - 1;
        int iy = y - 1;
        if ((ix < 0) || (ix >= pNumSquaresAlongCanvas)) return null;
        if ((iy < 0) || (iy >= pNumSquaresAlongCanvas)) return null;
        return mGridNew [iy] [ix];

    }**/
}
