package com.moodTracker.service.task;

import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.web.dto.TaskRequestDto;
import com.moodTracker.web.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public Task createNewTask(TaskRequestDto taskRequestDto){

        return taskRepository.save(taskRequestDto.toEntity());
    }

    @Transactional
    public List<TaskResponseDto> findByUsers(Users users){
        return taskRepository.findAllByUsers(users)
                .stream()
                .map(TaskResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long task_id, TaskRequestDto taskRequestDto){
        Task task = taskRepository.findById(task_id).orElseThrow(()->new IllegalArgumentException("no such task" + task_id));
        task.update(taskRequestDto.getTask_name(), taskRequestDto.getTask_level());
    }
}
