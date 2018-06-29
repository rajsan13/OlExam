package com.example.Aphexams;

/**
 * Created by HP on 24-05-2018.
 */

public class StudentId {
    private String StudentId;
    private String StudentMark,StudentEmailid,StudentPhno;

    public String getStudentId() {
        return StudentId;
    }

    public String getStudentMark() {
        return StudentMark;
    }

    public String getStudentPhno() {
        return StudentPhno;
    }

    public void setStudentPhno(String studentPhno) {
        StudentPhno = studentPhno;
    }

    public String getStudentEmailid() {
        return StudentEmailid;
    }

    public void setStudentEmailid(String studentEmailid) {
        StudentEmailid = studentEmailid;
    }

    public void setStudentMark(String studentMark) {
        StudentMark = studentMark;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }
}
