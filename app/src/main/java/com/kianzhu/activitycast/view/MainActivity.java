package com.kianzhu.activitycast.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.kianzhu.activitycast.R;
import com.kianzhu.activitycast.databinding.ActivityMainBinding;
import com.kianzhu.activitycast.model.ActivityReq;
import com.kianzhu.activitycast.room.ActivityReqDatabase;
import com.kianzhu.activitycast.view.adapter.MyAdapter;
import com.kianzhu.activitycast.viewmodel.MyViewModel;
import com.kianzhu.activitycast.worker.MultiActivityWorker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityReqDatabase activityReqDatabase;
    private ArrayList<ActivityReq> activityReqArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyViewModel viewModel;
    private MyAdapter myAdapter;
    private ActivityMainBinding mainBinding;
    private FloatingActionButton fab;
    private Dialog nameDialog;
    private Dialog notificationsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            showNotificationsDialog();
        }

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        activityReqDatabase = ActivityReqDatabase.getInstance(this);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.getAllActivityReq().observe(this, activityReqs -> {
            activityReqArrayList.clear();
            activityReqArrayList.addAll(activityReqs);
            if (activityReqArrayList.size() == 0) mainBinding.noActivitiesText.setVisibility(View.VISIBLE);
            else mainBinding.noActivitiesText.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
        });

        myAdapter = new MyAdapter(activityReqArrayList);
        recyclerView.setAdapter(myAdapter);

        fab = mainBinding.floatingActionButton;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        boolean newReqAdded = getIntent().getBooleanExtra("newReqAdded", false);

        if (newReqAdded)
        {
            scheduleNotificationWorker();
        }
    }

    private void showDialog()
    {
        nameDialog = new Dialog(this);
        nameDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.name_dialog, null);
        nameDialog.setContentView(view);
        nameDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        nameDialog.show();

        Button submit = view.findViewById(R.id.submit_btn);
        EditText edt = view.findViewById(R.id.name_edt);
        submit.setOnClickListener( v -> {
            String activityName = edt.getText().toString();
            Intent i = new Intent(this, SetLocationActivity.class);
            i.putExtra("activityName", activityName);
            startActivity(i);
        });
    }


    private void showNotificationsDialog()
    {
        notificationsDialog = new Dialog(this);
        notificationsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.notifications_alert_dialog, null);
        notificationsDialog.setContentView(view);
        notificationsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        notificationsDialog.show();

        Button okBTN = view.findViewById(R.id.ok_btn);
        okBTN.setOnClickListener( v -> {
            notificationsDialog.dismiss();
        });
    }


    private void scheduleNotificationWorker()
    {
        Data data = new Data.Builder().putBoolean("needsNotification", true).build();
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(MultiActivityWorker.class, 1, TimeUnit.HOURS)
                        .setInputData(data)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "NotifyUserWork",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }



    // Inflate menu_main.xml into the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_goto_second) {
            startActivity(new Intent(this,  ManualActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}