package com.aanto.quickjoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class AddTimeTableActivity extends AppCompatActivity {
    Spinner SelectDay,SelectSub,SelectTime;
    Button AddTT;
    String Regulations,Department,Semester,HoursTime;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);
        SharedPreferences settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);

        SelectDay = findViewById(R.id.select_day);
        SelectSub = findViewById(R.id.select_sub);
        SelectTime = findViewById(R.id.select_time);
        Regulations = settings.getString(Constants.Regulations,null);
        Department = settings.getString(Constants.Department,null);
        Semester = settings.getString(Constants.Semester,null);
        AddTT = findViewById(R.id.addSubjects);

        getSubjects();

        AddTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (SelectTime.getSelectedItemPosition()){
                    case 0:
                        HoursTime = "09";
                        break;
                    case 1:
                        HoursTime = "10";
                        break;
                    case 2:
                        HoursTime = "11";
                        break;
                    case 3:
                        HoursTime = "12";
                        break;
                    case 4:
                        HoursTime = "14";
                        break;
                }
                reference = FirebaseDatabase.getInstance().getReference();
                HashMap<String,Object> Subjects = new HashMap<>();
                Subjects.put(Constants.Day,SelectDay.getSelectedItem().toString().trim());
                Subjects.put(Constants.Subject_Code,SelectSub.getSelectedItem().toString().trim());
                Subjects.put(Constants.Time,HoursTime);
                reference.child(Regulations).child(Department).child(Constants.TimeTable).child(SelectDay.getSelectedItem().toString().trim())
                        .child(HoursTime).updateChildren(Subjects).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddTimeTableActivity.this, "Added to Time Table", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getSubjects(){
        reference = FirebaseDatabase.getInstance().getReference().child(Regulations).child(Department).child(Constants.Subjects);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> subarray = new ArrayList<String>();
                for (DataSnapshot subsnapshot: snapshot.getChildren() ){
                    String subname = subsnapshot.child(Constants.Subject_Code).getValue(String.class);
                    subarray.add(subname);
                }
                ArrayAdapter<String> subadapter = new ArrayAdapter<String>(AddTimeTableActivity.this,android.R.layout.simple_spinner_item,subarray);
                subadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SelectSub.setAdapter(subadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}