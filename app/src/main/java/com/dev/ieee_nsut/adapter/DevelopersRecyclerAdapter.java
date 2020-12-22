package com.dev.ieee_nsut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.interfaces.OnExecommItemClickListener;
import com.dev.ieee_nsut.models.Developers;

import java.util.ArrayList;

public class DevelopersRecyclerAdapter extends RecyclerView.Adapter<DevelopersRecyclerAdapter.DevelopersViewHolder> {
    private Context context;
    private ArrayList<Developers> arrayList;
    private OnExecommItemClickListener listener;

    public DevelopersRecyclerAdapter(Context context, ArrayList<Developers> arrayList, OnExecommItemClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public DevelopersRecyclerAdapter.DevelopersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.developers_item_layout, parent, false);
        return new DevelopersRecyclerAdapter.DevelopersViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final DevelopersRecyclerAdapter.DevelopersViewHolder holder, int position){
        Developers developers = arrayList.get(position);
        holder.nameTextView.setText(developers.getName());
        holder.designationTextView.setText(developers.getDesignation());
        holder.progressBar.setVisibility(View.VISIBLE);
        String phone = developers.getPhoneNo();
        String email = developers.getEmailId();

        if(phone != null){
            holder.phoneImageView.setVisibility(View.VISIBLE);
        } else {
            holder.phoneImageView.setVisibility(View.GONE);
        }

        if(email != null){
            holder.emailImageView.setVisibility(View.VISIBLE);
        } else {
            holder.emailImageView.setVisibility(View.GONE);
        }

        RequestBuilder<Bitmap> requestBuilder = Glide.with(context)
                .asBitmap()
                .load(developers.getPhotoUrl());

        requestBuilder.listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                holder.userPhotoImageView.setImageResource(R.drawable.user);
                return true;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory
                        .create(context.getResources(), resource);
                drawable.setCircular(true);
                holder.progressBar.setVisibility(View.GONE);
                holder.userPhotoImageView.setImageDrawable(drawable);
                return true;
            }
        }).into(holder.userPhotoImageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class DevelopersViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView designationTextView;
        ImageView phoneImageView;
        ImageView emailImageView;
        ImageView userPhotoImageView;
        ProgressBar progressBar;

        DevelopersViewHolder(final View itemView, final OnExecommItemClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.developers_name_text_view);
            designationTextView = itemView.findViewById(R.id.developers_designation_text_view);
            phoneImageView = itemView.findViewById(R.id.developers_phone_image_view);
            emailImageView = itemView.findViewById(R.id.developers_email_image_view);
            userPhotoImageView = itemView.findViewById(R.id.developers_image_view);
            progressBar = itemView.findViewById(R.id.developers_progress_bar);
            phoneImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        listener.onPhoneClicked(itemView);
                    }
                }
            });

            emailImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onEmailClicked(itemView);
                    }
                }
            });
        }
    }
}
