package com.moodTracker.domain.user_mood;

import com.moodTracker.domain.users.Role;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback(true)
class MoodRepositoryTest {

//    private final MoodRepository moodRepository;
    // 굳이 @Autowired를 안써도 되지 않을까? spring에서 권장하는 방법인 생성자로 받아와보자.
    // 안되네...???...

    @Autowired
    MoodRepository moodRepository;

//    private final UserRepository userRepository;

    @Autowired
    UserRepository userRepository;

    private Users user;

    @BeforeEach
    void setup(){
        moodRepository.deleteAll();
        userRepository.deleteAll();
        //위의 코드에서 순서를 바꾸면 에러가 뜨는데, 왜냐면 Mood table과 Users table의 관계때문이다.
        //유저테이블의 데이터를 먼저 삭제하고 mood 테이블의 데이터를 삭제하려고 하면
        //mood 테이블에 foreign key로 잡혀있는 user_id가 null을 가리키게 되고 그래서 에러가 생기가 된다.
        //하지만 무드테이블을 먼저 지워주면 user_id가 제대로 된 값을 가리키고 있으므로 삭제가 잘 되고
        //Users 테이블은 애초에 누굴 참조하지 않기 때문에 쉽게 삭제가 가능하다.

        user = Users.builder().
                name("test").
                email("test@test.com").
                picture("testPicture").
                role(Role.GUEST).
                build();

        user = userRepository.save(user);



    }
    @Test
    void findAllByUsers() {
        Mood firstTestMood = Mood.builder().users(user).mood_level(3).build();
        Mood secondTestMood =Mood.builder().users(user).mood_level(6).build();

        moodRepository.save(firstTestMood);
        moodRepository.save(secondTestMood);

        List<Mood> moodList = moodRepository.findAllByUsers(user);

        assertEquals(3, moodList.get(0).getMood_level());
        assertEquals(6, moodList.get(1).getMood_level());



    }

    @Test
    void findThisWeeksMoodsByUsers() {

        // 저번주에 한개의 데이터를 넣고, 이번주에 두개의 데이터를 넣어서 갯수를 확인해본다.


        Mood moodOfLastWeek =Mood.builder().users(user).mood_level(3).build();
        Mood moodOfThisWeek =Mood.builder().users(user).mood_level(3).build();
        Mood moodofThisWeek_2 =Mood.builder().users(user).mood_level(5).build();

        List<Mood> actualList = new ArrayList<>();
        actualList.add(moodOfLastWeek);
        actualList.add(moodOfThisWeek);
        actualList.add(moodofThisWeek_2);

        //이렇게 해도 되는건지 모르겠지만, 일단 저장한다음에 날짜를 변경하는 쿼리를 만들어서
        //그 쿼리를 이용해서 날짜를 변경한다.
        // 그러면 일단 그 날짜를 변경하는 쿼리의 테스트가 선행되어야 한다.
        // 실패했다... mysql query를 짜는법은 알겠는데 jpql query를 짜는법을 몰라서 실패...
        // 그냥 위의 세개를 actualList에 넣고
        // moodList와 길이를 비교하는식으로 간단하게 진행하려 한다.

        moodOfLastWeek = moodRepository.save(moodOfLastWeek);
        moodOfThisWeek = moodRepository.save(moodOfThisWeek);
        moodofThisWeek_2 = moodRepository.save(moodofThisWeek_2);

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = startDate.plusDays(7);
        List<Mood> moodList = moodRepository.findThisWeeksMoodsByUsers(startDate, endDate, user);

        assertEquals(actualList.size(), moodList.size());
    }

//    @Test
//    void updateDateOfUser() {
//        Mood moodOfLastWeek =Mood.builder().users(user).mood_level(3).build();
//        moodOfLastWeek = moodRepository.save(moodOfLastWeek); // 무드 저장했고
//        LocalDateTime modifiedTime = moodRepository.updateDateOfUser(2, user);
//
//        System.out.println(modifiedTime);
//
//
//    }
}