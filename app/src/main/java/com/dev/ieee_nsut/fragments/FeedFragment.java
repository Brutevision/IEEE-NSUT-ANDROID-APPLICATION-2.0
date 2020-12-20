package com.dev.ieee_nsut.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnHomeSliderInteractionListener;
import com.dev.ieee_nsut.models.Feed;

public class FeedFragment extends Fragment {

    private Feed mFeed;
    private String TAG = "FeedFragment";
    private OnHomeSliderInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnHomeSliderInteractionListener){
            mListener = (OnHomeSliderInteractionListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static FeedFragment newInstance(Feed feed) {

        Bundle args = new Bundle();
        args.putParcelable(ContentUtils.FEED_KEY, feed);
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeed = getArguments().getParcelable(ContentUtils.FEED_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imageView = view.findViewById(R.id.home_slider_image_view);
        final ProgressBar progressBar = view.findViewById(R.id.home_slider_progress);
        final TextView registerTextView = view.findViewById(R.id.register_text_view);
        final String imageUrl = mFeed.getFeedImageUrl();
        final String registerUrl = mFeed.getRegisterUrl();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Image Clicked");
                mListener.onHomeSliderInteraction(imageView, mFeed);
            }
        });
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "button Clicked");
                Uri registerUri = Uri.parse(registerUrl);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, registerUri);
                if (webIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(webIntent);
                } else {
                    Toast.makeText(getActivity(), "Error opening the link", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(registerUrl == null || registerUrl.equals("")){
            registerTextView.setVisibility(View.GONE);
        }

        if(imageUrl != null) {
            RequestBuilder<Drawable> requestBuilder = Glide.with(getActivity())
                    .load(imageUrl);

            requestBuilder.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);
        }
    }
}