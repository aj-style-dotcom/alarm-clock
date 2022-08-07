package com.codelancer.alarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.codelancer.alarmclock.Adapter.AlarmAdapter;
import com.codelancer.alarmclock.Database.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private RecyclerView listRecycleView;
    private FloatingActionButton addButton;
    private AlarmAdapter alarmAdapter;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("All Alarms");

        //initializing vars
        initializeVar();

        alarmAdapter = new AlarmAdapter(new AlarmAdapter.AlarmDiff(), this);
        listRecycleView.setAdapter(alarmAdapter);

        // updating alarm list
        viewModel.getAllAlarms().observe(this, alarmModels -> alarmAdapter.submitList(alarmModels));

        // adding new alarms
        addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateAlarm.class)));
    }

    private void initializeVar(){
        addButton = findViewById(R.id.addButton);
        listRecycleView = findViewById(R.id.listRecycleView);
        listRecycleView.setLayoutManager(new LinearLayoutManager(this));

        viewModel =new ViewModelProvider(this).get(ViewModel.class);
    }
}