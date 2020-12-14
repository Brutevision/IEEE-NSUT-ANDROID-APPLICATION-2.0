package com.dev.ieee_nsut.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnInfoDetailsFragmentInteractionListener;
import com.dev.ieee_nsut.interfaces.OnInfoFragmentInteractionListener;
import com.dev.ieee_nsut.models.Information;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 *
 */
public class InformationDetailsFragment extends Fragment {
    public static final String TAG = "InformationDetailsFrag";
    private Information mInfo;
    private OnInfoDetailsFragmentInteractionListener mListener;
    private String transitionName;
    public InformationDetailsFragment() {
        // Required empty public constructor
    }

    public static InformationDetailsFragment newInstance(Information info, String transitionName){
        InformationDetailsFragment fragment = new InformationDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ContentUtils.INFO_KEY, info);
        bundle.putString(ContentUtils.TRANSITION_NAME, transitionName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mInfo = bundle.getParcelable(ContentUtils.INFO_KEY);
            transitionName = bundle.getString(ContentUtils.TRANSITION_NAME);
        }
        postponeEnterTransition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.info_details_toolbar);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        AppBarLayout appBarLayout = view.findViewById(R.id.info_details_appbar);

        final CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.info_details_collapsing_toolbar);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                if(scrollRange + verticalOffset == 0){
                    toolbarLayout.setTitle(mInfo.getTitle());
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });

        final ImageView imageView = view.findViewById(R.id.info_details_image_view);
        TextView titleTextView = view.findViewById(R.id.info_details_title_text_view);
        TextView descriptionTextView = view.findViewById(R.id.info_details_description_text_view);
        TextView dateTextView = view.findViewById(R.id.date_text_view);

        CoordinatorLayout layout = view.findViewById(R.id.info_details_root_layout);
        ViewCompat.setTransitionName(layout, transitionName);

        AppBarLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ContentUtils.convertDpToPixel(100, getActivity()));
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|
                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED|
                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
        );

        /*
         problem due to using AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED flag
         so setting padding equal to toolbar height
         */
        descriptionTextView.setPadding(0,0,0,ContentUtils.convertDpToPixel(56, getActivity()));

        if(mInfo.getImageList() != null){
            String firstImageUrl = mInfo.getImageList().get(0);
            if(firstImageUrl != null &&
                    (!firstImageUrl.equals("null"))) {
                imageView.setVisibility(View.VISIBLE);

                RequestBuilder<Drawable> requestBuilder = Glide.with(getActivity())
                        .load(mInfo.getImageList().get(0));

                requestBuilder.listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        imageView.setVisibility(View.GONE);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        startPostponedEnterTransition();
                        return true;
                    }
                }).into(imageView);

            } else {
                startPostponedEnterTransition();
                imageView.setVisibility(View.GONE);
                toolbarLayout.setLayoutParams(layoutParams);
                toolbarLayout.setTitle(mInfo.getTitle());
            }
        } else {
            startPostponedEnterTransition();
            toolbarLayout.setLayoutParams(layoutParams);
            imageView.setVisibility(View.GONE);
            toolbarLayout.setTitle(mInfo.getTitle());
        }

        titleTextView.setText(mInfo.getTitle());
        String date = mInfo.getDate();
        if(date != null && !date.equals("null")){
            dateTextView.setText("" + mInfo.getDate());
        } else {
            dateTextView.setVisibility(View.GONE);
        }
        String description = mInfo.getDescription();
        description = ContentUtils.formatString(description);
        descriptionTextView.setText(description);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onInfoDetailsInteraction(imageView, mInfo);
                }

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnInfoDetailsFragmentInteractionListener){
            mListener = (OnInfoDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+" must implement " +
                    OnInfoFragmentInteractionListener.class.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}