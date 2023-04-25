package com.moodTracker.domain.user_mood;

import com.moodTracker.domain.BaseTimeEntity;
import com.moodTracker.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Mood extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="non_fk_daily_mood_id")
    private Long daily_mood_id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Users users;

    @Column(nullable = false)
    private int mood_level;

    @Builder
    public Mood(Users users, int mood_level){
        this.users=users;
        this.mood_level=mood_level;
    }
}
