package com.moodTracker.web;

import com.moodTracker.domain.finishedTasks.FinishedTasks;
import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import com.moodTracker.domain.user_mood.Mood;
import com.moodTracker.domain.user_mood.MoodRepository;
import com.moodTracker.domain.users.UserRepository;
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
    private final UserRepository userRepository;
    private final MoodRepository moodRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final FinishedTasksService finishedTasksService;
    private final HttpSession httpSession;

///v1/selected_level_resolver
    @GetMapping("/v1/selected_level_resolver")
    public String selected_level(@RequestParam("selected_level") String selected_level, Model model){
//        System.out.println(selected_level);

        model.addAttribute("selected_level", selected_level);
//        List<Integer> level = new ArrayList<>();
//        level.add(Integer.parseInt(selected_level));
        List<Task> taskList = taskRepository.findAllByTaskLevel(Integer.parseInt(selected_level));

        List<String> taskListString;

        for (Task task : taskList){
            System.out.println(task.getTask_name());

        }

        model.addAttribute("taskList", taskList);
        return "level_selected";
    }

    @GetMapping("/v1/selected_tasks_resolver")
    public String level_selected(@RequestParam("task_list") String tasks,
                                       @RequestParam("selected_level") int selected_level, Model model){

        Long user_id = (Long) httpSession.getAttribute("user_id");
        Users user = userRepository.findById(user_id).orElseThrow(()->new IllegalArgumentException("해당 id를 가진 유저가 없습니다"));
        System.out.println(tasks);


        List<String> taskList = new ArrayList<>();


        if (tasks.equals("[]")) {
            if (selected_level<3) {
                Mood mood = new Mood(user, selected_level);
                moodRepository.save(mood);
                model.addAttribute("userName", user.getName());
                return "index";
            }

        }else {
            tasks = tasks.substring(1, tasks.length() - 1);

            String[] elements = tasks.split("\",\"");

            for (String element : elements) {
                String trimmedElement = element.replaceAll("\"", "");
                taskList.add(trimmedElement);
            }

            for (String finishedTask_id : taskList) {
                System.out.println(finishedTask_id);
                finishedTasksService.update(user_id, Long.parseLong(finishedTask_id));
                //업데이트까지 잘 완료되는것을 확인했습니다.
                //이제는 종료되는 로직을 짜야 할 차례입니다.
                //바로 뒷줄에서 쩌야한다.
            }

            // 어떤 레벨에서 태스크를 두개 실행했거나, 레벨이 1일때 2개 이하로 실행했거나,
            // 레벨이 6일때 2개 이상 실행했으면 무드레벨이 정해짐.
            if (taskList.size()==2 || (selected_level==1 && taskList.size()<2)
                    || (selected_level==6 && taskList.size()>2)){
                Mood mood = new Mood(user, selected_level);
                moodRepository.save(mood);
                model.addAttribute("userName", user.getName());
                return "index";
            }

        }

        //

        // 태스크를 몇개 했는지에 따라서 레벨이 변동되는 로직입니다.
        // 어떤 레벨에 태스크가 정확히 2개를 실행하게 되면 종료가 되게 됩니다.
        // 그때까지의 태스크들은 모두 db에 저장이 되게 됩니다.
        // 종료조건은 세가지입니다.
        // 1. 레벨 1에서 1개 이하의 일을 수행할 수 있었다. -- mood level = 1
        // 2. 모든 레벨에서 정확히 2개의 일을 수행할 수 있었다 -- mood level = selected_level
        // 3. 레벨 6에서 3개 이상의 일을 수행할 수 있었다 -- mood level =6


        //아래 로직은 한 레벨에서 2개를 선택했을 때는 필요 없는 것들이다.

        int nextLevel = returnSelectedLevel(taskList.size(), selected_level);

        System.out.println(nextLevel);

        model.addAttribute("selected_level", nextLevel);

        List<Task> taskList_from_db = taskRepository.findAllByTaskLevel(nextLevel);

        model.addAttribute("taskList", taskList_from_db);
//
//
        return "level_selected";

    }




    public int returnSelectedLevel(int numberOfSelection, int currentLevel){
        int nextLevel = (numberOfSelection-2)+currentLevel;

        if (nextLevel<1){
            nextLevel=1;
        }else if(nextLevel>6){
            nextLevel=6;
        }

        return nextLevel;

    }



}
