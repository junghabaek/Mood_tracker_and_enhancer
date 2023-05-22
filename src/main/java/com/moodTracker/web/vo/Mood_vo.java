package com.moodTracker.web.vo;

import lombok.Getter;

@Getter
public class Mood_vo {
    private String strippedDate;
    private int mood_level;

    public Mood_vo(String strippedDate, int mood_level){
        this.strippedDate=strippedDate;
        this.mood_level=mood_level;
    }
}
