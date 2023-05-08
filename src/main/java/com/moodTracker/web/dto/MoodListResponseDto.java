package com.moodTracker.web.dto;

import com.moodTracker.domain.user_mood.Mood;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MoodListResponseDto {
    private int mood_level;

//    public int getMood_Level(){
//        return mood_level;
//    }

    @Builder
    public MoodListResponseDto(Mood entity){
        this.mood_level=entity.getMood_level();
    }
}
