package com.dev.ieee_nsut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.ieee_nsut.R;
import com.dev.ieee_nsut.interfaces.OnRecyclerViewItemClickListener;
import com.dev.ieee_nsut.models.Resources;

import java.util.ArrayList;

public class IeeeResourcesAdapter extends RecyclerView.Adapter<com.dev.ieee_nsut.adapter.IeeeResourcesAdapter.ResourcesViewHolder> {
    private Context mContext;
    private ArrayList<Resources> mArrayList;
    private OnRecyclerViewItemClickListener mListener;

    public IeeeResourcesAdapter(Context mContext, ArrayList<Resources> mArrayList, OnRecyclerViewItemClickListener listener) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mListener = listener;
    }

    @Override
    public ResourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ieee_resources_item_layout, parent, false);
        return new ResourcesViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final ResourcesViewHolder holder, int position) {
        Resources resources = mArrayList.get(position);
        holder.textView.setText(resources.getmTitle());
        holder.imageView.setImageResource(resources.getmImageResId());
        holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, resources.getBgColorId()));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    static class ResourcesViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ResourcesViewHolder(final View itemView, final OnRecyclerViewItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.resources_image_view);
            textView = itemView.findViewById(R.id.resources_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onItemClicked(itemView);
                    }
                }
            });
        }
    }
}
