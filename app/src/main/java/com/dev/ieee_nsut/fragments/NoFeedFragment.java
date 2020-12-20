package com.dev.ieee_nsut.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.ieee_nsut.R;

public class NoFeedFragment extends Fragment {


    public NoFeedFragment() {
        // Required empty public constructor
    }

    public static NoFeedFragment newInstance() {
        return new NoFeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_feed, container, false);
    }
}