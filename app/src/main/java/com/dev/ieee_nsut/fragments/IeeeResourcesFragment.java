package com.dev.ieee_nsut.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.adapter.IeeeResourcesAdapter;
import com.dev.ieee_nsut.helpers.ContentUtils;
import com.dev.ieee_nsut.interfaces.OnRecyclerViewItemClickListener;
import com.dev.ieee_nsut.models.Resources;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class IeeeResourcesFragment extends Fragment implements OnRecyclerViewItemClickListener {

    public static final String TAG = "IeeeResourcesFragment";
    private ArrayList<Resources> mArrayList;
    private RecyclerView mRecyclerView;

    public IeeeResourcesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ieee_resources, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.SMP_toolbar);
        CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.ieee_resources_collapsing_toolbar);
        toolbar.setTitle("IEEE Resources");

        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mRecyclerView = view.findViewById(R.id.resources_recycler_view);
        mArrayList = new ArrayList<>();

        mArrayList.add(new Resources("Uncover IEEE member benefits that are most relevant to you. Use the Global Benefits finder below to select your current career phase and country, then select \"Go.\" Your results page will render a list of key IEEE member benefits that can help you accelerate your career plans and help you grow as a technology professional.\n" +
                "Accelerate your plans.  As a member, you'll be presented with new resources, valuable opportunities and many discounts that will help you advance your career in the right direction."
                , R.drawable.whatsapp_image_2020_12_28_at_02_32_14, "https://www.ieee.org/membership/benefits/index.html?byCareerPhase=Undergraduate+Student&byCountry=IN&fbclid=IwAR3SztRNiz0QZhQ2ucxSpEd1yvN325TWo4UCSLI8XEQon7MbJR1PiK14Yn4", R.color.dark_blue));
        mArrayList.add(new Resources("IEEE Spectrum is the flagship magazine and website of the IEEE, the world’s largest professional organization devoted to engineering and the applied sciences. Our charter is to keep over 400,000 members informed about major trends and developments in technology, engineering, and science."
                , R.drawable.ieee_spectrum, "https://spectrum.ieee.org/", R.color.dark_blue));
        mArrayList.add(new Resources("IEEE Collabratec™ is an integrated online community where technology professionals can network, collaborate, and create — all in one central hub."
                ,R.drawable.ieee_collabratec,"https://ieee-collabratec.ieee.org/", R.color.collabratec_color));
        mArrayList.add(new Resources("IEEE Xplore® Digital Library is a powerful resource for discovery of and access to scientific and technical content published by the IEEE (Institute of Electrical and Electronics Engineers) and its publishing partners."
                ,R.drawable.ieee_xplore,"http://ieeexplore.ieee.org/Xplore/home.jsp", R.color.xplore_color));
        mArrayList.add(new Resources("IEEE Transmitter™, in this you will find a collection of articles, videos, infographics, inspiration and more all curated by IEEE."
                ,R.drawable.ieee_transmitter,"https://transmitter.ieee.org/", R.color.transmitter_color));
        mArrayList.add(new Resources("IEEE ResumeLab is an online service that allows IEEE members to develop a resume or curriculum vitae using a wide array of resume templates."
                ,R.drawable.ieee_resume,"http://www.ieee.org/membership_services/membership/resumelab.html",
                R.color.resume_lab_color));

        IeeeResourcesAdapter adapter = new IeeeResourcesAdapter(getContext(), mArrayList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setPadding(0,0,0, ContentUtils.convertDpToPixel(56f, getActivity()));
    }
    @Override
    public void onItemClicked(View view) {
        int position = mRecyclerView.getChildAdapterPosition(view);

        Uri uri = Uri.parse((mArrayList.get(position).getmUrl()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getActivity().getPackageManager();
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent);
        } else{
            Snackbar.make(mRecyclerView, "Please install browser to continue", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("DISMISS", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }
}