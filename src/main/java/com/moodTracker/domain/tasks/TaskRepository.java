package com.moodTracker.domain.tasks;

import com.moodTracker.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUsers(Users users);

//    @Query("SELECT * FROM Task where task_level=task_level")
//    List<Task> findAllByTask_level(int task_level);

    @Query("SELECT t FROM Task t WHERE t.taskLevel = :taskLevel")
    List<Task> findAllByTaskLevel(@Param("taskLevel") int taskLevel);
}
