package com.moodTracker.service.mood;

import com.moodTracker.domain.user_mood.Mood;
import com.moodTracker.domain.user_mood.MoodRepository;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import com.moodTracker.web.dto.MoodListResponseDto;
import com.moodTracker.web.vo.Mood_vo_ForAverageMoodLevel;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MoodService {
    private final MoodRepository moodRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
//    public List<MoodListResponseDto> findAllById(){
//        moodRepository.findAllById().stream()
//                .map(MoodListResponseDto::new)
//                .collect(Collectors.toList());
    public int getAverageMoodLevel (Long user_id){
        System.out.println(user_id);
        Users users = userRepository.findById(user_id)
                .orElseThrow(()-> new IllegalArgumentException("해당 id를 가진 유저를 찾을 수 없습니다"));

//        List<Mood_vo_ForAverageMoodLevel> moodVoForAverageMoodLevels=
//                moodRepository.findAllByUsers(users).stream()
//                .map(Mood_vo_ForAverageMoodLevel::new)
//                .collect(Collectors.toList());

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(6);
        List<Mood> moodList = moodRepository.findThisWeeksMoodsByUsers(startDate, endDate, users);
//        List<Mood> moodList = moodRepository.findThisWeeksMoodsByUsers(users.getUser_id());
//        List<Mood> moods = moodRepository.findMoods(users);
        // 사실 직접 유저를 이용해서 List<Mood>를 받아와서 하나하나 꺼내서 쓸 수 있다.
        // 하지만 그렇게 하지 않고 Dto를 굳이 만들어 쓰는 이유는
        // Mood라는 엔티티의 값이 우연히라도 변하는 것을 방지하기 위해서이다.
        // 위의 말이 사실 틀린것 같기도 한데, 현업에서는 어떻게 쓸지 궁금하다.
        // 인터넷의 글들이 다 다른걸 보면 딱히 정해진건 없는듯 하다.
//        return 1;
        int total =0;

        for (Mood mood : moodList){
            total+= mood.getMood_level();
        }
        if (moodList.size()==0){
            return 0;
        } else {
            return (int) (total / moodList.size());
        }
    }



}
