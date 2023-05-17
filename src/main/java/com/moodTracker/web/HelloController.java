package com.moodTracker.web;

import com.moodTracker.config.auth.dto.SessionUser;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.service.mood.MoodService;
import com.moodTracker.service.task.TaskService;
import com.moodTracker.web.dto.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class HelloController {

    private final HttpSession httpSession;
    private final MoodService moodService;
    private final TaskService taskService;
    private final UserRepository userRepository;

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

        Long user_id = (Long) httpSession.getAttribute("user_id");

        int average_mood_level = moodService.getAverageMoodLevel(user_id);

        if (average_mood_level!=0){
            model.addAttribute("pastWeekLevel", average_mood_level);
        }
        return "moodtracker";
    }

    @GetMapping("/taskManager")
    public String newTask(Model model){
        Long user_id = (Long) httpSession.getAttribute("user_id");

        Users users = userRepository.findById(user_id).get();

        List<TaskResponseDto> taskResponseDtoList = taskService.findByUsers(users);

        model.addAttribute(taskResponseDtoList);

        return "taskManager";
    }
//
//    @GetMapping("/selectedLevel")
//    public String selectedLevel(@RequestParam int level, Model model){
//
//    }
}
