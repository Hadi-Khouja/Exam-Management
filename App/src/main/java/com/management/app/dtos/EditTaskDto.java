package com.management.app.dtos;

import com.management.app.types.Exam;
import com.management.app.types.Task;

public class EditTaskDto {
    private Exam exam;
    private Task task;

    public EditTaskDto(Exam exam, Task task) {
        this.exam = exam;
        this.task = task;
    }

    public Exam getExam() {
        return exam;
    }

    public Task getTask() {
        return task;
    }
}
