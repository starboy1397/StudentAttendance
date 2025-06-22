package com.ravi.attendance.ui.attendance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ravi.attendance.data.db.AppDatabase;
import com.ravi.attendance.data.model.Student;
import com.ravi.attendance.data.model.StudentStatus;

import java.util.ArrayList;
import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {

    private final MutableLiveData<List<StudentStatus>> studentStatusList = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentIndex = new MutableLiveData<>();

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        loadStudents();
    }

    private void loadStudents() {
        List<Student> students = AppDatabase.getDatabase(getApplication()).appDao().getAllStudents();
        List<StudentStatus> statuses = new ArrayList<>();
        for (Student s : students) {
            statuses.add(new StudentStatus(s));
        }
        studentStatusList.setValue(statuses);
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
        if (list == null || index == null || index >= list.size()) return;
        list.get(index).isAbsent = isAbsent;
        studentStatusList.setValue(list);

        if (index + 1 < list.size()) {
            currentIndex.setValue(index + 1);
        }
    }
}
