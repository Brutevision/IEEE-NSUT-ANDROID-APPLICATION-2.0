package com.dev.ieee_nsut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.dev.ieee_nsut.custom.splashscreen;
import com.dev.ieee_nsut.fragments.AboutIeeeFragment;
import com.dev.ieee_nsut.fragments.DevelopersFragment;
import com.dev.ieee_nsut.fragments.ExecommFragment;
import com.dev.ieee_nsut.fragments.HomeFragment;
import com.dev.ieee_nsut.fragments.IeeeResourcesFragment;
import com.dev.ieee_nsut.fragments.InformationDetailsFragment;
import com.dev.ieee_nsut.fragments.InformationFragment;
//import com.dev.ieee_nsut.fragments.SMPFragment;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnHomeFragmentInteractionListener;
import com.dev.ieee_nsut.interfaces.OnHomeSliderInteractionListener;
import com.dev.ieee_nsut.interfaces.OnInfoDetailsFragmentInteractionListener;
import com.dev.ieee_nsut.interfaces.OnInfoFragmentInteractionListener;
import com.dev.ieee_nsut.models.Feed;
import com.dev.ieee_nsut.models.Information;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , OnInfoFragmentInteractionListener, OnInfoDetailsFragmentInteractionListener
        , OnHomeFragmentInteractionListener, OnHomeSliderInteractionListener {
    private static final String TAG = "MainActivity";

    private static final String HOME_FRAGMENT_TAG = "home_fragment";
    private static final String ABOUT_IEEE_FRAGMENT_TAG = "about_ieee_fragment";
    private static final String EVENTS_FRAGMENT_TAG = "events_fragment";
    private static final String FRAGMENT_TAG_KEY = "fragment_tag_key";

    private static final String ACHIEVEMENTS_FRAGMENT_TAG = "achieve_fragment_tag";
    private static final String PROJECTS_FRAGMENT_TAG = "projects_fragment_tag";
    private static final String DIARIES_FRAGMENT_TAG = "diaries_fragment";
//    private static final String SMP_FRAGMENT_TAG = "s_m_p_Fragment";
    private static final String IEEE_RESOURCES_TAG = "ieee_resources_tag";
    private static final String EXECOMM_FRAGMENT_TAG = "execomm_fragment";
    private static final String DEVELOPERS_FRAGMENT_TAG = "developers_fragment";

    private NavigationView mNavigationView;
//    private Button btnToggleDark;
    private String currentFragmentTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//title removed by Harsh Sharma :)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        // First display HomeFragment
        {
            displaySelectedFragment(R.id.nav_home);
            mNavigationView.setCheckedItem(R.id.nav_home);
        }

//        btnToggleDark = findViewById(R.id.btnToggleDark);
//
//        // Saving state of our app
//        // using SharedPreferences
//        SharedPreferences sharedPreferences
//                = getSharedPreferences(
//                "sharedPrefs", MODE_PRIVATE);
//        final SharedPreferences.Editor editor
//                = sharedPreferences.edit();
//        final boolean isDarkModeOn
//                = sharedPreferences
//                .getBoolean(
//                        "isDarkModeOn", false);
//
//        // When user reopens the app
//        // after applying dark/light mode
//        if (isDarkModeOn) {
//            AppCompatDelegate
//                    .setDefaultNightMode(
//                            AppCompatDelegate
//                                    .MODE_NIGHT_YES);
//            btnToggleDark.setText(
//                    "Disable Dark Mode");
//        }
//        else {
//            AppCompatDelegate
//                    .setDefaultNightMode(
//                            AppCompatDelegate
//                                    .MODE_NIGHT_NO);
//            btnToggleDark
//                    .setText(
//                            "Enable Dark Mode");
//        }
//
//        btnToggleDark.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view)
//                    {
//                        // When user taps the enable/disable
//                        // dark mode button
//                        if (isDarkModeOn) {
//
//                            // if dark mode is on it
//                            // will turn it off
//                            AppCompatDelegate
//                                    .setDefaultNightMode(
//                                            AppCompatDelegate
//                                                    .MODE_NIGHT_NO);
//                            // it will set isDarkModeOn
//                            // boolean to false
//                            editor.putBoolean(
//                                    "isDarkModeOn", false);
//                            editor.apply();
//
//                            // change text of Button
//                            btnToggleDark.setText(
//                                    "Enable Dark Mode");
//                        }
//                        else {
//
//                            // if dark mode is off
//                            // it will turn it on
//                            AppCompatDelegate
//                                    .setDefaultNightMode(
//                                            AppCompatDelegate
//                                                    .MODE_NIGHT_YES);
//
//                            // it will set isDarkModeOn
//                            // boolean to true
//                            editor.putBoolean(
//                                    "isDarkModeOn", true);
//                            editor.apply();
//
//                            // change text of Button
//                            btnToggleDark.setText(
//                                    "Disable Dark Mode");
//                        }
//                    }
//                });
    }

    //When pressed back activity should jump to HomeFragment
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //Create variable name for fragment
        HomeFragment homeFragment = (HomeFragment) fm.findFragmentByTag(HOME_FRAGMENT_TAG);
        AboutIeeeFragment ieeeFragment = (AboutIeeeFragment) fm.findFragmentByTag(ABOUT_IEEE_FRAGMENT_TAG);
        IeeeResourcesFragment ieeeResourcesFragment = (IeeeResourcesFragment) fm.findFragmentByTag(IEEE_RESOURCES_TAG);

        InformationFragment eventsFragment = (InformationFragment) fm.findFragmentByTag(EVENTS_FRAGMENT_TAG);
        InformationFragment achievementsFragment = (InformationFragment) fm.findFragmentByTag(ACHIEVEMENTS_FRAGMENT_TAG);
        InformationFragment projectsFragment = (InformationFragment) fm.findFragmentByTag(PROJECTS_FRAGMENT_TAG);
        InformationFragment diariesFragment = (InformationFragment) fm.findFragmentByTag(DIARIES_FRAGMENT_TAG);

        ExecommFragment execommFragment = (ExecommFragment) fm.findFragmentByTag(EXECOMM_FRAGMENT_TAG);

        DevelopersFragment developersFragment = (DevelopersFragment) fm.findFragmentByTag(DEVELOPERS_FRAGMENT_TAG);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ((ieeeFragment != null && ieeeFragment.isVisible())
                || (ieeeResourcesFragment != null && ieeeResourcesFragment.isVisible())
                || (eventsFragment != null && eventsFragment.isVisible())
                || (achievementsFragment != null && achievementsFragment.isVisible())
                || (projectsFragment != null && projectsFragment.isVisible())
                || (diariesFragment != null && diariesFragment.isVisible())
                || (execommFragment != null && execommFragment.isVisible())
                || (developersFragment != null && developersFragment.isVisible())) {
            ft.setCustomAnimations(R.anim.slide_back_from_left, R.anim.fade_translate_down);
            ft.replace(R.id.main_frame_layout, HomeFragment.newInstance(), HOME_FRAGMENT_TAG).addToBackStack(null).commit();
            mNavigationView.setCheckedItem(R.id.nav_home);
            currentFragmentTag = HOME_FRAGMENT_TAG;
        } else if (homeFragment != null && homeFragment.isVisible()) {
            finishAffinity();
        } else {
            try {
                super.onBackPressed();
            } catch (Exception e) {
                ft.setCustomAnimations(R.anim.slide_back_from_left, R.anim.fade_translate_down);
                ft.replace(R.id.main_frame_layout, HomeFragment.newInstance(), HOME_FRAGMENT_TAG).addToBackStack(null).commit();
                mNavigationView.setCheckedItem(R.id.nav_home);
                currentFragmentTag = HOME_FRAGMENT_TAG;
            }
        }
    }

    //Navigation item activities
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id== R.id.nav_share)
        {
            String appLink = "https://play.google.com/store/apps/details?id=" +
                    getApplicationContext().getPackageName();
            String shareAppMsg = "Download IEEE NSUT android application" +
                    "\n\n" +
                    "What the app has to offer?\n" +
                    "-Success journeys of our seniors\n" +
                    "-All projects made under society\n" +
                    "-Upcoming events, for which you can register with a single tap.\n" +
                    "-Society achievements, resources, and a lot more.\n" +
                    "-Complete interview processes of various companies\n\n" + appLink;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareAppMsg);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);}
        }
        else if (id == R.id.nav_connect) {
            startActivity(new Intent(this, AboutAppActivity.class));
        } else if (id == R.id.nav_join_ieee) {
            Uri uri = Uri.parse(ContentUtils.JOIN_IEEE_URL);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            PackageManager packageManager = getPackageManager();
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please install browser to continue", Toast.LENGTH_SHORT).show();
            }
        } else {
            displaySelectedFragment(id);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Funtion to display selected fragment
    void displaySelectedFragment(int menuItemId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (menuItemId) {
            case R.id.nav_home:
                if (!currentFragmentTag.equals(HOME_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, HomeFragment.newInstance(), HOME_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_home);
                    currentFragmentTag = HOME_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_ieee:
                if (!currentFragmentTag.equals(ABOUT_IEEE_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, new AboutIeeeFragment(), ABOUT_IEEE_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_ieee);
                    currentFragmentTag = ABOUT_IEEE_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_resource:
                if (!currentFragmentTag.equals(IEEE_RESOURCES_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, new IeeeResourcesFragment(), IEEE_RESOURCES_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_resource);
                    currentFragmentTag = IEEE_RESOURCES_TAG;
                }
                break;
            case R.id.nav_events:
                if (!currentFragmentTag.equals(EVENTS_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, InformationFragment.newInstance(ContentUtils.EVENTS), EVENTS_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_events);
                    currentFragmentTag = EVENTS_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_achievements:
                if (!currentFragmentTag.equals(ACHIEVEMENTS_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, InformationFragment.newInstance(ContentUtils.ACHIEVEMENTS), ACHIEVEMENTS_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_achievements);
                    currentFragmentTag = ACHIEVEMENTS_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_project:
                if (!currentFragmentTag.equals(PROJECTS_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, InformationFragment.newInstance(ContentUtils.PROJECTS), PROJECTS_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_project);
                    currentFragmentTag = PROJECTS_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_diaries:
                if (!currentFragmentTag.equals(DIARIES_FRAGMENT_TAG)) {
                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, InformationFragment.newInstance(ContentUtils.DIARIES), DIARIES_FRAGMENT_TAG).addToBackStack(null).commit();
                    mNavigationView.setCheckedItem(R.id.nav_diaries);
                    currentFragmentTag = PROJECTS_FRAGMENT_TAG;
                }
                break;
//            case R.id.nav_smp:
//                if (!currentFragmentTag.equals(SMP_FRAGMENT_TAG)) {
//                    ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left);
//                    ft.replace(R.id.main_frame_layout, new SMPFragment(), SMP_FRAGMENT_TAG).addToBackStack(null).commit();
//                    mNavigationView.setCheckedItem(R.id.nav_smp);
//                    currentFragmentTag = SMP_FRAGMENT_TAG;
//                }
//                break;
            case R.id.nav_team:
                if(!currentFragmentTag.equals(EXECOMM_FRAGMENT_TAG)){
                    ft.setCustomAnimations(R.anim.fade_translate_up,R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, ExecommFragment.newInstance(), EXECOMM_FRAGMENT_TAG).addToBackStack(null).commit();
                    ft.addToBackStack(null);
                    mNavigationView.setCheckedItem(R.id.nav_team);
                    currentFragmentTag = EXECOMM_FRAGMENT_TAG;
                }
                break;
            case R.id.nav_developers:
                if(!currentFragmentTag.equals(DEVELOPERS_FRAGMENT_TAG)){
                    ft.setCustomAnimations(R.anim.fade_translate_up,R.anim.slide_to_left);
                    ft.replace(R.id.main_frame_layout, DevelopersFragment.newInstance(), DEVELOPERS_FRAGMENT_TAG).addToBackStack(null).commit();
                    ft.addToBackStack(null);
                    mNavigationView.setCheckedItem(R.id.nav_developers);
                    currentFragmentTag = DEVELOPERS_FRAGMENT_TAG;
                }
        }
    }

    @Override
    public void onHomeFragmentInteraction(String itemTitle) {
        switch (itemTitle) {
            case ContentUtils.EVENTS:
                displaySelectedFragment(R.id.nav_events);
                break;
            case ContentUtils.PROJECTS:
                displaySelectedFragment(R.id.nav_project);
                break;
            case ContentUtils.ACHIEVEMENTS:
                displaySelectedFragment(R.id.nav_achievements);
                break;
            case ContentUtils.DIARIES:
                displaySelectedFragment(R.id.nav_diaries);
                break;
            case ContentUtils.ABOUT_IEEE:
                displaySelectedFragment(R.id.nav_ieee);
                break;
//            case ContentUtils.SMP:
//                displaySelectedFragment(R.id.nav_smp);
//                break;
            case ContentUtils.IEEE_RESOURCES:
                displaySelectedFragment(R.id.nav_resource);
                break;
        }
    }

    @Override
    public void onInfoFragmentInteraction(View sharedView, Information info) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ft.setCustomAnimations(R.anim.fade_translate_up, R.anim.slide_to_left,
                    R.anim.slide_back_from_left, R.anim.fade_translate_down)
                    .replace(R.id.main_frame_layout, InformationDetailsFragment.newInstance(info, null),
                            "InformationDetailsFragment")
                    .addToBackStack("InformationDetailsFragment")
                    .commit();
        } else {
            String transitionName = ViewCompat.getTransitionName(sharedView);
            InformationDetailsFragment fragment = InformationDetailsFragment.newInstance(info, transitionName);
            fragment.setSharedElementEnterTransition(TransitionInflater.from(this).
                    inflateTransition(android.R.transition.move));
            ft.addSharedElement(sharedView, transitionName).
                    replace(R.id.main_frame_layout, fragment,
                            "InformationDetailsFragment")
                    .addToBackStack("InformationDetailsFragment")
                    .commit();
        }
    }

    @Override
    public void onInfoDetailsInteraction(View view, Information information) {
        Intent intent = new Intent(this, InformationImageSliderActivity.class);
        intent.putExtra(ContentUtils.INFO_KEY, information);
        startActivity(intent);
    }

    @Override
    public void onHomeSliderInteraction(View view, Feed feed) {
        Intent intent = new Intent(this, ShowFeedImageActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view));
        intent.putExtra(ContentUtils.FEED_KEY, feed);
        startActivity(intent, options.toBundle());
    }
}