package com.moodTracker.domain.user_mood;

import com.moodTracker.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface MoodRepository extends JpaRepository<Mood, Long> {

    List<Mood> findAllByUsers(Users users);

//    @Query("SELECT m FROM Mood m WHERE m.daily_mood_id IN (SELECT daily_mood_id FROM Mood WHERE created_date >= :start_date AND created_date <= :modified_date)")
//    List<Mood> findThisWeeksMoodsByUsers(@Param("startDate") LocalDate start_date, @Param("endDate") LocalDate modified_date, Users users);
//
    @Query("SELECT m FROM Mood m WHERE m.users = :users AND m.daily_mood_id IN (SELECT daily_mood_id FROM Mood WHERE created_date >= :startDate AND created_date <= :endDate)")

    List<Mood> findThisWeeksMoodsByUsers(
            LocalDate startDate,
            LocalDate endDate,
            Users users
    );

}
