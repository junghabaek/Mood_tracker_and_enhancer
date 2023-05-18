package com.moodTracker.service.finishedTask;

import com.moodTracker.domain.finishedTasks.FinishedTasks;
import com.moodTracker.domain.finishedTasks.FinishedTasksRepository;
import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FinishedTasksService {
    private final FinishedTasksRepository finishedTasksRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    @Transactional
    public void update(Long user_id, Long finishedTask_id){
        Users user = userRepository.findById(user_id).orElseThrow(()-> new IllegalArgumentException("해당 id를 가진 유저를 찾을 수 없습니다"));
        Task task = taskRepository.findById(finishedTask_id).orElseThrow(()-> new IllegalArgumentException("해당 id를 가진 task를 찾을 수 없습니다"));
        FinishedTasks finishedTasks = FinishedTasks.builder().users(user).task(task).build();
        finishedTasksRepository.save(finishedTasks);

    }
}
