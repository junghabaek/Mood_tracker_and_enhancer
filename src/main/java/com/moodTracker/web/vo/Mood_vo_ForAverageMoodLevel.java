package com.moodTracker.web.vo;

import com.moodTracker.domain.user_mood.Mood;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Mood_vo_ForAverageMoodLevel {
    private int mood_level;

//    public int getMood_Level(){
//        return mood_level;
//    }

    @Builder
    public Mood_vo_ForAverageMoodLevel(Mood entity){
        this.mood_level=entity.getMood_level();
    }
}