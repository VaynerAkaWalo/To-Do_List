package com.example.todo_list.web.rest.integration;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.dto.request.TaskCreationDTO;
import com.example.todo_list.services.TasksService;
import com.example.todo_list.web.rest.TaskRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskRestController.class)
class TaskRestControllerIntegrationTest {

    @MockBean
    private TasksService tasksService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;
    private Task otherTask;

    @BeforeEach
    void beforeEach() {
        task = new Task();
        task.setId(1L);
        task.setName("take out the garbage");
        task.setDetails("very important details");

        otherTask = new Task();
        otherTask.setId(2L);
        otherTask.setName("wash the dishes");
        otherTask.setDetails("idk some details");
    }

    @WithMockUser("springTest")
    @Test
    void taskShouldReturnArrayAndOkStatusWhenNoTaskIdGiven() throws Exception {
        when(tasksService.getAllTasksByUser("springTest")).thenReturn(List.of(task, otherTask));

        mvc.perform(MockMvcRequestBuilders
                .get("/api/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @WithMockUser("springTest")
    @Test
    void taskShouldReturnTaskAndOkStatusWhenTaskIdGiven() throws Exception {
        Long id = 1L;
        when(tasksService.getTaskById(id ,"springTest")).thenReturn(task);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/tasks/1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(id))
                        .andExpect(jsonPath("$.name").exists())
                        .andExpect(jsonPath("$.details").exists());
    }

    @WithMockUser("SpringTest")
    @Test
    void createTaskShouldReturnCreatedStatus() throws Exception {
        TaskCreationDTO taskCreationDTO = new TaskCreationDTO(task.getName(), task.getDetails(), false);
        when(tasksService.addTask(taskCreationDTO, "SpringTest")).thenReturn(task);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/tasks")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskCreationDTO)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(task.getName()))
                        .andExpect(jsonPath("$.details").value(task.getDetails()))
                        .andExpect(jsonPath("$.status").value("false"));
    }

    @WithMockUser("SpringTest")
    @Test
    void updateTaskShouldReturnOkStatusAndReturnUpdatedTask() throws Exception {
        TaskCreationDTO taskCreationDTO = new TaskCreationDTO(otherTask.getName(), otherTask.getDetails(), false);
        otherTask.setId(task.getId());
        when(tasksService.editTask(task.getId(), taskCreationDTO, "SpringTest")).thenReturn(otherTask);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/tasks/" + task.getId())
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskCreationDTO)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(otherTask.getName()))
                        .andExpect(jsonPath("$.details").value(otherTask.getDetails()))
                        .andExpect(jsonPath("$.status").value("false"));
    }

    @WithMockUser("SpringTest")
    @Test
    void toggleStatusShouldReturnOkStatusAndReturnUpdatedTask() throws Exception {
        when(tasksService.changeTaskStatus(task.getId(), "SpringTest")).thenReturn(task);
        task.setAsCompleted();

        mvc.perform(MockMvcRequestBuilders
                        .patch("/api/tasks/" + task.getId())
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(task.getName()))
                        .andExpect(jsonPath("$.details").value(task.getDetails()))
                        .andExpect(jsonPath("$.status").value("true"));
    }

    @WithMockUser("SpringTest")
    @Test
    void deleteTaskShouldReturnNoContentStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/tasks/" + task.getId())
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNoContent());
    }
}
