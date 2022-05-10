package com.aanto.quickjoin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aanto.quickjoin.model.Subjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QuickJoinFragment extends Fragment {
    Button quickjoin;
    String currentday = "",currenttime;
    DatabaseReference reference;
    TextView textsub;
    SharedPreferences settings;
    String Regulations,Department;
    public QuickJoinFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_quick_join, container, false);
        settings = getActivity().getSharedPreferences("Classroom", Context.MODE_PRIVATE);
        quickjoin = rootview.findViewById(R.id.quick_join);
        textsub = rootview.findViewById(R.id.textsub);
        Regulations = settings.getString(Constants.Regulations,null);
        Department = settings.getString(Constants.Department,null);

        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        currenttime = sdf.format(new Date());

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                currentday = "Sunday";
                break;
            case Calendar.MONDAY:
                currentday =  "Monday";
                break;
            case Calendar.TUESDAY:
                currentday =  "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                currentday = "Wednesday";
                break;
            case Calendar.THURSDAY:
                currentday = "Thursday";
                break;
            case Calendar.FRIDAY:
                currentday = "Friday";
                break;
            case Calendar.SATURDAY:
                currentday = "Saturday";
                break;
        }

        GetMeetlink();

        return rootview;

    }

    private void GetMeetlink() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(currentday).child(currenttime).exists()){
                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(currentday).child(currenttime).getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child("2017").child("CSE").child("Subjects").child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    textsub.setText(subdata.getSubject_code() +" - "+subdata.getSubject_name());
                    quickjoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);

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