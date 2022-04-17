package gp_b_5.shapetd.backend;

/**
 * Created by zachary on 10/7/17.
 */

public interface TileMapTouchListener {
    /**
     * This method is called when a touch Down action occurs.
     *
     * <p> Note that the location of the down location is not provided, but it is provided when the touch ends
     * and a call is made to onTouchUp.
     */

    public void onTouchDown ();

    /**
     * This method is called when a touch Up action occurs.
     *
     * <p>
     * Index values are 0 based.
     * Values are between 0 and NumSquaresAlongCanvas-1.
     *
     * @param downX int - x value of the down action square
     * @param downY int - y value of the down action square
     * @param upX int - x value of the up action square
     * @param upY int - y value of the up action square
     * @return void
     */

    public void onTouchUp (int downX, int downY, int upX, int upY);

    /**
     * This method is called when a touch Up action occurs and the time between down and up
     * exceeds the Android long press timeout value.
     *
     * <p>
     * Index values are 0 based.
     * Values are between 0 and NumSquaresAlongCanvas-1.
     *
     * @param downX int - x value of the down action square
     * @param downY int - y value of the down action square
     * @param upX int - x value of the up action square
     * @param upY int - y value of the up action square
     * @return void
     */

    public void onLongTouchUp (int downX, int downY, int upX, int upY);


}
