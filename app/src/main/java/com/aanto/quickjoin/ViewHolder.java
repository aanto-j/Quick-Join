package com.aanto.quickjoin;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView SubjectCode,DisplayInfo,FacultyName,SubjectName;
    Button MeetLink;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        SubjectCode = itemView.findViewById(R.id.sub_code);
        SubjectName = itemView.findViewById(R.id.sub_name);
        DisplayInfo = itemView.findViewById(R.id.disp_info);
        FacultyName = itemView.findViewById(R.id.facname);
        MeetLink = itemView.findViewById(R.id.m_link);

    }
}
