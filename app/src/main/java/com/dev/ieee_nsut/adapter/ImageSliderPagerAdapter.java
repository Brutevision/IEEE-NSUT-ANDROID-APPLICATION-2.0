package com.dev.ieee_nsut.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dev.ieee_nsut.fragments.ImageSliderFragment;

import java.util.ArrayList;

/**
 *
 */

public class ImageSliderPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ImageSliderPagerAdapter";
    private ArrayList<String> mArrayList;


    public ImageSliderPagerAdapter(FragmentManager fm, ArrayList<String> arrayList) {
        super(fm);
        this.mArrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageSliderFragment.newInstance(mArrayList.get(position));
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        return super.instantiateItem(container, position);

    }
}
