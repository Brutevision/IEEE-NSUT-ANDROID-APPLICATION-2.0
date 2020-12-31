package com.dev.ieee_nsut;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ieee_nsut.adapter.SocialRecyclerAdapter;
import com.dev.ieee_nsut.custom.MyRecyclerDivider;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnRecyclerViewItemClickListener;
import com.dev.ieee_nsut.models.Social;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        final androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        final CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                if(verticalOffset + totalScrollRange == 0){
                    toolbarLayout.setTitle("Connect");
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });

        TextView privacyTextView = findViewById(R.id.privacy_text_view);
        privacyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUrl(ContentUtils.PRIVACY_POLICY_URL);
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.social_recycler_view);
        final ArrayList<Social> arrayList = new ArrayList<>();
        arrayList.add(new Social("Visit our site", R.drawable.logos, ContentUtils.WEBSITE_URL));
        arrayList.add(new Social("Like us on facebook", R.drawable.fb, ContentUtils.FACEBOOK_URL));
        arrayList.add(new Social("Follow us on instagram", R.drawable.instagram, ContentUtils.INSTAGRAM_URL));
        arrayList.add(new Social("Follow us on twitter", R.drawable.tw1, ContentUtils.TWITTER_URL));
        SocialRecyclerAdapter adapter = new SocialRecyclerAdapter(this, arrayList, new OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClicked(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                handleUrl((arrayList.get(position).getUrl()));
            }
        });

        recyclerView.addItemDecoration(new MyRecyclerDivider(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
    private void handleUrl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getPackageManager();
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent);
        } else{
            Toast.makeText(this, "Please install browser to continue", Toast.LENGTH_SHORT).show();
        }
    }
}