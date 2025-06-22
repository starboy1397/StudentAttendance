package com.ravi.attendance.data.model;

public class StudentStatus {

    public Student student;
    public boolean isAbsent;

    public StudentStatus(Student student) {
        this.student = student;
        this.isAbsent = false;
    }
}
