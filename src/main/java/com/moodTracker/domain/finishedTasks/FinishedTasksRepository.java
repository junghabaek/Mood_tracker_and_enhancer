package com.moodTracker.domain.finishedTasks;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedTasksRepository extends JpaRepository<FinishedTasks, Long> {
}
