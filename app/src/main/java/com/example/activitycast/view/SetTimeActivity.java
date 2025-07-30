package com.example.activitycast.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.activitycast.R;

public class SetTimeActivity extends AppCompatActivity {
    TimePicker startTimePicker, endTimePicker;
    Button btnConfirm;
    private Dialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startTimePicker = findViewById(R.id.startTimePicker);
        endTimePicker = findViewById(R.id.endTimePicker);
        btnConfirm = findViewById(R.id.btnConfirm);

        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);

        btnConfirm.setOnClickListener(view -> {
            int startHour = startTimePicker.getHour();
            int startMinute = startTimePicker.getMinute();

            int endHour = endTimePicker.getHour();
            int endMinute = endTimePicker.getMinute();

            if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
                showDialog();
            } else {
                String activityName = getIntent().getStringExtra("activityName");
                float latitude = getIntent().getFloatExtra("latitude", 0);
                float longitude = getIntent().getFloatExtra("longitude", 0);
                int day = getIntent().getIntExtra("day", 0);
                int month = getIntent().getIntExtra("month", 0);
                int year = getIntent().getIntExtra("year", 0);
                boolean aqiAvailable = getIntent().getBooleanExtra("aqiAvailable", false);
                Intent i = new Intent(SetTimeActivity.this, SetReqActivity.class);
                i.putExtra("activityName", activityName);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                i.putExtra("day", day);
                i.putExtra("month", month);
                i.putExtra("year", year);
                i.putExtra("startHour", startHour);
                i.putExtra("endHour", endHour);
                i.putExtra("aqiAvailable", aqiAvailable);
                startActivity(i);
            }
        });
    }

    private void showDialog()
    {
        errorDialog = new Dialog(this);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.invalid_time_dialog, null);
        errorDialog.setContentView(view);
        errorDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        errorDialog.show();

        Button okBTN = view.findViewById(R.id.ok_btn);
        okBTN.setOnClickListener( v -> {
            errorDialog.dismiss();
        });
    }
}