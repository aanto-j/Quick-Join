package com.aanto.quickjoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddFacultyActivity extends AppCompatActivity {
    EditText FacultyName,FacultyID;
    Button AddFac;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        FacultyName = findViewById(R.id.faculty_name);
        FacultyID = findViewById(R.id.fac_id);
        AddFac = findViewById(R.id.addfac);

        AddFac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = FirebaseDatabase.getInstance().getReference().child("Faculty").child(FacultyID.getText().toString());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put(Constants.Faculty_Name, FacultyName.getText().toString().trim());
                        map.put(Constants.Faculty_ID,FacultyID.getText().toString().trim());
                        ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(AddFacultyActivity.this, "Faculty Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddFacultyActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}