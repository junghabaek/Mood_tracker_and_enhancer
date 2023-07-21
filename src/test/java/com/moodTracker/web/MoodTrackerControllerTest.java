package com.moodTracker.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodTracker.domain.tasks.Task;
import com.moodTracker.domain.tasks.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MoodTrackerControllerTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mvc;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    @WithMockUser(roles = "GUEST")
    void selected_level() throws Exception {
        String URI = "/v1/selected_level_resolver";
        String selected_level = "1";

        mvc.perform(MockMvcRequestBuilders.get(URI)
                .param("selected_level", selected_level))
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    @WithMockUser(roles = "GUEST")
    void level_selected() throws Exception {
        String URI = "/v1/selected_tasks_resolver";
        String selected_level = "1";
        List<Task> mockTaskList = taskRepository.findAllByTaskLevel(Integer.parseInt(selected_level));

        mvc.perform(MockMvcRequestBuilders.get(URI)
//                        .content(new ObjectMapper().writeValueAsString(mockTaskList))
                        .sessionAttr("user_id", 1l)
//                        .param("task_list", new ObjectMapper().writeValueAsString(mockTaskList))
                        .param("task_list", "")
                .param("selected_level", selected_level))
                .andExpect(status().isOk());

    }

    @Test
    void returnSelectedLevel() {
    }
}