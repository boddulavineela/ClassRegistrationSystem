package com.vineela.classregistrationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.vineela.classregistrationsystem.ApplicationTests;
import com.vineela.classregistrationsystem.model.Class;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vineela Boddula
 */
public class ClassControllerAPITest extends ApplicationTests {

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
    public void testGetClassById_withoutMockUser() throws Exception {
        mockMvc.perform(get("/api/v1/classes/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testCreateAndDeleteClass() throws Exception {
        Class aclass = new Class(0L, "CSC100", "Java", "Basics of Java");
        MvcResult result = mockMvc.perform(post("/api/v1/classes")
                .content(objectMapper.writeValueAsString(aclass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        MvcResult delete = mockMvc.perform(delete("/api/v1/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetAllClasss() throws Exception {
        mockMvc.perform(get("/api/v1/classes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetClassById() throws Exception {
        Class aclass = new Class(0L, "CSC100", "Java", "Basics of Java");
        MvcResult result = mockMvc.perform(post("/api/v1/classes")
                .content(objectMapper.writeValueAsString(aclass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        MvcResult getById = mockMvc.perform(get("/api/v1/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, getById.getResponse().getStatus());
        assertEquals("CSC100", JsonPath.read(getById.getResponse().getContentAsString(), "$.classNumber").toString());
        assertEquals("Java", JsonPath.read(getById.getResponse().getContentAsString(), "$.className").toString());
        assertEquals("Basics of Java", JsonPath.read(getById.getResponse().getContentAsString(), "$.classDescription").toString());

        MvcResult delete = mockMvc.perform(delete("/api/v1/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testUpdateClassById() throws Exception {
        Class aclass = new Class(0L, "CSC100", "Java", "Basics of Java");
        MvcResult result = mockMvc.perform(post("/api/v1/classes")
                .content(objectMapper.writeValueAsString(aclass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        Class updateClass = new Class(0L, "CSC100-1", "Java Updated", "Basics of Java Updated");

        MvcResult update = mockMvc.perform(put("/api/v1/classes/{id}", id)
                .content(objectMapper.writeValueAsString(updateClass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, update.getResponse().getStatus());

        //verify after update
        MvcResult getById = mockMvc.perform(get("/api/v1/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, getById.getResponse().getStatus());
        assertEquals("CSC100-1", JsonPath.read(getById.getResponse().getContentAsString(), "$.classNumber").toString());
        assertEquals("Java Updated", JsonPath.read(getById.getResponse().getContentAsString(), "$.className").toString());
        assertEquals("Basics of Java Updated", JsonPath.read(getById.getResponse().getContentAsString(), "$.classDescription").toString());

        //delete
        MvcResult delete = mockMvc.perform(delete("/api/v1/classes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }
}