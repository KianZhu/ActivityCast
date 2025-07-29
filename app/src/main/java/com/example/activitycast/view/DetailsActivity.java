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

import com.example.activitycast.R;
import com.example.activitycast.databinding.ActivityDetailsBinding;
import com.example.activitycast.model.ActivityReq;
import com.example.activitycast.viewmodel.MyViewModel;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private ActivityReq activity;
    private ActivityDetailsBinding binding;
    private MyViewModel viewModel;
    private Dialog confirmDeleteDialog;

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
        binding.minTempRequired.setText(minTemp);
        binding.maxTempRequired.setText(maxTemp);
        binding.rainRequired.setText(rain);
        binding.snowRequired.setText(snow);
        binding.visRequired.setText(vis);
        binding.windSpdRequired.setText(windSpd);
        binding.time.setText(time);
        binding.notes.setText(notes);
    }
}