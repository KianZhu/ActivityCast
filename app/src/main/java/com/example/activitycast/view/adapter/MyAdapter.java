package com.example.activitycast.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitycast.R;
import com.example.activitycast.databinding.ActivityreqListItemBinding;
import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.view.DetailsActivity;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<ActivityReq> activityReqArrayList;

    public MyAdapter(ArrayList<ActivityReq> activityReqArrayList)
    {
        this.activityReqArrayList = activityReqArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ActivityreqListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.activityreq_list_item,
                viewGroup,
                false
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ActivityReq current = activityReqArrayList.get(i);
        myViewHolder.binding.setActivityreq(current);
    }

    @Override
    public int getItemCount() {
        if (activityReqArrayList != null) return activityReqArrayList.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ActivityreqListItemBinding binding;

        public MyViewHolder(ActivityreqListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ActivityReq activity = activityReqArrayList.get(position);
                    Intent i = new Intent(v.getContext(), DetailsActivity.class);
                    i.putExtra("activity", activity);
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
