package com.moodTracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodTracker.config.auth.CustomOAuth2UserService;
import com.moodTracker.config.auth.SecurityConfig;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

//@WebMvcTest(SecurityConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
class HelloControllerTest {

//    @Autowired
//    private WebApplicationContext context;

//    @Mock
//    private HttpSession httpSession;

    //아래의 목빈도 더이상 필요없다. 왜냐면 @SpringBootTest로 받아왔기 때문
//    @MockBean
//    private CustomOAuth2UserService customOAuth2UserService;

//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;
// @WebMvcTest는 @Component같은것들을 빈으로 등록하고 사용하지 않으므로 autowired로 가져올 수 없다.
    // 그래서 아무리 컨트롤러를 위한 테스트라 할지라도 컨트롤러가 의존하는 다른 빈들이 있다면
    // @WebMvcTest 보다는 @SpringBootTest를 사용해야 한다.
    // 그리고 Spring-security를 사용하기 때문에 @WithMockUser를 사용해야 하는데
    // 이것을 하려면 의존성을 등록해 주어야 한다
//testImplementation("org.springframework.security:spring-security-test")
    @Autowired
    private MockMvc mvc;

    //아래의 코드는 MockMvc 객체를 생성해 주는 코드인데
    //우리는 @AutoConfigureMockMvc를 통해서 mockmvc를 받아왔기 때문에
    //아래의 코드는 굳이 필요가 없다.

//    @BeforeEach
//    public void setup(){
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }


    @Test
//    @WithMockUser(roles = "GUEST") 인덱스 페이지는 로그인이 필요없으므로 목유저가 필요없다
    void hello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));

    }

    @Test
    @WithMockUser(roles = "GUEST")
    void moodtracker() throws Exception {
        // HelloController에 moodtracker에 가보면 user_id를 session에서 받아온다
        // 그런데 우리는 session에 아무것도 없으므로 테스트가 실패했다
        // 그래서 세션에 값이 없는 경우에는 무드의 평균값을 -1로 지정해서 테스트를 패스시켰다.
//        mvc.perform(MockMvcRequestBuilders.get("/v1/mood_tracker"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("-1"));
        //세션값을 넣는 방법을 알았으므로 다시 코드를 작성함

        mvc.perform(MockMvcRequestBuilders.get("/v1/mood_tracker")
                .sessionAttr("user_id", 1l))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void newTask() throws Exception {
        String url = "/v1/task_manager";
        //내 테스트에 있는 문제가 뭐냐면
        //"GUEST" role까지는 주어지는데
        // 실제 유저가 로그인을 해야 httpsession에 값이 들어감
        // 근데 그러지 못하고 있음
        // 그래서 생각해본게 mvc에다가 session값을 넣어주면 어떨까? 해서
        // 찾아봤더니 .sessionAttr이란 메서드가 있어서 여기에 값을 넣어서 보내봤더니
        // 잘 동작하긴 하는데, 약간 신기했던게 session의 datatype이 parsing이 안된다.
        // 그래서 아래처럼 .sessionAttr("user_id", 1l)
        // 이렇게 long value를 넣어줘야 했음. int value도 안됐음.
        // 아 세션이 서버에서 관리하는 데이터니까 parsing이 따로 필요가 없는것 같다.
        // Oauth에 의해 로그인이 되면서, user_id같은 값이 Users의 id처럼 Long으로
        // 들어가는것 같다.
        //위의 테스트도 바꿔야겠다.


        //when
        mvc.perform(MockMvcRequestBuilders.get(url)
                .sessionAttr("user_id", 1l))
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void moodhistory() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v1/mood_history"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "GUEST")
    void moodhistoryResolver() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//        LocalDate startDate = LocalDate.parse("2023-01-01", formatter);
//        LocalDate endDate = LocalDate.parse("2023-12-31", formatter);
        mvc.perform(MockMvcRequestBuilders.get("/v1/mood_history_resolver")
            .sessionAttr("user_id", 1l)
            .contentType(MediaType.APPLICATION_JSON)
            .param("startDate", "2023-01-01")
            .param("endDate", "2023-12-31"))
            .andExpect(status().isOk());




    }
}