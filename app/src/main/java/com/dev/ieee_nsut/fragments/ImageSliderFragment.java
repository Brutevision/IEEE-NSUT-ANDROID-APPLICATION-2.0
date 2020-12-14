package com.dev.ieee_nsut.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.ieee_nsut.R;

/**
 *
 */
public class ImageSliderFragment extends Fragment {
    private static String IMAGE_URL_KEY = "image_key";
    private String mImageUrl;


    public ImageSliderFragment() {
        // Required empty public constructor
    }

    public static ImageSliderFragment newInstance(String imageUrl){
        ImageSliderFragment fragment = new ImageSliderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL_KEY, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mImageUrl = bundle.getString(IMAGE_URL_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return LayoutInflater.from(getActivity()).inflate(R.layout.image_slider_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imageView = view.findViewById(R.id.slider_image_view);
        final ProgressBar progressBar = view.findViewById(R.id.slider_progress_bar);

        RequestBuilder<Drawable> requestBuilder = Glide.with(getActivity())
                .load(mImageUrl);

        requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.place);
                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
    }
}