package com.example.activitycast.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.activitycast.R;
import com.example.activitycast.databinding.ActivityDetailsBinding;
import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.viewmodel.MyViewModel;
import com.example.activitycast.worker.SingleActivityWorker;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailsActivity extends AppCompatActivity {

    private ActivityReq activity;
    private ActivityDetailsBinding binding;
    private MyViewModel viewModel;
    private Dialog confirmDeleteDialog;
    private int activityID = -1;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        activity = getIntent().getParcelableExtra("activity");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        binding.setActivityReq(activity);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        displayActivityDetails(binding, activity);

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(activity.getName());
        }

        System.out.println(activity.getId());
        activityID = activity.getId();

    }



    // Inflate menu_main.xml into the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    // Handle menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_delete_activity) {
            showConfirmDeleteDialog();
            return true;
        } else if (item.getItemId() == R.id.menu_refresh)
        {
            Data data = new Data.Builder().putInt("id", activityID).build();
            WorkRequest wr = new OneTimeWorkRequest.Builder(SingleActivityWorker.class).setInputData(data).build();
            WorkManager.getInstance(getApplicationContext()).enqueue(wr);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra("activity", activity);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.menu_edit_activity)
        {
            Intent intent = new Intent(this, UpdateReqActivity.class);
            intent.putExtra("activity", activity);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmDeleteDialog()
    {
        confirmDeleteDialog = new Dialog(this);
        confirmDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.delete_confirmation_dialog, null);
        confirmDeleteDialog.setContentView(view);
        confirmDeleteDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        confirmDeleteDialog.show();

        Button cancelBTN = view.findViewById(R.id.cancel_btn);
        Button deleteBTN = view.findViewById(R.id.delete_btn);
        cancelBTN.setOnClickListener( v -> {
            confirmDeleteDialog.dismiss();
        });
        deleteBTN.setOnClickListener( v -> {
            viewModel.deleteActivityReq(activity);
            Toast.makeText(this, "Activity deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void displayActivityDetails(ActivityDetailsBinding binding, ActivityReq activity)
    {
        String time = activity.getStartHour() + "h - " + activity.getEndHour() + "h";
        String minTemp = activity.getMinTemp() == -99 ? "Not Specified" : "＞" + String.valueOf(activity.getMinTemp());
        String maxTemp = activity.getMaxTemp() == 99 ? "Not Specified" : "＜" + String.valueOf(activity.getMaxTemp());
        String rain = activity.isRain() ? "No" : "Not Specified";
        String snow = activity.isSnow() ? "No" : "Not Specified";
        String vis = activity.getVisibility() == -1 ? "Not Specified" : "＞" + String.valueOf(activity.getVisibility());
        String windSpd = activity.isWindSet() ? (activity.isWindLow() ? "＜10" : "≥10") : "Not Specified";
        String notes = Objects.equals(activity.getNotes(), "") ? "No notes" : activity.getNotes();
        String aqiString;
        int aqiRequired = activity.getAqi();
        if (aqiRequired != 700)
        {
            if (aqiRequired <= 50) aqiString = "Good";
            else if (aqiRequired <= 100) aqiString = "Moderate";
            else if (aqiRequired <= 150) aqiString = "Unhealthy for Sensitive Groups";
            else if (aqiRequired <= 200) aqiString = "Unhealthy";
            else if (aqiRequired <= 300) aqiString= "Very Unhealthy";
            else aqiString = "Hazardous";
        }
        else
        {
            aqiString = "Not Specified";
        }
        binding.aqiRequired.setText(aqiString);


        binding.minTempRequired.setText(minTemp);
        binding.maxTempRequired.setText(maxTemp);
        binding.rainRequired.setText(rain);
        binding.snowRequired.setText(snow);
        binding.visRequired.setText(vis);
        binding.windSpdRequired.setText(windSpd);
        binding.time.setText(time);
        binding.notes.setText(notes);

        double minTempForecasted = activity.getMinTempForecasted();
        double maxTempForecasted = activity.getMaxTempForecasted();
        Boolean isRainForecasted = activity.isRainForecasted();
        Boolean isSnowForecasted = activity.isSnowForecasted();
        int visibilityForecasted = activity.getVisibilityForecasted();
        double windSpeedForecasted = activity.getWindSpeedForecasted();

        binding.minTempForecasted.setText(String.valueOf(minTempForecasted));
        if (minTempForecasted < activity.getMinTemp()) binding.minTempForecasted.setTextColor(getResources().getColor(R.color.red));
        binding.maxTempForecasted.setText(String.valueOf(maxTempForecasted));
        if (maxTempForecasted > activity.getMaxTemp()) binding.maxTempForecasted.setTextColor(getResources().getColor(R.color.red));
        binding.rainForecasted.setText(isRainForecasted ? "Yes" : "No");
        if (isRainForecasted && activity.isRain()) binding.rainForecasted.setTextColor(getResources().getColor(R.color.red));
        binding.snowForecasted.setText(isSnowForecasted ? "Yes" : "No");
        if (isSnowForecasted && activity.isSnow()) binding.snowForecasted.setTextColor(getResources().getColor(R.color.red));
        binding.visForecasted.setText(String.valueOf(visibilityForecasted));
        if (visibilityForecasted < activity.getVisibility()) binding.visForecasted.setTextColor(getResources().getColor(R.color.red));
        binding.windSpdForecasted.setText(String.valueOf(activity.getWindSpeedForecasted()));
        if (activity.isWindSet())
        {
            if (activity.isWindLow()){
                if (windSpeedForecasted >= 10) binding.windSpdForecasted.setTextColor(getResources().getColor(R.color.red));
            } else{
                if (windSpeedForecasted < 10) binding.windSpdForecasted.setTextColor(getResources().getColor(R.color.red));
            }
        }

        String aqiForecastedString;
        if (activity.isAqiAvailable())
        {
            int aqiForecasted = activity.getAqiForecasted();
            if (aqiForecasted <= 50) aqiForecastedString = "Good";
            else if (aqiForecasted <= 100) aqiForecastedString = "Moderate";
            else if (aqiForecasted <= 150) aqiForecastedString = "Unhealthy for \nSensitive Groups";
            else if (aqiForecasted <= 200) aqiForecastedString = "Unhealthy";
            else if (aqiForecasted <= 300) aqiForecastedString = "Very Unhealthy";
            else aqiForecastedString = "Hazardous";
            if (aqiForecasted > activity.getAqi()) binding.aqiForecasted.setTextColor(getResources().getColor(R.color.red));
        }
        else
        {
            aqiForecastedString = "Unavailable";
            binding.aqiForecasted.setTextColor(getResources().getColor(R.color.grey));
        }
        binding.aqiForecasted.setText(aqiForecastedString);

    }
}