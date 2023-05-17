package com.moodTracker.web.dto;

import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.users.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskResponseDto {
    private Long task_id;
    private Users users;
    private String task_name;
    private int task_level;

    public TaskResponseDto(Task task){
        this.task_id=task.getTask_id();
        this.users=task.getUsers();
        this.task_name=task.getTask_name();
        this.task_level=task.getTaskLevel();
    }
}