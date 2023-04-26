package com.moodTracker.web;

import com.moodTracker.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class HelloController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String hello(Model model){

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if(sessionUser != null){
            model.addAttribute("userName", sessionUser.getName());
        }

        return "index";
    }

    @GetMapping("/moodtracker")
    public String moodtracker (){
        return "moodtracker";
    }
}
