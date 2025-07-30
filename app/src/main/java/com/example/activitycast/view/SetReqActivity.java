package com.example.activitycast.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.example.activitycast.databinding.ActivitySetReqBinding;
import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.viewmodel.MyViewModel;
import com.example.activitycast.worker.SingleActivityWorker;

import java.util.Objects;

public class SetReqActivity extends AppCompatActivity {

    private MyViewModel viewModel;
    private int minTemp = -99;
    private int maxTemp = 99;
    private boolean rain = false;
    private boolean snow = false;
    private int visibility = -1;
    private boolean windLow = false;
    private boolean windSet = false;
    private int aqi = 700;

    private ActivitySetReqBinding binding;
    private int requirementsAdded = 0;
    private Dialog minTempDialog;
    private Dialog maxTempDialog;
    private Dialog minVisDialog;
    private Dialog windDialog;
    private Dialog maxAqiDialog;
    private Dialog noReqAddedDialog;
    private Dialog tempErrorDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_req);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_req);

        String activityName = getIntent().getStringExtra("activityName");
        float latitude = getIntent().getFloatExtra("latitude", 0);
        float longitude = getIntent().getFloatExtra("longitude", 0);
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int day = getIntent().getIntExtra("day", 0);
        int startHour = getIntent().getIntExtra("startHour", 0);
        int endHour = getIntent().getIntExtra("endHour", 0);
        Boolean aqiAvailable = getIntent().getBooleanExtra("aqiAvailable", false);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.setActivityName(activityName);

        binding.addMinTempBtn.setOnClickListener(v -> {showMinTempDialog();});
        binding.addMaxTempBtn.setOnClickListener(v -> {showMaxTempDialog();});
        binding.addRainBTN.setOnClickListener(v -> {
            rain = true;
            requirementsAdded++;
            binding.addRainBTN.setVisibility(View.INVISIBLE);
            binding.rmvRainBtn.setVisibility(View.VISIBLE);
        });
        binding.addSnowBTN.setOnClickListener(v -> {
            snow = true;
            requirementsAdded++;
            binding.addSnowBTN.setVisibility(View.INVISIBLE);
            binding.rmvSnowBtn.setVisibility(View.VISIBLE);
        });
        binding.addVisibilityBTN.setOnClickListener(v -> {showMinVisDialog();});
        binding.addWindSpeedBTN.setOnClickListener(v -> {showWindSpdDialog();});
        binding.addAirQualityBTN.setOnClickListener(v -> {showAqiDialog();});




        binding.rmvMinTempBtn.setOnClickListener(v -> {
            minTemp = -99;
            requirementsAdded--;
            binding.addMinTempBtn.setVisibility(View.VISIBLE);
            binding.rmvMinTempBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvMaxTempBtn.setOnClickListener(v -> {
            maxTemp = 99;
            requirementsAdded--;
            binding.addMaxTempBtn.setVisibility(View.VISIBLE);
            binding.rmvMaxTempBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvRainBtn.setOnClickListener(v -> {
            rain = false;
            requirementsAdded--;
            binding.addRainBTN.setVisibility(View.VISIBLE);
            binding.rmvRainBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvSnowBtn.setOnClickListener(v -> {
            snow = false;
            requirementsAdded--;
            binding.addSnowBTN.setVisibility(View.VISIBLE);
            binding.rmvSnowBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvVisibilityBtn.setOnClickListener(v -> {
            visibility = -1;
            requirementsAdded--;
            binding.addVisibilityBTN.setVisibility(View.VISIBLE);
            binding.rmvVisibilityBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvWindSpeedBtn.setOnClickListener(v -> {
            windSet = false;
            requirementsAdded--;
            binding.addWindSpeedBTN.setVisibility(View.VISIBLE);
            binding.rmvWindSpeedBtn.setVisibility(View.INVISIBLE);
        });
        binding.rmvAirQualityBtn.setOnClickListener(v -> {
            aqi = 0;
            requirementsAdded--;
            binding.addAirQualityBTN.setVisibility(View.VISIBLE);
            binding.rmvAirQualityBtn.setVisibility(View.INVISIBLE);
        });


        binding.doneBTN.setOnClickListener(v -> {
            if (requirementsAdded == 0)
            {
                showNoReqAddedDialog();
            }
            else if (minTemp > maxTemp)
            {
                showTempErrorDialog();
            }
            else
            {
                ActivityReq newReq = new ActivityReq();
                newReq.setName(activityName);
                newReq.setYear(year);
                newReq.setMonth(month);
                newReq.setDate(day);
                newReq.setStartHour(startHour);
                newReq.setEndHour(endHour);
                newReq.setLatitude(latitude);
                newReq.setLongitude(longitude);
                newReq.setMinTemp(minTemp);
                newReq.setMaxTemp(maxTemp);
                newReq.setRain(rain);
                newReq.setSnow(snow);
                newReq.setVisibility(visibility);
                newReq.setWindLow(windLow);
                newReq.setWindSet(windSet);
                newReq.setAqi(aqi);
                newReq.setNotes(binding.notesEDT.getText().toString());
                newReq.setAqiAvailable(aqiAvailable);
                viewModel.addNewActivityReq(newReq);
                Toast.makeText(this, "New activity added", Toast.LENGTH_SHORT).show();
                WorkRequest wr = new OneTimeWorkRequest.Builder(SingleActivityWorker.class).build();
                WorkManager.getInstance(getApplicationContext()).enqueue(wr);

//                viewModel.getNewestActivityReq().observe(this, activityReq -> {
//                    System.out.println(activityReq.getName());
//                });


                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        });
    }

    private void showMinTempDialog()
    {
        minTempDialog = new Dialog(this);
        minTempDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.min_temp_dialog, null);
        minTempDialog.setContentView(view);
        minTempDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        minTempDialog.show();

        RadioGroup radioGroup = minTempDialog.findViewById(R.id.radioGroup);
        ColorStateList tint = ContextCompat.getColorStateList(this.getApplicationContext(), R.color.radio_color_selector);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            ((RadioButton) child).setButtonTintList(tint);
        }

        Button submit = view.findViewById(R.id.submit_btn);
        submit.setOnClickListener( v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) return;
            if (selectedId == R.id.min30) minTemp = 30;
            else if (selectedId == R.id.min25) minTemp = 25;
            else if (selectedId == R.id.min20) minTemp = 20;
            else if (selectedId == R.id.min10) minTemp = 10;
            else if (selectedId == R.id.min0) minTemp = 0;
            else if (selectedId == R.id.minn5) minTemp = -5;
            else if (selectedId == R.id.minn10) minTemp = -10;
            else if (selectedId == R.id.minn20) minTemp = -20;
            requirementsAdded++;
            binding.addMinTempBtn.setVisibility(View.INVISIBLE);
            binding.rmvMinTempBtn.setVisibility(View.VISIBLE);
            minTempDialog.dismiss();
        });
    }

    private void showMaxTempDialog()
    {
        maxTempDialog = new Dialog(this);
        maxTempDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.max_temp_dialog, null);
        maxTempDialog.setContentView(view);
        maxTempDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        maxTempDialog.show();

        RadioGroup radioGroup = maxTempDialog.findViewById(R.id.radioGroup);
        ColorStateList tint = ContextCompat.getColorStateList(this.getApplicationContext(), R.color.radio_color_selector);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            ((RadioButton) child).setButtonTintList(tint);
        }

        Button submit = view.findViewById(R.id.submit_btn);
        submit.setOnClickListener( v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) return;
            if (selectedId == R.id.max40) maxTemp = 40;
            else if (selectedId == R.id.max35) maxTemp = 35;
            else if (selectedId == R.id.max30) maxTemp = 30;
            else if (selectedId == R.id.max25) maxTemp = 25;
            else if (selectedId == R.id.max20) maxTemp = 20;
            else if (selectedId == R.id.max10) maxTemp = 10;
            else if (selectedId == R.id.max0) maxTemp = 0;
            else if (selectedId == R.id.maxn10) maxTemp = -10;
            requirementsAdded++;
            binding.addMaxTempBtn.setVisibility(View.INVISIBLE);
            binding.rmvMaxTempBtn.setVisibility(View.VISIBLE);
            maxTempDialog.dismiss();
        });
    }

    private void showMinVisDialog()
    {
        minVisDialog = new Dialog(this);
        minVisDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.min_vis_dialog, null);
        minVisDialog.setContentView(view);
        minVisDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        minVisDialog.show();

        RadioGroup radioGroup = minVisDialog.findViewById(R.id.radioGroup);
        ColorStateList tint = ContextCompat.getColorStateList(this.getApplicationContext(), R.color.radio_color_selector);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            ((RadioButton) child).setButtonTintList(tint);
        }

        Button submit = view.findViewById(R.id.submit_btn);
        submit.setOnClickListener( v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) return;
            if (selectedId == R.id.min10) visibility = 10;
            else if (selectedId == R.id.min30) visibility = 30;
            else if (selectedId == R.id.min75) visibility = 75;
            else if (selectedId == R.id.min100) visibility = 100;
            else if (selectedId == R.id.min200) visibility = 200;
            else if (selectedId == R.id.min300) visibility = 300;
            else if (selectedId == R.id.min400) visibility = 400;
            else if (selectedId == R.id.min500) visibility = 500;
            else if (selectedId == R.id.min1k) visibility = 1000;
            else if (selectedId == R.id.min2k) visibility = 2000;
            else if (selectedId == R.id.min5k) visibility = 5000;
            else if (selectedId == R.id.min10k) visibility = 10000;
            requirementsAdded++;
            binding.addVisibilityBTN.setVisibility(View.INVISIBLE);
            binding.rmvVisibilityBtn.setVisibility(View.VISIBLE);
            minVisDialog.dismiss();
        });
    }

    private void showWindSpdDialog()
    {
        windDialog = new Dialog(this);
        windDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.wind_spd_dialog, null);
        windDialog.setContentView(view);
        windDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        windDialog.show();

        RadioGroup radioGroup = windDialog.findViewById(R.id.radioGroup);
        ColorStateList tint = ContextCompat.getColorStateList(this.getApplicationContext(), R.color.radio_color_selector);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            ((RadioButton) child).setButtonTintList(tint);
        }

        Button submit = view.findViewById(R.id.submit_btn);
        submit.setOnClickListener( v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) return;
            windSet = true;
            if (selectedId == R.id.l10) windLow = true;
            else if (selectedId == R.id.ge10) windLow = false;
            requirementsAdded++;
            binding.addWindSpeedBTN.setVisibility(View.INVISIBLE);
            binding.rmvWindSpeedBtn.setVisibility(View.VISIBLE);
            windDialog.dismiss();
        });
    }

    private void showAqiDialog()
    {
        maxAqiDialog = new Dialog(this);
        maxAqiDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.aqi_dialog, null);
        maxAqiDialog.setContentView(view);
        maxAqiDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        maxAqiDialog.show();

        RadioGroup radioGroup = maxAqiDialog.findViewById(R.id.radioGroup);
        ColorStateList tint = ContextCompat.getColorStateList(this.getApplicationContext(), R.color.radio_color_selector);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            ((RadioButton) child).setButtonTintList(tint);
        }

        Button submit = view.findViewById(R.id.submit_btn);
        submit.setOnClickListener( v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) return;
            if (selectedId == R.id.max50) aqi = 50;
            else if (selectedId == R.id.max100) aqi = 100;
            else if (selectedId == R.id.max150) aqi = 150;
            else if (selectedId == R.id.max200) aqi = 200;
            else if (selectedId == R.id.max300) aqi = 400;
            requirementsAdded++;
            binding.addAirQualityBTN.setVisibility(View.INVISIBLE);
            binding.rmvAirQualityBtn.setVisibility(View.VISIBLE);
            maxAqiDialog.dismiss();
        });
    }

    private void showNoReqAddedDialog() {
        noReqAddedDialog = new Dialog(this);
        noReqAddedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.no_req_added_dialog, null);
        noReqAddedDialog.setContentView(view);
        noReqAddedDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        noReqAddedDialog.show();

        Button okBTN = view.findViewById(R.id.ok_btn);
        okBTN.setOnClickListener( v -> {
            noReqAddedDialog.dismiss();
        });
    }

    private void showTempErrorDialog()
    {
        tempErrorDialog = new Dialog(this);
        tempErrorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.temp_error_dialog, null);
        tempErrorDialog.setContentView(view);
        tempErrorDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tempErrorDialog.show();

        Button okBTN = view.findViewById(R.id.ok_btn);
        okBTN.setOnClickListener( v -> {
            tempErrorDialog.dismiss();
        });
    }

}