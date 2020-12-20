package com.dev.ieee_nsut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.models.Feed;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ShowFeedImageActivity extends AppCompatActivity {

    private static final String TAG = ShowFeedImageActivity.class.getSimpleName();
    private ObjectAnimator arrowAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feed_image);
        final Intent intent = getIntent();
        Feed feed = intent.getParcelableExtra(ContentUtils.FEED_KEY);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ImageView imageView = findViewById(R.id.show_feed_image_view);
        final ProgressBar progressBar = findViewById(R.id.show_feed_progress);

        ConstraintLayout layout = findViewById(R.id.feed_bottom_sheet);
        final FrameLayout foregroundLayout = findViewById(R.id.foreground_frame_layout);
        TextView feedDetailsTextView = findViewById(R.id.feed_details_text_view);
        TextView feedTitleTextView = findViewById(R.id.feed_title);
        TextView registerTextView = findViewById(R.id.register_text_view);
        final ImageView arrowImageView = findViewById(R.id.arrow_image_view);
        arrowAnimator = ObjectAnimator.ofFloat(arrowImageView, "translationY", 0f, -50f, 0f);
        arrowAnimator.setInterpolator(new OvershootInterpolator());
        arrowAnimator.setDuration(1000);
        arrowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        arrowAnimator.start();
        feedTitleTextView.setText(feed.getFeedTitle());
        feedDetailsTextView.setText(feed.getFeedDetails());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        final String registerUrl = feed.getRegisterUrl();
        if(registerUrl == null || registerUrl.equals("")){
            registerTextView.setVisibility(View.GONE);
        } else {
            registerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri registerUri = Uri.parse(registerUrl);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, registerUri);
                    if(webIntent.resolveActivity(getPackageManager()) != null){
                        startActivity(webIntent);
                    }
                }
            });
        }

        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(layout);
        bottomSheetBehavior.setPeekHeight(ContentUtils.convertDpToPixel(60, this));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    if(arrowAnimator.isRunning()){
                        arrowAnimator.end();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                foregroundLayout.setAlpha(slideOffset);
            }
        });

        RequestBuilder<Drawable> requestBuilder = Glide.with(this)
                .load(feed.getFeedImageUrl());

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return false;
            }
        }).into(imageView);
    }
}