package com.management.app.types;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {
    @Test
    public void addExamTest() {
        Course course = new Course("1", "test course");

        course.addExam(new Exam("testexam"));
        int result = course.getExams().size();

        assertEquals(1, result);
    }
}
