package com.moodTracker.web;

import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.service.mood.MoodService;
import com.moodTracker.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MoodTrackerController {
    private final MoodService moodService;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @GetMapping("/selectedLevel")
    public String selected_level(@RequestParam("selected_option") String selected_level, Model model){
//        System.out.println(selected_level);

        model.addAttribute("selected_level", selected_level);
//        List<Integer> level = new ArrayList<>();
//        level.add(Integer.parseInt(selected_level));
        List<Task> taskList = taskRepository.findAllByTaskLevel(Integer.parseInt(selected_level));

        for (Task task : taskList){
            System.out.println(task.getTask_name());
        }

        model.addAttribute("taskList", taskList);
        return "level_selected";
    }

}
