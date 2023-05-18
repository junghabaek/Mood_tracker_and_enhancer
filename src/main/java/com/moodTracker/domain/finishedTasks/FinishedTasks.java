package com.moodTracker.domain.finishedTasks;

import com.moodTracker.domain.BaseTimeEntity;
import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class FinishedTasks extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fk_finishedTask_id")
    private Long finishedTask_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Users users;

    @Builder
    public FinishedTasks(Long finishedTask_id, Task task, Users users){
        this.finishedTask_id=finishedTask_id;
        this.task=task;
        this.users=users;
    }


}
