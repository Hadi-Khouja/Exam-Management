package com.management.app.dtos;

import com.management.app.types.Course;
import com.management.app.types.Exam;

public class EditExamDto {
    private final Course course;
    private final Exam exam;

    public EditExamDto(Course course, Exam exam) {
        this.course = course;
        this.exam = exam;
    }

    public Course getCourse() {
        return course;
    }

    public Exam getExam() {
        return exam;
    }
}
