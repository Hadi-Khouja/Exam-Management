package com.management.app.dtos;

import com.management.app.types.Student;

public class EditStudentDto {
    private String examId;
    private Student student;

    public EditStudentDto(String examId, Student student) {
        this.examId = examId;
        this.student = student;
    }

    public String getExamId() {
        return examId;
    }

    public Student getStudent() {
        return student;
    }
}
