package com.ravi.attendance.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ravi.attendance.R;
import com.ravi.attendance.databinding.ActivityMainBinding;
import com.ravi.attendance.ui.attendance.AttendanceActivity;
import com.ravi.attendance.utils.ExcelReaderUtil;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int PICK_EXCEL_FILE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnImportExcel.setOnClickListener(v -> openFilePicker());

    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Excel File"), PICK_EXCEL_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_EXCEL_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            ExcelReaderUtil.readExcelAndInsertStudents(this, uri);
            Toast.makeText(this, "Excel Imported", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
            startActivity(intent);
        }
    }
}