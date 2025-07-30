package com.example.activitycast.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitycast.databinding.CityListItemBinding;
import com.example.activitycast.model.CityResultInd;
import com.example.activitycast.view.ManualActivity;
import com.example.activitycast.view.SetDateActivity;
import com.example.activitycast.view.SetTimeActivity;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private ArrayList<CityResultInd> cityList;
    private String activityName;

    public LocationAdapter(ArrayList<CityResultInd> cityList, String activityName) {
        this.cityList = cityList;
        this.activityName = activityName;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CityListItemBinding binding = CityListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new LocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        locationViewHolder.binding.setCityResult(cityList.get(i));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private CityListItemBinding binding;

        public LocationViewHolder(CityListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.nextBtn.setOnClickListener(v -> {
                int position = getAdapterPosition();
                CityResultInd clickedResult = cityList.get(position);
                Intent i = new Intent(v.getContext(), SetDateActivity.class);
                System.out.println(clickedResult.getLatitude());
                System.out.println(clickedResult.getLongitude());
                i.putExtra("activityName", activityName);
                i.putExtra("latitude", (float) clickedResult.getLatitude());
                i.putExtra("longitude", (float) clickedResult.getLongitude());
                v.getContext().startActivity(i);
            });
        }
    }
}
