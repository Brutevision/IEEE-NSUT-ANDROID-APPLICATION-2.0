package com.dev.ieee_nsut.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.adapter.InformationItemAdapter;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.helpers.EndlessRecyclerViewScrollListener;
import com.dev.ieee_nsut.interfaces.OnInfoFragmentInteractionListener;
import com.dev.ieee_nsut.interfaces.OnRecyclerViewItemClickListener;
import com.dev.ieee_nsut.interfaces.OnSharedElementClickListener;
import com.dev.ieee_nsut.models.Information;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.dev.ieee_nsut.helpers.ContentUtils.INFO_KEY;

// GlideApp functionalities are different.
public class InformationFragment extends Fragment implements OnRecyclerViewItemClickListener,
        OnSharedElementClickListener {

    private static final String TAG = "InformationFragment";
    private RecyclerView mInfoRecyclerView;
    private InformationItemAdapter mInfoAdapter;
    private ArrayList<Information> mInfoArrayList;
    private ProgressBar mProgressBar;
    private CardView mLoadMoreProgress;
    private TextView mNoInfoTextView;

    private OnInfoFragmentInteractionListener mListener;
    private String mInfoType;

    private EventListener<QuerySnapshot> mEventListener;

    private static final int NO_OF_INFO_TO_FETCH = 10;
    private boolean isMoreDataAvailable = true;

    private float mLastItemId;

    private CollectionReference mCollectionReference;

    private ListenerRegistration mListenerRegistration;

    private EndlessRecyclerViewScrollListener mScrollListener;

    private boolean isFetchingDataFirstTime = true;

    public InformationFragment() {
        // Required empty public constructor
    }

    public static InformationFragment newInstance(String infoType) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(INFO_KEY, infoType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mInfoType = bundle.getString(INFO_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    // next onViewCreated class
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Toolbar toolbar = view.findViewById(R.id.information_toolbar);
        toolbar.setTitle(mInfoType);

        AppBarLayout mAppBarLayout = view.findViewById(R.id.info_appbar);
        mProgressBar = view.findViewById(R.id.information_progress_bar);
        mLoadMoreProgress = view.findViewById(R.id.load_more_progress);
        mNoInfoTextView = view.findViewById(R.id.no_info_text_view);
        mNoInfoTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mLoadMoreProgress.setVisibility(View.INVISIBLE);

        ImageView appbarImageView = view.findViewById(R.id.app_bar_image_view);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        switch (mInfoType) {
            case ContentUtils.EVENTS:
                //appbarImageView.setImageResource(R.drawable.events);
                mCollectionReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_EVENTS);
                break;
            case ContentUtils.ACHIEVEMENTS:
                //appbarImageView.setImageResource(R.drawable.events);
                mCollectionReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_ACHIEVEMENTS);
                break;
            case ContentUtils.PROJECTS:
                //appbarImageView.setImageResource(R.drawable.events);
                mCollectionReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_PROJECTS);
                break;
        }

        if (mInfoArrayList != null) {
            isFetchingDataFirstTime = false;
            mAppBarLayout.setExpanded(false);

        } else {
            mInfoArrayList = new ArrayList<>();

        }

        mInfoRecyclerView = view.findViewById(R.id.information_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mInfoRecyclerView.setLayoutManager(layoutManager);
        mInfoAdapter = new InformationItemAdapter(getActivity(), mInfoArrayList, this);
        mInfoRecyclerView.setAdapter(mInfoAdapter);
        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (isMoreDataAvailable) {
                    fetchMoreData();
                }
            }
        };
        mInfoRecyclerView.addOnScrollListener(mScrollListener);
        if (isFetchingDataFirstTime) {
            fetchDataFirstTime();
        } else {

            /**
             * bit of a hack to remove bug which hides some part of recycler view's last item.
             * This bug is only encountered when CollapsingToolbarLayout is used with RecyclerView
             */
//            mInfoRecyclerView.setPadding(0,0,0, ContentUtils.convertDpToPixel(56f,getContext()));
        }
    }
    private void fetchDataFirstTime() {
        mProgressBar.setVisibility(View.VISIBLE);
        mInfoRecyclerView.setPadding(0,0,0,0);
        mScrollListener.resetState();
        attachCollectionSnapshotListener();
    }

    private void fetchMoreData() {
        mInfoRecyclerView.setPadding(0,0,0,0);
        mLoadMoreProgress.setVisibility(View.VISIBLE);
        mCollectionReference.orderBy("id", Query.Direction.DESCENDING)
                .limit(NO_OF_INFO_TO_FETCH)
                .startAfter(mLastItemId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                for (DocumentSnapshot documents : querySnapshot.getDocuments()) {
                                    Information information = documents.toObject(Information.class);
                                    mInfoArrayList.add(information);
                                }
                                Information info  = mInfoArrayList.get(mInfoArrayList.size()-1);
                                mLastItemId = info.getId();
                                mInfoAdapter.notifyDataSetChanged();
                            } else {
                                isMoreDataAvailable = false;
                            }
                        }
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mLoadMoreProgress.setVisibility(View.INVISIBLE);
                    }
                });

    }

    private void attachCollectionSnapshotListener(){
        if(mEventListener == null){
            mEventListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if(e == null){
                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            mInfoArrayList.clear();
                            for (DocumentSnapshot documents : documentSnapshots.getDocuments()) {
                                Information information = documents.toObject(Information.class);
                                mInfoArrayList.add(information);
                            }
                            Information info  = mInfoArrayList.get(mInfoArrayList.size()-1);
                            mLastItemId = info.getId();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mInfoAdapter.notifyDataSetChanged();
                        } else {
                            isMoreDataAvailable = false;
                            mNoInfoTextView.setText(R.string.error);
                            mNoInfoTextView.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    } else {
                        mNoInfoTextView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        mNoInfoTextView.setText("Error getting "+mInfoType+"!. Please try again later");
                    }
                }
            };

            mListenerRegistration = mCollectionReference.orderBy("id", Query.Direction.DESCENDING)
                    .limit(NO_OF_INFO_TO_FETCH)
                    .addSnapshotListener(mEventListener);
        }
    }

    private void detachCollectionSnapshotListener(){
        if(mListenerRegistration != null){
            mListenerRegistration.remove();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInfoFragmentInteractionListener) {
            mListener = (OnInfoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnInfoFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        detachCollectionSnapshotListener();
    }


    @Override
    public void onItemClicked(View view) {
        if (mListener != null) {
            int position = mInfoRecyclerView.getChildAdapterPosition(view);
            Information info = mInfoArrayList.get(position);
            mListener.onInfoFragmentInteraction(view, info);
        }
    }

    @Override
    public void onSharedElementClicked(View view, View sharedView) {
        if (mListener != null) {
            int position = mInfoRecyclerView.getChildAdapterPosition(view);
            Information info = mInfoArrayList.get(position);
            mListener.onInfoFragmentInteraction(sharedView, info);
        }
    }

}