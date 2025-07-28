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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.activitycast.R;
import com.example.activitycast.databinding.ActivitySetReqBinding;
import com.example.activitycast.viewmodel.MyViewModel;

public class SetReqActivity extends AppCompatActivity {

    private MyViewModel viewModel;
    private int minTemp = -99;
    private int maxTemp;
    private boolean rain;
    private boolean snow;
    private int visibility;
    private boolean windLow;
    private int aqi;

    private ActivitySetReqBinding binding;
    private int requirementsAdded = 0;
    private Dialog minTempDialog;
    private Dialog maxTempDialog;


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
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        int year = getIntent().getIntExtra("year", 0);
        int month = getIntent().getIntExtra("month", 0);
        int date = getIntent().getIntExtra("date", 0);
        int startHour = getIntent().getIntExtra("startHour", 0);
        int endHour = getIntent().getIntExtra("endHour", 0);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.addMinTempBtn.setOnClickListener(v -> {showMinTempDialog();});
        binding.addMaxTempBtn.setOnClickListener(v -> {showMaxTempDialog();});



        binding.rmvMinTempBtn.setOnClickListener(v -> {minTemp = -99;
            requirementsAdded--;
            binding.addMinTempBtn.setVisibility(View.VISIBLE);
            binding.rmvMinTempBtn.setVisibility(View.INVISIBLE);
        });

        binding.rmvMaxTempBtn.setOnClickListener(v -> {minTemp = 99;
            requirementsAdded--;
            binding.addMaxTempBtn.setVisibility(View.VISIBLE);
            binding.rmvMaxTempBtn.setVisibility(View.INVISIBLE);
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
            if (selectedId == R.id.min30) minTemp = 30;
            else if (selectedId == R.id.min25) minTemp = 25;
            else if (selectedId == R.id.min20) minTemp = 20;
            else if (selectedId == R.id.min10) minTemp = 10;
            else if (selectedId == R.id.min0) minTemp = 0;
            else if (selectedId == R.id.minn5) minTemp = -5;
            else if (selectedId == R.id.minn10) minTemp = -10;
            else if (selectedId == R.id.minn20) minTemp = -20;
            requirementsAdded++;
            binding.addMaxTempBtn.setVisibility(View.INVISIBLE);
            binding.rmvMaxTempBtn.setVisibility(View.VISIBLE);
            maxTempDialog.dismiss();
        });
    }

}