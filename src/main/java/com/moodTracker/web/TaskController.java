package com.moodTracker.web;

import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.service.task.TaskService;
import com.moodTracker.web.dto.TaskRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final HttpSession httpSession;


    @PostMapping("/v1/POST/tasks")
    public RedirectView createTask(@RequestParam("task_name") String task_name, @RequestParam("task_level") String task_level) {

        Long user_id = (Long) httpSession.getAttribute("user_id");
        Users users = userRepository.findById(user_id).get();

        TaskRequestDto taskRequestDto = new TaskRequestDto(users, task_name, Integer.parseInt(task_level));

        taskService.createNewTask(taskRequestDto);

//        taskService.createNewTask(users, task_name, task_level);
        return new RedirectView("/v1/task_manager");
    }

    //
    @GetMapping("/v1/task_to_modify/{task_id}")
    public String modifyTask (@PathVariable Long task_id, Model model){
        Task task = taskRepository.findById(task_id).get();
        model.addAttribute(task);
        return "modify_task";
    }

    @PutMapping("/v1/PUT/task/{task_id}")
    public String updateTask(@PathVariable Long task_id, @RequestBody TaskRequestDto taskRequestDto){
        taskService.update(task_id, taskRequestDto);
        return "task_manager";
    }

    @DeleteMapping("/v1/DELETE/task/{task_id}")
    public String deleteTask(@PathVariable Long task_id){
        taskRepository.deleteById(task_id);
        return "task_manager";

    }
}
