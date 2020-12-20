package com.dev.ieee_nsut.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dev.ieee_nsut.fragments.FeedFragment;
import com.dev.ieee_nsut.fragments.NoFeedFragment;
import com.dev.ieee_nsut.models.Feed;

import java.util.ArrayList;

/**
 * 
 */

public class FeedPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "FeedPagerAdapter";
    private ArrayList<Feed> mFeedArrayList;

    public FeedPagerAdapter(FragmentManager fm, ArrayList<Feed> feedArrayList) {
        super(fm);
        this.mFeedArrayList = feedArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == mFeedArrayList.size()){
            return NoFeedFragment.newInstance();
        } else {
            return FeedFragment.newInstance(mFeedArrayList.get(position));
        }
    }

    @Override
    public int getCount() {

        return mFeedArrayList.isEmpty()? 1 : mFeedArrayList.size()+1;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
