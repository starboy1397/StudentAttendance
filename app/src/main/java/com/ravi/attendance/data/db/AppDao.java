package com.ravi.attendance.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ravi.attendance.data.model.Absence;
import com.ravi.attendance.data.model.Student;

import java.util.List;


@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudents(List<Student> students);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudent(Student student);

    @Query("SELECT * FROM students ORDER BY studentName ASC")
    List<Student> getAllStudents();

    @Query("DELETE FROM students")
    void deleteAllStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAbsence(Absence absence);

    @Query("SELECT * FROM absences WHERE dateTime LIKE :date || '%'")
    List<Absence> getAbsencesByDate(String date);

    @Query("DELETE FROM absences")
    void deleteAllAbsences();
}
