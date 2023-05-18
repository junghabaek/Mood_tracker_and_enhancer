package com.moodTracker.web;

import com.moodTracker.domain.finishedTasks.FinishedTasks;
import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.service.finishedTask.FinishedTasksService;
import com.moodTracker.service.mood.MoodService;
import com.moodTracker.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MoodTrackerController {
    private final MoodService moodService;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final FinishedTasksService finishedTasksService;
    private final HttpSession httpSession;

    @GetMapping("/selectedLevel")
    public String selected_level(@RequestParam("selected_level") String selected_level, Model model){
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

    @GetMapping("/level_selected")
    public String level_selected(@RequestParam("task_list") List<String> tasks,
                                       @RequestParam("selected_level") int selected_level, Model model){

        Long user_id = (Long) httpSession.getAttribute("user_id");

        for(String finishedTask_id : tasks){
            System.out.println(finishedTask_id);
//여기에 오늘 한 일들을 추가해주는 sql이 필요함
//            finishedTasksService.update(user_id, Long.parseLong(finishedTask_id));
        }
//        System.out.println(selected_level);
        // 점수든 갯수든간에 레벨이 결정되면, selected_level에다가 넣어줘서 selectedLevel에 보내줘야함.
        // 어디로 보낼지 정하는 알고리즘이 필요하고 -- 갯수제로 하자
        // 오늘 한 것을 토대로 Done 버튼이 필요함.

        int nextLevel = ReturnSelectedLevel(tasks.size(), selected_level);

//        System.out.println(nextLevel);

        model.addAttribute("selected_level", nextLevel);
        //여기에 로직이 들어가야함

        List<Task> taskList = taskRepository.findAllByTaskLevel(nextLevel);

        model.addAttribute("taskList", taskList);


        return "level_selected";

    }

    public int ReturnSelectedLevel(int numberOfSelection, int currentLevel){
        int nextLevel = (numberOfSelection-2)+currentLevel;

        if (nextLevel<1){
            nextLevel=1;
        }else if(nextLevel>6){
            nextLevel=6;
        }

        return nextLevel;

    }

}
