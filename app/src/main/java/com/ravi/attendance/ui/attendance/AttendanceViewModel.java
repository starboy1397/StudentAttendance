package com.ravi.attendance.ui.attendance;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ravi.attendance.data.db.AppDatabase;
import com.ravi.attendance.data.model.Absence;
import com.ravi.attendance.data.model.Student;
import com.ravi.attendance.data.model.StudentStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceViewModel extends AndroidViewModel {

    private final MutableLiveData<List<StudentStatus>> studentStatusList = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentIndex = new MutableLiveData<>();
    private final AppDatabase database;

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getDatabase(application);
        loadStudents();
    }

    private void loadStudents() {
        List<Student> students = AppDatabase.getDatabase(getApplication()).appDao().getAllStudents();
        List<StudentStatus> statuses = new ArrayList<>();
        for (Student s : students) {
            statuses.add(new StudentStatus(s));
        }
        studentStatusList.setValue(statuses);
        if (!statuses.isEmpty()) {
            currentIndex.setValue(0);
        }
    }

    public LiveData<List<StudentStatus>> getStudentStatusList() {
        return studentStatusList;
    }

    public LiveData<Integer> getCurrentIndex() {
        return currentIndex;
    }

    public void markAttendance(boolean isAbsent) {
        List<StudentStatus> list = studentStatusList.getValue();
        Integer index = currentIndex.getValue();
        if (list == null || index == null || index >= list.size()) {
            Log.d("AttendanceViewModel", "markAttendance: list or index is null");
            return;
        }

        if (index >= list.size()) {
            Log.d("AttendanceViewModel", "markAttendance: index out of bounds -> " + index);
            return;
        }

        StudentStatus status = list.get(index);
        status.isAbsent = isAbsent;
        Log.d("AttendanceViewModel", "Marking student: " + status.student.studentName +
                " (ID: " + status.student.id + ") as " + (isAbsent ? "ABSENT" : "PRESENT"));
        if (isAbsent) {
            Absence a = new Absence();
            a.dateTime = getCurrentDateTime();
            a.studentId = status.student.id;
            database.appDao().insertAbsence(a);
            Log.d("AttendanceViewModel", "Inserted absence record for studentId = " + a.studentId);

        }

        studentStatusList.setValue(list);

        if (index + 1 < list.size()) {
            currentIndex.setValue(index + 1);
            Log.d("AttendanceViewModel", "Moving to next student: index = " + (index + 1));
        } else {
            Log.d("AttendanceViewModel", "Reached end of list. Attendance complete.");
        }
    }

    public void markAbsentManually(StudentStatus status) {
        status.isAbsent = true;
        Log.d("AttendanceViewModel", "Inserting absence: " + status.student.studentName);
        Absence a = new Absence();
        a.dateTime = getCurrentDateTime();
        a.studentId = status.student.id;
        database.appDao().insertAbsence(a);
        Log.d("AttendanceViewModel", "Manually marked absent and inserted for studentId = " + status.student.id);
    }

    public void unmarkAbsentManually(StudentStatus status) {
        status.isAbsent = false;
        database.appDao().deleteAbsenceByStudentId(status.student.id);
        Log.d("AttendanceViewModel", "Manually unmarked absent and deleted for studentId = " + status.student.id);
    }


    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
