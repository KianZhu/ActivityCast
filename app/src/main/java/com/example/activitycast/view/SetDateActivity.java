package com.example.activitycast.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.activitycast.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;

public class SetDateActivity extends AppCompatActivity {

    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_date);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        datePicker = findViewById(R.id.datePicker);
        datePicker.setMinDate(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();

        datePicker.setMinDate(today);

        calendar.add(Calendar.DAY_OF_MONTH, 15);
        long maxDate = calendar.getTimeInMillis();
        datePicker.setMaxDate(maxDate);

        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            String activityName = getIntent().getStringExtra("activityName");
            int latitude = getIntent().getIntExtra("latitude", 0);
            int longitude = getIntent().getIntExtra("longitude", 0);
            Intent i = new Intent(v.getContext(), SetTimeActivity.class);
            i.putExtra("activityName", activityName);
            i.putExtra("latitude", latitude);
            i.putExtra("longitude", longitude);
            i.putExtra("day", day);
            i.putExtra("month", month);
            i.putExtra("year", year);
            v.getContext().startActivity(i);
        });

    }
}