package com.moodTracker.web;

import com.moodTracker.config.auth.dto.SessionUser;
import com.moodTracker.domain.user_mood.Mood;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.service.mood.MoodService;
import com.moodTracker.service.task.TaskService;
import com.moodTracker.web.dto.TaskResponseDto;
import com.moodTracker.web.vo.Mood_vo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @GetMapping("/v1/mood_tracker")
    public String moodtracker (Model model){

        Long user_id = (Long) httpSession.getAttribute("user_id");


        if (user_id==null){
            model.addAttribute("pastWeekLevel", -1);
            return "mood_tracker";
        }

        int average_mood_level = moodService.getAverageMoodLevel(user_id);

        if (average_mood_level!=0){
            model.addAttribute("pastWeekLevel", average_mood_level);
        }
        return "mood_tracker";
    }

    @GetMapping("/v1/task_manager")
    public String newTask(Model model){
        System.out.println(httpSession.getAttribute("user_id"));

        Long user_id = (Long) httpSession.getAttribute("user_id");
//        System.out.println("hasdf");
//        System.out.println(user_id);

        Users users = userRepository.findById(user_id).get();

        List<TaskResponseDto> taskResponseDtoList = taskService.findByUsers(users);

        model.addAttribute(taskResponseDtoList);

        return "task_manager";
    }

    @GetMapping("/v1/mood_history")
    public String moodhistory(){



        return "mood_history";

    }

    @GetMapping("/v1/mood_history_resolver")
    public String moodhistoryResolver(@RequestParam("startDate")String startDate,
                                      @RequestParam("endDate") String endDate, Model model){
        Long user_id = (Long) httpSession.getAttribute("user_id");

        Users users = userRepository.findById(user_id).get();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localStartDate = LocalDate.parse(startDate, formatter);
        LocalDate localEndDate = LocalDate.parse(endDate, formatter);
        localEndDate=localEndDate.plusDays(1);
        System.out.println(localStartDate);
        System.out.println(localEndDate);
        List<Mood> tempMoodListBetweenDates = moodService.getAverageMoodLevelBetweenDates (user_id, localStartDate, localEndDate);
        //여기서 Mood를 받아왔고,
        //Mood에 있는 LocaDate의 값을 바꿔서 저장하자. 왜냐면 그냥 그대로 받아오면 날짜가,
        // 2023-05-16 12:29:19.814896 이런식으로 다나와서 가독성이 떨어짐
        // 2023-05-16 이렇게만 바꿔주고싶음

        List<Mood_vo> moodListBetweenDates = new ArrayList<>();

        for (Mood mood : tempMoodListBetweenDates){
            String strippedDate = mood.getCreatedDate().toString();
            strippedDate=strippedDate.substring(0, 10);
            moodListBetweenDates.add(new Mood_vo(strippedDate, mood.getMood_level()));
        }


        int total = 0;

        for (Mood_vo mood : moodListBetweenDates){
            total+= mood.getMood_level();
        }
        if (moodListBetweenDates.size()==0){
            total =0;
        } else {
            total = (int) Math.ceil(total / moodListBetweenDates.size());
        }

        int averageMood = total;

        model.addAttribute("moodListBetweenDates", moodListBetweenDates);

        model.addAttribute("averageMood", averageMood);


        return "mood_history";
    }

}
