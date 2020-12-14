package com.dev.ieee_nsut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.dev.ieee_nsut.adapter.ExecommRecyclerAdapter;
import com.dev.ieee_nsut.interfaces.OnExecommItemClickListener;
import com.dev.ieee_nsut.models.Execomm;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

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
    }
}