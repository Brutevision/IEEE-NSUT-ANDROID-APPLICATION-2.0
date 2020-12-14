package com.dev.ieee_nsut;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.dev.ieee_nsut.adapter.ImageSliderPagerAdapter;
import com.dev.ieee_nsut.custom.ZoomOutPageTransformer;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.models.Information;

import java.util.ArrayList;

//import me.relex.circleindicator.CircleIndicator;

public class InformationImageSliderActivity extends AppCompatActivity {

    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_image_slider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Information information = getIntent().getParcelableExtra(ContentUtils.INFO_KEY);
        if (information != null) {
            for(String url : information.getImageList()){
                if(url != null){
                    mArrayList.add(url);
                }
            }
        }

        ViewPager viewPager = findViewById(R.id.image_slider_view_pager);
        //CircleIndicator circleIndicator = findViewById(R.id.circle_indicator);
        ImageSliderPagerAdapter pagerAdapter = new ImageSliderPagerAdapter(getSupportFragmentManager(), mArrayList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //circleIndicator.setViewPager(viewPager);
        //.registerDataSetObserver(circleIndicator.getDataSetObserver());
        //ContentUtils.syncIndicatorWithViewPager(viewPager, circleIndicator);


    }

}
