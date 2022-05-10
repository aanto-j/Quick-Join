package com.aanto.quickjoin.model;

public class Subjects {
    private String department,faculty_name,meet_link,regulation,semester,subject_code,subject_name;
    public Subjects(){

    }

    public Subjects(String department, String faculty_name, String meet_link, String regulation, String semester, String subject_code, String subject_name) {
        this.department = department;
        this.faculty_name = faculty_name;
        this.meet_link = meet_link;
        this.regulation = regulation;
        this.semester = semester;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getMeet_link() {
        return meet_link;
    }

    public void setMeet_link(String meet_link) {
        this.meet_link = meet_link;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
