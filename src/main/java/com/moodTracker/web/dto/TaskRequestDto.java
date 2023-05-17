package com.moodTracker.web.dto;

import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.users.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskRequestDto {
    private Users users;
    private String task_name;
    private int task_level;

    @Builder
    public TaskRequestDto(Users users, String task_name, int task_level){
        this.users = users;
        this.task_name = task_name;
        this.task_level = task_level;
    }

    public Task toEntity(){
        return Task.builder()
                .users(users)
                .task_name(task_name)
                .task_level(task_level)
                .build();
    }
}
