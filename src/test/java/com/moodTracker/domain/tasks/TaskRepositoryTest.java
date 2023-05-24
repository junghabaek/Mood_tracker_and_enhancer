package com.moodTracker.domain.tasks;

import com.moodTracker.domain.users.Role;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback(true)
class TaskRepositoryTest {

    // Test를 하고싶었는데 이미 db에 데이터가 있었기 때문에
    // findAllByTaskLevel을 하기가 어려웠음
    // 그래서 없는 레벨인 7을 만들고 그것을 사용했음

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    private Users user;
    private Task firstTask;
    private Task secondTask;
    @BeforeEach
    void setup(){
        user = Users.builder().
                name("test").
                email("test@test.com").
                picture("testPicture").
                role(Role.GUEST).
                build();
        user = userRepository.save(user);

        firstTask = Task.builder().
                users(user).
                task_name("firstTask").
                task_level(7).
                build();

        secondTask = Task.builder().
                users(user).
                task_name("secondTask").
                task_level(7).
                build();

        firstTask = taskRepository.save(firstTask);
        secondTask = taskRepository.save(secondTask);
    }
//    @AfterEach
//    void cleanup() {
//        taskRepository.delete(firstTask); // Delete all tasks
//        taskRepository.delete(secondTask);
//        userRepository.delete(user); // Delete the user
//    }
    //@Transactional과 @Rollback(true)를 사용하면
    //@AfterEach 없이도 testdata를 없앨 수 있다.


    @Test
    void findAllByUsers() {
//        Users user = userRepository.findByEmail("test@Test.com").get();
        // 위처럼 할 필요 없이 class 변수에다가 만든 Users user를 사용하면 됨.



        //when
        List<Task> taskList = taskRepository.findAllByUsers(user);

        //then
        assertEquals(taskList.get(0).getTask_name(), firstTask.getTask_name());
        assertEquals(taskList.get(1).getTaskLevel(), secondTask.getTaskLevel());
        assertEquals(taskList.get(1).getTask_name(), secondTask.getTask_name());
        assertEquals(taskList.get(1).getTaskLevel(), secondTask.getTaskLevel());
    }

    @Test
    void findAllByTaskLevel() {
        List<Task> taskList = taskRepository.findAllByTaskLevel(7);

//        for (Task t : taskList){
//            System.out.println(t.getTask_name());
//        }
        assertEquals("firstTask", taskList.get(0).getTask_name());
        assertEquals ("secondTask", taskList.get(1).getTask_name());

    }
}