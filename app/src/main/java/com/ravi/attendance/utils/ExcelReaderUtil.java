package com.ravi.attendance.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.ravi.attendance.data.db.AppDatabase;
import com.ravi.attendance.data.model.Student;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtil {

    public static void readExcelAndInsertStudents(Context context, Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<Student> studentList = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Cell nameCell = row.getCell(0);
                Cell rollCell = row.getCell(1);
                Cell courseCell = row.getCell(2);

                if (nameCell == null || rollCell == null || courseCell == null) continue;

                String name = nameCell.toString();
                String roll = rollCell.toString();
                String course = courseCell.toString();

                Student student = new Student();
                student.studentName = name;
                student.rollNumber = roll;
                student.courseName = course;

                studentList.add(student);
            }

            AppDatabase.getDatabase(context).appDao().insertStudents(studentList);
            workbook.close();
            inputStream.close();
            Log.d("ExcelImport", "Inserted " + studentList.size() + " students");
        } catch (Exception e) {
            Log.e("ExcelImport", "Error reading Excel: " + e.getMessage());
        }
    }
}
