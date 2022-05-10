package com.aanto.quickjoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button Continue_btn,Admin_btn;
    Spinner Regulation,Semester,Department;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Continue_btn = findViewById(R.id.cont);
        Admin_btn = findViewById(R.id.admin_cont);

        Regulation = findViewById(R.id.regulation);
        Department = findViewById(R.id.department);
        Semester = findViewById(R.id.semester);

        Continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("regulation",Regulation.getSelectedItem().toString());
                editor.putString("department",Department.getSelectedItem().toString());
                editor.putString("semester",Semester.getSelectedItem().toString());
                editor.commit();
                Intent intent = new Intent(MainActivity.this,StudentHomeActivity.class);
                startActivity(intent);
            }
        });
        Admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("regulation",Regulation.getSelectedItem().toString());
                editor.putString("department",Department.getSelectedItem().toString());
                editor.putString("semester",Semester.getSelectedItem().toString());
                editor.commit();
                Intent intent = new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}