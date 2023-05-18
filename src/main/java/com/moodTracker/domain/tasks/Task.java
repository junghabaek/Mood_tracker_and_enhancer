package com.moodTracker.domain.tasks;

import com.moodTracker.domain.BaseTimeEntity;
import com.moodTracker.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="task")
public class Task extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "non_fk_task_id")
    private Long task_id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Users users;

    @Column(nullable = false)
    private String task_name;

    @Column(name="task_level", nullable = false)
    private int taskLevel;

    @Builder
    public Task(Users users, String task_name, int task_level){
        this.users=users;
        this.task_name=task_name;
        this.taskLevel=task_level;
    }

    public void update(String task_name, int task_level){
        this.task_name=task_name;
        this.taskLevel=task_level;
    }
}
