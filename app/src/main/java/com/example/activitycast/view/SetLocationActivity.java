package com.example.activitycast.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activitycast.R;
import com.example.activitycast.databinding.ActivitySetLocationBinding;
import com.example.activitycast.model.CityResultInd;
import com.example.activitycast.view.adapter.LocationAdapter;
import com.example.activitycast.viewmodel.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class SetLocationActivity extends AppCompatActivity {
    private ArrayList<CityResultInd> cityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private ActivitySetLocationBinding binding;
    private MyViewModel myViewModel;
    private EditText editTextSearch;
    private Button buttonSearch;
    private String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_location);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = binding.editTextSearch;
        buttonSearch = binding.buttonSearch;

        activityName = getIntent().getStringExtra("activityName");
        binding.setActivityName(activityName);

        buttonSearch.setOnClickListener(v -> {
            String cityName = editTextSearch.getText().toString();
            myViewModel.getCityMutableLiveData(cityName).observe(this, new Observer<List<CityResultInd>>() {
                @Override
                public void onChanged(List<CityResultInd> cityResultInd) {
                    cityList.clear();
                    for (int i = 0; i < 10; i++)
                    {
                        cityList.add(cityResultInd.get(i));
                    }
//                    cityList.addAll(cityResultInd);
                    locationAdapter = new LocationAdapter(cityList, activityName);
                    Toast.makeText(SetLocationActivity.this, Integer.toString(locationAdapter.getItemCount()), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(locationAdapter);
                    locationAdapter.notifyDataSetChanged();
                }
            });
        });

    }


}