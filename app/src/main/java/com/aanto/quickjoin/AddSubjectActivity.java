package com.aanto.quickjoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {
    Button AddSub,AddFac;
    EditText SubCode,SubName,MLink;
    DatabaseReference reference;
    String Regulations,Department,Semester;
    Spinner FacName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        SharedPreferences settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);

        Regulations = settings.getString(Constants.Regulations,null);
        Semester = settings.getString(Constants.Semester,null);
        Department = settings.getString(Constants.Department,null);

        AddSub = findViewById(R.id.add_sub);
        SubCode = findViewById(R.id.sub_code);
        SubName = findViewById(R.id.sub_name);
        FacName = findViewById(R.id.fac_name);
        MLink = findViewById(R.id.m_link);
        AddFac = findViewById(R.id.add_fac);
        getFacultySpinner();

        AddFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddSubjectActivity.this,AddFacultyActivity.class);
                startActivity(intent);
            }
        });

        AddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateSubject();

                }
            });


    }

    private void getFacultySpinner() {
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> facultyarray = new ArrayList<String>();
                for (DataSnapshot facultysnapshot: snapshot.getChildren() ){
                    String facultyname = facultysnapshot.child(Constants.Faculty_Name).getValue(String.class);
                    facultyarray.add(facultyname);
                }
                ArrayAdapter<String> facultyadapter = new ArrayAdapter<String>(AddSubjectActivity.this,android.R.layout.simple_spinner_item,facultyarray);
                facultyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                FacName.setAdapter(facultyadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ValidateSubject()
    {
        if(SubCode.getText().toString().equals(""))  {
            Toast.makeText(AddSubjectActivity.this, "Please enter a Subject Code", Toast.LENGTH_SHORT).show();
        }
        else if(SubName.getText().toString().equals(""))  {
            Toast.makeText(AddSubjectActivity.this, "Please enter a Subject Name", Toast.LENGTH_SHORT).show();
        }
        else if(MLink.getText().toString().equals(""))  {
            Toast.makeText(AddSubjectActivity.this, "Please enter a Meet Link", Toast.LENGTH_SHORT).show();
        }
        else{
            AddsubjectDatabase();
        }

    }

    private void AddsubjectDatabase()
    {
        reference = FirebaseDatabase.getInstance().getReference().child(Regulations).child(Department).child(Constants.Subjects).child(SubCode.getText().toString().trim());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(AddSubjectActivity.this, "Subject Already Added", Toast.LENGTH_SHORT).show();
                }

                else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put(Constants.Subject_Code, SubCode.getText().toString().trim());
                    map.put(Constants.Subject_Name, SubName.getText().toString().trim());
                    map.put(Constants.Faculty_Name, FacName.getSelectedItem().toString());
                    map.put(Constants.Meet_Link, MLink.getText().toString().trim());
                    map.put(Constants.Semester,Semester);
                    map.put(Constants.Regulations,Regulations);
                    map.put(Constants.Department,Department);
                    reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddSubjectActivity.this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AddSubjectActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}