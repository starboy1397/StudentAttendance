package com.ravi.attendance.ui.attendance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ravi.attendance.R;
import com.ravi.attendance.data.model.StudentStatus;
import com.ravi.attendance.databinding.ActivityAttendanceBinding;
import com.ravi.attendance.ui.adapter.StudentAdapter;

import java.util.List;

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
        adapter = new StudentAdapter(viewModel);

        attendanceBinding.recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
        attendanceBinding.recyclerViewStudents.setAdapter(adapter);

        viewModel.getStudentStatusList().observe(this, adapter::setData);
        viewModel.getCurrentIndex().observe(this, index ->{

            if (index == null) return;
            List<StudentStatus> list = viewModel.getStudentStatusList().getValue();
            if (list == null || index >= list.size()) return;

            adapter.setCurrentIndex(index);
            attendanceBinding.recyclerViewStudents.scrollToPosition(index);

            StudentStatus status = viewModel.getStudentStatusList().getValue().get(index);
            attendanceBinding.tvCurrentStudent.setText("Currently marking: " + status.student.studentName);

        });

        attendanceBinding.btnShowAbsentees.setOnClickListener(v -> {
            Intent intent = new Intent(this, AbsenteesActivity.class);
            startActivity(intent);
        });
        attendanceBinding.btnPresent.setOnClickListener(v -> viewModel.markAttendance(false));
        attendanceBinding.btnAbsent.setOnClickListener(v -> viewModel.markAttendance(true));

    }
}