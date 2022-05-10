package com.aanto.quickjoin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Button AddSub,ViewSub,AddTT,ViewTT,QuickJoin,AddAdmin;
    TextView Title;
    String Regulations,Department,Semester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AddSub = findViewById(R.id.add_sub);
        ViewSub = findViewById(R.id.view_sub);
        AddTT = findViewById(R.id.add_time);
        ViewTT = findViewById(R.id.view_time);
        QuickJoin = findViewById(R.id.quick_join);
        Title = findViewById(R.id.title);
        AddAdmin = findViewById(R.id.add_admin);

        SharedPreferences settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);
        Regulations = settings.getString(Constants.Regulations,null);
        Semester = settings.getString(Constants.Semester,null);
        Department = settings.getString(Constants.Department,null);
        Title.setText(Regulations + " - " + Semester + " - " + Department);
        AddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddSubjectActivity.class);
                startActivity(intent);
            }
        });
        ViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ViewSubjectActivity.class);
                startActivity(intent);
            }
        });
        AddTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddTimeTableActivity.class);
                startActivity(intent);
            }
        });
        ViewTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ViewTimeTableActivity.class);
                startActivity(intent);
            }
        });
        QuickJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,StudentHomeActivity.class);
                startActivity(intent);
            }
        });
        AddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddAdminActivity.class);
                startActivity(intent);
            }
        });

    }
}