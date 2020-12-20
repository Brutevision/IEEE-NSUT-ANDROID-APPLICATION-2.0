package com.dev.ieee_nsut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ieee_nsut.adapter.ExecommRecyclerAdapter;
import com.dev.ieee_nsut.custom.MyRecyclerDivider;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnExecommItemClickListener;
import com.dev.ieee_nsut.models.Execomm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PastExecommActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener, OnExecommItemClickListener*/ {

    public static final String TAG = "PastExecommActivity";
    private DocumentReference pastExecommSessionDocument;
    private CollectionReference pastExecommMembersCollection;

    private RecyclerView execommRecyclerView;
    private ArrayList<Execomm> execommArrayList;
    private ExecommRecyclerAdapter execommRecyclerAdapter;
    private ArrayList<String> sessionList;
    private ArrayAdapter spinnerAdapter;
    private Spinner spinner;

    private TextView hintTextView;
    private ProgressBar progressBar;

    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_execomm);

        Toolbar toolbar = findViewById(R.id.past_toolbar);
        toolbar.setTitle("Past Team");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressBar = findViewById(R.id.past_progress_bar);
        hintTextView = findViewById(R.id.past_hint_text_view);
        cardView = findViewById(R.id.past_card_view);

        cardView.setVisibility(View.INVISIBLE);
        hintTextView.setVisibility(View.INVISIBLE);


    }
}