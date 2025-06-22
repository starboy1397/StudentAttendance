package com.ravi.attendance.ui.attendance;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ravi.attendance.R;
import com.ravi.attendance.databinding.ActivityAttendanceBinding;
import com.ravi.attendance.ui.adapter.StudentAdapter;

public class AttendanceActivity extends AppCompatActivity {

    private ActivityAttendanceBinding attendanceBinding;
    private AttendanceViewModel viewModel;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attendanceBinding = ActivityAttendanceBinding.inflate(getLayoutInflater());
        setContentView(attendanceBinding.getRoot());

        viewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
        adapter = new StudentAdapter();

        attendanceBinding.recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
        attendanceBinding.recyclerViewStudents.setAdapter(adapter);

        viewModel.getStudentStatusList().observe(this, adapter::setData);
        viewModel.getCurrentIndex().observe(this, index ->{
            adapter.setCurrentIndex(index);
            attendanceBinding.recyclerViewStudents.scrollToPosition(index);
        });

        attendanceBinding.btnPresent.setOnClickListener(v -> viewModel.markAttendance(false));
        attendanceBinding.btnAbsent.setOnClickListener(v -> viewModel.markAttendance(true));

    }
}