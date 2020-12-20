package com.dev.ieee_nsut.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.adapter.FeedPagerAdapter;
import com.dev.ieee_nsut.adapter.HomeItemsAdapter;
import com.dev.ieee_nsut.custom.MyRecyclerDivider;
import com.dev.ieee_nsut.custom.ZoomOutPageTransformer;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnRecyclerViewItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.dev.ieee_nsut.interfaces.OnHomeFragmentInteractionListener;
import com.dev.ieee_nsut.models.Feed;
import com.dev.ieee_nsut.models.HomeItems;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private static String TAG = "HomeFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<HomeItems> mHomeItemsArrayList;

    private ViewPager mFeedViewPager;
    private FeedPagerAdapter mFeedAdapter;
    private ArrayList<Feed> mFeedArrayList;
    private CircleIndicator mCircleIndicator;

    private CollectionReference mFeedReference;
    private ListenerRegistration mListenerRegistration;


    private OnHomeFragmentInteractionListener listener;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.home_toolbar);
        AppBarLayout appBarLayout = view.findViewById(R.id.home_app_bar);
        final CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.home_collapsing_toolbar);



        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                if(scrollRange + verticalOffset == 0){
                    toolbarLayout.setTitle("IEEE NSUT");
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });

        mFeedReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_FEEDS);

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mRecyclerView = view.findViewById(R.id.item_recycler_view);
        mHomeItemsArrayList = new ArrayList<>();
        mHomeItemsArrayList.add(new HomeItems(ContentUtils.EVENTS, R.drawable.ic_events, R.color.xplore_color));
        mHomeItemsArrayList.add(new HomeItems(ContentUtils.PROJECTS, R.drawable.ic_project,R.color.colorRed));
        mHomeItemsArrayList.add(new HomeItems(ContentUtils.ACHIEVEMENTS, R.drawable.ic_achieve, R.color.colorRed));
        mHomeItemsArrayList.add(new HomeItems(ContentUtils.ABOUT_IEEE, R.drawable.ic_ieeenew1, R.color.colorRed));
        mHomeItemsArrayList.add(new HomeItems(ContentUtils.IEEE_RESOURCES, R.drawable.ic_resources, R.color.colorAccent));
        HomeItemsAdapter mHomeItemsAdapter = new HomeItemsAdapter(getActivity(), mHomeItemsArrayList, this);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mHomeItemsAdapter);
//        mRecyclerView.setPadding(0,0,0, ContentUtils.convertDpToPixel(56f,getContext()));
        mRecyclerView.addItemDecoration(new MyRecyclerDivider(getActivity(), DividerItemDecoration.VERTICAL));

        mFeedViewPager = view.findViewById(R.id.home_slider_view_pager);
        mFeedArrayList = new ArrayList<>();
        mFeedAdapter = new FeedPagerAdapter(getChildFragmentManager(), mFeedArrayList);
        mFeedViewPager.setAdapter(mFeedAdapter);
        mFeedViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mCircleIndicator =view.findViewById(R.id.circle_indicator);
        mCircleIndicator.setViewPager(mFeedViewPager);
        mFeedAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
        mCircleIndicator.setVisibility(View.GONE);
        attachFeedSnapshotListener();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnHomeFragmentInteractionListener){
            listener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement" +
                    "OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void attachFeedSnapshotListener(){
        mListenerRegistration = mFeedReference.orderBy("id", Query.Direction.DESCENDING).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w(TAG, "attachFeedSnapshotListener: error: "+e);
                        } else {
                            if(documentSnapshots != null && !documentSnapshots.isEmpty()){
                                mFeedArrayList.clear();
                                for(DocumentSnapshot documentSnapshot : documentSnapshots){
                                    Feed feed = documentSnapshot.toObject(Feed.class);
                                    mFeedArrayList.add(feed);
                                }
//                        Collections.reverse(mFeedArrayList);
                                mFeedAdapter.notifyDataSetChanged();
                                ContentUtils.syncIndicatorWithViewPager(mFeedViewPager, mCircleIndicator);
                                mRecyclerView.setPadding(0,0,0,0);
                            }
                        }
                    }
                });
    }

    private void detachFeedSnapshotListener(){
        if(mListenerRegistration != null){
            mListenerRegistration.remove();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        detachFeedSnapshotListener();
    }

    @Override
    public void onItemClicked(View view) {
        if(listener != null){
            int position = mRecyclerView.getChildAdapterPosition(view);
            HomeItems homeItems = mHomeItemsArrayList.get(position);
            listener.onHomeFragmentInteraction(homeItems.getTitle());
        }
    }
}




/*

 */