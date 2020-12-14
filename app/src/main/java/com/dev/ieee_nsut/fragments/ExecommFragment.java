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

import com.dev.ieee_nsut.PastExecommActivity;
import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.adapter.ExecommRecyclerAdapter;
import com.dev.ieee_nsut.custom.MyRecyclerDivider;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnExecommItemClickListener;
import com.dev.ieee_nsut.models.Execomm;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExecommFragment extends Fragment implements OnExecommItemClickListener {
    private static final String TAG = "ExecommFragment";
    private CollectionReference execommCollectionReference;
    private EventListener<QuerySnapshot> eventListener;
    private ListenerRegistration listenerRegistration;

    private RecyclerView execommRecyclerView;
    private ArrayList<Execomm> execommArrayList;
    private ExecommRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private TextView errorTextView;

    private TextView pastTeamTextView;

    private CardView cardView;


    public ExecommFragment() {
        // Required empty public constructor
    }

    public static ExecommFragment newInstance() {
        return new ExecommFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_execomm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        final Toolbar toolbar = view.findViewById(R.id.execomm_toolbar);
        toolbar.setTitle("Team");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        execommCollectionReference = FirebaseFirestore.getInstance().collection(ContentUtils.FIRESTORE_EXECOMM);

        progressBar = view.findViewById(R.id.execomm_progress_bar);
        errorTextView = view.findViewById(R.id.execomm_error_text_view);
        errorTextView.setVisibility(View.GONE);
        cardView = view.findViewById(R.id.execomm_card_view);
        cardView.setVisibility(View.INVISIBLE);

        pastTeamTextView = view.findViewById(R.id.past_text_view);
        pastTeamTextView.setVisibility(View.GONE);

        pastTeamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PastExecommActivity.class));
            }
        });

        execommArrayList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        adapter = new ExecommRecyclerAdapter(getActivity(), execommArrayList, this);
        execommRecyclerView = view.findViewById(R.id.execomm_recycler_view);
        execommRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        execommRecyclerView.addItemDecoration(new MyRecyclerDivider(getActivity(), DividerItemDecoration.VERTICAL));
        execommRecyclerView.setAdapter(adapter);
        execommRecyclerView.setNestedScrollingEnabled(false);
        attachCollectionSnapshotListener();
    }

    private void attachCollectionSnapshotListener(){
        if(eventListener == null){
            eventListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    progressBar.setVisibility(View.GONE);
                    if(e == null){
                        if(documentSnapshots != null && !documentSnapshots.isEmpty()){
                            execommArrayList.clear();
                            for(DocumentSnapshot documentSnapshot : documentSnapshots){
                                Execomm execomm = documentSnapshot.toObject(Execomm.class);
                                execommArrayList.add(execomm);
                            }
                            cardView.setVisibility(View.VISIBLE);
                            pastTeamTextView.setVisibility(View.VISIBLE);
                            errorTextView.setVisibility(View.INVISIBLE);
                            adapter.notifyDataSetChanged();
                        } else {
                            errorTextView.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.INVISIBLE);
                            pastTeamTextView.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        cardView.setVisibility(View.INVISIBLE);
                        pastTeamTextView.setVisibility(View.GONE);
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                }
            };
        }
        listenerRegistration = execommCollectionReference
                .orderBy("id")
                .addSnapshotListener(eventListener);
    }

    private void detachCollectionSnapshotListener(){
        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
    }

    @Override
    public void onPhoneClicked(View view) {
        int position = execommRecyclerView.getChildAdapterPosition(view);
        Execomm execomm = execommArrayList.get(position);
        String phoneNo = execomm.getPhoneNo();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ phoneNo));
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(intent);
        }
    }

    @Override
    public void onEmailClicked(View view) {
        int position = execommRecyclerView.getChildAdapterPosition(view);
        Execomm execomm = execommArrayList.get(position);
        String emailId = execomm.getEmailId();
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