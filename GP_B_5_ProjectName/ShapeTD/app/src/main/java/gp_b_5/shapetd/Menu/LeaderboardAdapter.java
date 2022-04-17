package gp_b_5.shapetd.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gp_b_5.shapetd.Login_Classes.User;
import gp_b_5.shapetd.R;

/**
 * Created by Joseph Nunez on 10/1/2017.
 */

public class LeaderboardAdapter extends BaseAdapter
{
    private Context         mContext;
    private LayoutInflater  mInflater;
    private ArrayList<User> mUsers;

    public LeaderboardAdapter(Context context, ArrayList<User> users)
    {
        this.mContext = context;
        this.mUsers = users;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = mInflater.inflate(R.layout.activity_leaderboards, parent, false);

        return rowView;
    }
}
