package com.dev.ieee_nsut.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;


/**
 *
 */

public class ContentUtils {

    public static final String EVENTS = "Events";
    public static final String ACHIEVEMENTS = "Achievements";
    public static final String PROJECTS = "Projects";
    public static final String DIARIES = "Success Diaries";
    public static final String SMP = "SMP";
    public static final String IEEE_RESOURCES = "IEEE Resources";
    public static final String ABOUT_IEEE = "About IEEE";
    public static final String INFO_KEY = "info_key";
    public static final String SHARED_PREF = "ieee_nsut";


    public static final String FEED_KEY = "feed_key";

    public static final String IMAGE_URL_KEY = "image_url_key";


    public static final String STATE_RESOLVING_ERROR = "resolving_error";

    public static final String TRANSITION_NAME = "transition_name";


    public static final String FIRESTORE_EVENTS = "events";
    public static final String FIRESTORE_ACHIEVEMENTS = "achievements";
    public static final String FIRESTORE_PROJECTS = "projects";
    public static final String FIRESTORE_DIARIES = "diaries";
    public static final String FIRESTORE_FEEDS = "feeds";
    public static final String FIRESTORE_EXECOMM = "execomm";
    public static final String FIRESTORE_DEVELOPERS = "developers";
    public static final String FIREBASE_STORAGE_PROFILE_IMAGE = "profile_image";
    public static final  String FIRESTORE_PAST_EXECOMM = "pastExecomm";
    public static final  String FIRESTORE_PAST_EXECOMM_SESSION = "session";
    public static final  String FIRESTORE_PAST_EXECOMM_SESSION_YEAR = "year";


    public static final String WEBSITE_URL = "https://www.ieeensut.com/";
    public static final String FACEBOOK_URL = "https://www.facebook.com/ieeensit/";
    public static final String INSTAGRAM_URL = "https://www.instagram.com/ieee_nsut/";
    public static final String TWITTER_URL = "https://twitter.com/ieeensut?lang=en";
    public static final String JOIN_IEEE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSe92LHpgIFl9dvsDGR1H_Rwl9dQyvGbtjfBr67IcmkAbf-Rrw/viewform";
    public static final String PRIVACY_POLICY_URL = "https://sites.google.com/view/ieeensutapplication/home?authuser=3";


    public static ArrayList<String> getInterestArrayList(String interest) {
        ArrayList<String> interestArrayList = new ArrayList<>();
        if (interest != null) {
            String interestArray[] = interest.split(";");
            Collections.addAll(interestArrayList, interestArray);
            interestArrayList.remove(0);
        }

        return interestArrayList;
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

    public static HashMap<String, Boolean> getMapFromArrayList(ArrayList<String> interestArrayList) {
        HashMap<String, Boolean> interestMap = new HashMap<>();
        for (String interest : interestArrayList) {
            interestMap.put(interest, true);
        }
        return interestMap;
    }

    public static ArrayList<String> getArrayListFromMap(HashMap<String, Boolean> interestMap) {
        ArrayList<String> interestArrayList = new ArrayList<>();
        interestArrayList.addAll(interestMap.keySet());
        return interestArrayList;
    }


    /**
     * @param context
     */

    public static void deleteUserDataFromSharedPref(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(com.dev.ieee_nsut.helpers.ContentUtils.SHARED_PREF,
                Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    public static String formatString(String description){
        description = description.replace("\\n", "\n").replace("\\r", "\r");
        return description;

    }
    // circleIndicator with view pager
    public static void syncIndicatorWithViewPager(ViewPager viewPager, CircleIndicator circleIndicator){
        if(viewPager == null || circleIndicator == null) {
            return;
        }
        if (viewPager.getAdapter().getCount() > 1) {
            circleIndicator.setVisibility(View.VISIBLE);
        } else {
            circleIndicator.setVisibility(View.INVISIBLE);
        }
    }
}