package com.vineela.classregistrationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vineela.classregistrationsystem.ApplicationTests;
import com.vineela.classregistrationsystem.model.Professor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vineela Boddula
 */
public class ProfessorControllerUnitTest extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetProfessorById_withoutMockUser() throws Exception {
        mockMvc.perform(get("/api/v1/professors/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testCreateProfessor() throws Exception {
        Professor professor = new Professor(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        mockMvc.perform(post("/api/v1/professors")
                .content(objectMapper.writeValueAsString(professor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetAllProfessors() throws Exception {
        mockMvc.perform(get("/api/v1/professors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}