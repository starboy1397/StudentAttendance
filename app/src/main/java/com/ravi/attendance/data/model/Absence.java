package com.ravi.attendance.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "absences")
public class Absence {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String dateTime;
    public int studentId;

}
