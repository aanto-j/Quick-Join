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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aanto.quickjoin.model.Subjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ViewTimeTableFragment extends Fragment {
    TextView SubCode1,FacName1,SubName1,Time1,DispInfo1;
    TextView SubCode2,FacName2,SubName2,Time2,DispInfo2;
    TextView SubCode3,FacName3,SubName3,Time3,DispInfo3;
    TextView SubCode4,FacName4,SubName4,Time4,DispInfo4;
    TextView SubCode5,FacName5,SubName5,Time5,DispInfo5;
    Button MLink1,MLink2,MLink3,MLink4,MLink5;
    DatabaseReference reference;
    Spinner Dayspinner;
    String Regulations,Department;
    RelativeLayout subjectlayout1,subjectlayout2,subjectlayout3,subjectlayout4,subjectlayout5;


    public ViewTimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_view_time_table, container, false);
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getActivity().getSharedPreferences("Classroom", Context.MODE_PRIVATE);

        Dayspinner = rootview.findViewById(R.id.choose_day);
        subjectlayout1 = rootview.findViewById(R.id.subject1);
        subjectlayout2 = rootview.findViewById(R.id.subject2);
        subjectlayout3 = rootview.findViewById(R.id.subject3);
        subjectlayout4 = rootview.findViewById(R.id.subject4);
        subjectlayout5 = rootview.findViewById(R.id.subject5);

        SubCode1 = rootview.findViewById(R.id.sub_code_1);
        SubName1 = rootview.findViewById(R.id.sub_name_1);
        FacName1 = rootview.findViewById(R.id.facname_1);
        Time1 = rootview.findViewById(R.id.sub_time_1);
        DispInfo1 = rootview.findViewById(R.id.disp_info_1);
        MLink1 = rootview.findViewById(R.id.m_link_1);

        SubCode2 = rootview.findViewById(R.id.sub_code_2);
        SubName2 = rootview.findViewById(R.id.sub_name_2);
        FacName2 = rootview.findViewById(R.id.facname_2);
        Time2 = rootview.findViewById(R.id.sub_time_2);
        DispInfo2 = rootview.findViewById(R.id.disp_info_2);
        MLink2 = rootview.findViewById(R.id.m_link_2);


        SubCode3 = rootview.findViewById(R.id.sub_code_3);
        SubName3 = rootview.findViewById(R.id.sub_name_3);
        FacName3 = rootview.findViewById(R.id.facname_3);
        Time3 = rootview.findViewById(R.id.sub_time_3);
        DispInfo3 = rootview.findViewById(R.id.disp_info_3);
        MLink3 = rootview.findViewById(R.id.m_link_3);

        SubCode4 = rootview.findViewById(R.id.sub_code_4);
        SubName4 = rootview.findViewById(R.id.sub_name_4);
        FacName4 = rootview.findViewById(R.id.facname_4);
        Time4 = rootview.findViewById(R.id.sub_time_4);
        DispInfo4 = rootview.findViewById(R.id.disp_info_4);
        MLink4 = rootview.findViewById(R.id.m_link_4);

        SubCode5 = rootview.findViewById(R.id.sub_code_5);
        SubName5 = rootview.findViewById(R.id.sub_name_5);
        FacName5 = rootview.findViewById(R.id.facname_5);
        Time5 = rootview.findViewById(R.id.sub_time_5);
        DispInfo5 = rootview.findViewById(R.id.disp_info_5);
        MLink5 = rootview.findViewById(R.id.m_link_5);

        Regulations = settings.getString(Constants.Regulations,null);
        Department = settings.getString(Constants.Department,null);

        reference = FirebaseDatabase.getInstance().getReference();
        Dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subjectlayout1.setVisibility(View.VISIBLE);
                subjectlayout2.setVisibility(View.VISIBLE);
                subjectlayout3.setVisibility(View.VISIBLE);
                subjectlayout4.setVisibility(View.VISIBLE);
                subjectlayout5.setVisibility(View.VISIBLE);
                SetSubject1();
                SetSubject2();
                SetSubject3();
                SetSubject4();
                SetSubject5();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootview;

    }

    private void SetSubject1() {


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).exists()){

                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).child("09").getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child(Regulations).child(Department).child(Constants.Subjects).child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    //Toast.makeText(ViewTimeTableActivity.this, subdata.getSubject_name(), Toast.LENGTH_SHORT).show();
                    SubCode1.setText(subdata.getSubject_code());
                    SubName1.setText(subdata.getSubject_name());
                    FacName1.setText(subdata.getFaculty_name());
                    DispInfo1.setText(subdata.getRegulation()+"-"+subdata.getDepartment()+"-"+subdata.getSemester());
                    MLink1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    });

                }
                else {
                    subjectlayout1.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetSubject2() {


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).exists()){
                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).child("10").getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child(Regulations).child(Department).child(Constants.Subjects).child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    //Toast.makeText(ViewTimeTableActivity.this, subdata.getSubject_name(), Toast.LENGTH_SHORT).show();
                    SubCode2.setText(subdata.getSubject_code());
                    SubName2.setText(subdata.getSubject_name());
                    FacName2.setText(subdata.getFaculty_name());
                    DispInfo2.setText(subdata.getRegulation()+"-"+subdata.getDepartment()+"-"+subdata.getSemester());
                    MLink2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    });
                }
                else{
                    subjectlayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetSubject3() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).exists()){
                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).child("11").getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child(Regulations).child(Department).child(Constants.Subjects).child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    //Toast.makeText(ViewTimeTableActivity.this, subdata.getSubject_name(), Toast.LENGTH_SHORT).show();
                    SubCode3.setText(subdata.getSubject_code());
                    SubName3.setText(subdata.getSubject_name());
                    FacName3.setText(subdata.getFaculty_name());
                    DispInfo3.setText(subdata.getRegulation()+"-"+subdata.getDepartment()+"-"+subdata.getSemester());
                    MLink3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    });
                }
                else{
                    subjectlayout3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetSubject4() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).exists()){
                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).child("12").getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child(Regulations).child(Department).child(Constants.Subjects).child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    //Toast.makeText(ViewTimeTableActivity.this, subdata.getSubject_name(), Toast.LENGTH_SHORT).show();
                    SubCode4.setText(subdata.getSubject_code());
                    SubName4.setText(subdata.getSubject_name());
                    FacName4.setText(subdata.getFaculty_name());
                    DispInfo4.setText(subdata.getRegulation()+"-"+subdata.getDepartment()+"-"+subdata.getSemester());
                    MLink4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    });
                }
                else{
                    subjectlayout4.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void SetSubject5() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).exists()){
                    Subjects ttdata = snapshot.child(Regulations).child(Department).child(Constants.TimeTable).child(Dayspinner.getSelectedItem().toString().trim()).child("14").getValue(Subjects.class);
                    assert ttdata != null;
                    Subjects subdata = snapshot.child(Regulations).child(Department).child(Constants.Subjects).child(ttdata.getSubject_code()).getValue(Subjects.class);
                    assert subdata != null;
                    //Toast.makeText(ViewTimeTableActivity.this, subdata.getSubject_name(), Toast.LENGTH_SHORT).show();
                    SubCode5.setText(subdata.getSubject_code());
                    SubName5.setText(subdata.getSubject_name());
                    FacName5.setText(subdata.getFaculty_name());
                    DispInfo5.setText(subdata.getRegulation()+"-"+subdata.getDepartment()+"-"+subdata.getSemester());
                    MLink5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(subdata.getMeet_link());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                    });
                }
                else{
                    subjectlayout5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}