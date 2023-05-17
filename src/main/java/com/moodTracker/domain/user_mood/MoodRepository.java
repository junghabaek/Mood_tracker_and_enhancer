package com.moodTracker.domain.user_mood;

import com.moodTracker.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood, Long> {

    List<Mood> findAllByUsers(Users users);
}
