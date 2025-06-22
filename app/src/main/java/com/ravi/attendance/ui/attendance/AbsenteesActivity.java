package com.ravi.attendance.ui.attendance;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ravi.attendance.R;
import com.ravi.attendance.data.db.AppDatabase;
import com.ravi.attendance.data.model.Absence;
import com.ravi.attendance.data.model.Student;
import com.ravi.attendance.databinding.ActivityAbsenteesBinding;
import com.ravi.attendance.databinding.ActivityAttendanceBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbsenteesActivity extends AppCompatActivity {

    private ActivityAbsenteesBinding absenteesBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        absenteesBinding = ActivityAbsenteesBinding.inflate(getLayoutInflater());
        setContentView(absenteesBinding.getRoot());

        AppDatabase db = AppDatabase.getDatabase(this);
        String today = getTodayDate();

        List<Absence> absences = db.appDao().getAbsencesByDate(today);
        List<String> absenteeNames = new ArrayList<>();

        for (Absence a : absences) {
            Student s = db.appDao().getStudentById(a.studentId);
            if (s != null) {
                absenteeNames.add(s.studentName + " (" + s.rollNumber + ")");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, absenteeNames);
        absenteesBinding.listViewAbsentees.setAdapter(adapter);
    }

    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}