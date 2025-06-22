package com.ravi.attendance.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String studentName;
    public String rollNumber;
    public String courseName;
}
