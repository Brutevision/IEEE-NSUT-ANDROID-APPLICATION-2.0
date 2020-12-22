package com.dev.ieee_nsut.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.adapter.DevelopersRecyclerAdapter;
import com.dev.ieee_nsut.adapter.ExecommRecyclerAdapter;
import com.dev.ieee_nsut.custom.MyRecyclerDivider;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnExecommItemClickListener;
import com.dev.ieee_nsut.models.Developers;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DevelopersFragment extends Fragment implements OnExecommItemClickListener {
    private static final String TAG = "DevelopersFragment";
    private CollectionReference developersCollectionReference;
    private EventListener<QuerySnapshot> myeventListener;
    private ListenerRegistration mylistenerRegistration;

    private RecyclerView developersRecyclerView;
    private ArrayList<Developers> developersArrayList;
    private DevelopersRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorTextView;

    private CardView cardView;

    public DevelopersFragment() {
        // Required empty public constructor
    }

    public static DevelopersFragment newInstance() {
        return new DevelopersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        final Toolbar toolbar = view.findViewById(R.id.developers_toolbar);
        toolbar.setTitle("Developers");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        developersCollectionReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_DEVELOPERS);

        progressBar = view.findViewById(R.id.developers_progress_bar);
        errorTextView = view.findViewById(R.id.developers_error_text_view);
        errorTextView.setVisibility(View.GONE);
        cardView = view.findViewById(R.id.developers_card_view);
        cardView.setVisibility(View.INVISIBLE);

        developersArrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        adapter = new DevelopersRecyclerAdapter(getActivity(), developersArrayList, this);
        developersRecyclerView = view.findViewById(R.id.developers_recycler_view);
        developersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        developersRecyclerView.addItemDecoration(new MyRecyclerDivider(getActivity(), DividerItemDecoration.VERTICAL));
        developersRecyclerView.setAdapter(adapter);
        developersRecyclerView.setNestedScrollingEnabled(false);
        attachCollectionSnapshotListener();
    }

    private void attachCollectionSnapshotListener() {
        if (myeventListener == null) {
            myeventListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    progressBar.setVisibility(View.GONE);
                    if (e == null) {
                        if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                            developersArrayList.clear();
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                Developers developers = documentSnapshot.toObject(Developers.class);
                                developersArrayList.add(developers);
                            }
                            cardView.setVisibility(View.VISIBLE);
                            //pastTeamTextView.setVisibility(View.VISIBLE);
                            errorTextView.setVisibility(View.INVISIBLE);
                            adapter.notifyDataSetChanged();
                        } else {
                            errorTextView.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.INVISIBLE);
                            //pastTeamTextView.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        cardView.setVisibility(View.INVISIBLE);
                        //pastTeamTextView.setVisibility(View.GONE);
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                }
            };
        }
        mylistenerRegistration = developersCollectionReference
                .orderBy("id")
                .addSnapshotListener(myeventListener);
    }

    private void detachCollectionSnapshotListener(){
        if(mylistenerRegistration != null){
            mylistenerRegistration.remove();
        }
    }

    @Override
    public void onPhoneClicked(View view) {
        int position = developersRecyclerView.getChildAdapterPosition(view);
        Developers developers = developersArrayList.get(position);
        String phoneNo = developers.getPhoneNo();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ phoneNo));
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(intent);
        }
    }

    @Override
    public void onEmailClicked(View view) {
        int position = developersRecyclerView.getChildAdapterPosition(view);
        Developers developers = developersArrayList.get(position);
        String emailId = developers.getEmailId();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailId));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(),"Please install email app to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClicked(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
        detachCollectionSnapshotListener();
    }
}