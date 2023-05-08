package com.moodTracker.web;

import com.moodTracker.config.auth.dto.SessionUser;
import com.moodTracker.service.mood.MoodService;
import com.moodTracker.web.dto.MoodListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HelloController {

    private final HttpSession httpSession;
    private final MoodService moodService;

    @GetMapping("/")
    public String hello(Model model){

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if(sessionUser != null){
            model.addAttribute("userName", sessionUser.getName());
        }

        return "index";
    }

    @GetMapping("/moodtracker")
    public String moodtracker (Model model){

        int average_mood_level = moodService.getAverageMoodLevel();

        if (average_mood_level!=0){
            model.addAttribute("pastWeekLevel", average_mood_level);
        }
        return "moodtracker";
    }
}
