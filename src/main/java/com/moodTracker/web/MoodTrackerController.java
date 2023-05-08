package com.moodTracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoodTrackerController {

    @GetMapping("/selectedLevel")
    public String selected_level(@RequestParam("selected_option") String selected_level, Model model){
        System.out.println(selected_level);
        model.addAttribute("selected_level", selected_level);
        return "level_selected";
    }
}
