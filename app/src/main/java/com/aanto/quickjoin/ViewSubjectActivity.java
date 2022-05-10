package com.aanto.quickjoin;

import static android.content.Context.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aanto.quickjoin.model.Subjects;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Set;

public class ViewSubjectActivity extends AppCompatActivity{
    String Regulations,Department,Semester;
    RecyclerView recycleview;
    RecyclerView.LayoutManager layoutmanager;
    DatabaseReference reference;
    FirebaseRecyclerOptions<Subjects> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);
        SharedPreferences settings = getSharedPreferences("Classroom", Context.MODE_PRIVATE);

        Regulations = settings.getString(Constants.Regulations,null);
        Semester = settings.getString(Constants.Semester,null);
        Department = settings.getString(Constants.Department,null);

        recycleview = findViewById(R.id.recyclerview);
        layoutmanager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(layoutmanager);
        reference = FirebaseDatabase.getInstance().getReference().child(Regulations).child(Department).child(Constants.Subjects);
        viewSubjects();

    }

    private void viewSubjects() {
        options = new FirebaseRecyclerOptions.Builder<Subjects>()
                .setQuery(reference,Subjects.class)
                .build();
        FirebaseRecyclerAdapter<Subjects,ViewHolder> adapter
                = new FirebaseRecyclerAdapter<Subjects, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Subjects Subjects) {
                viewHolder.FacultyName.setText(Subjects.getFaculty_name());
                viewHolder.SubjectCode.setText(Subjects.getSubject_code());
                viewHolder.SubjectName.setText(Subjects.getSubject_name());
                viewHolder.DisplayInfo.setText(Subjects.getRegulation()+" "+Subjects.getDepartment()+" "+Subjects.getSemester());
                viewHolder.MeetLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(Subjects.getMeet_link());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_layout,parent,false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }
        };
        recycleview.setAdapter(adapter);
        adapter.startListening();
    }
}