package com.vineela.classregistrationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.vineela.classregistrationsystem.ApplicationTests;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Student;
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
public class StudentControllerAPITest extends ApplicationTests {

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
    public void testGetStudentById_withoutMockUser() throws Exception {
        mockMvc.perform(get("/api/v1/students/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testCreateAndDeleteStudent() throws Exception {
        Student student = new Student(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        MvcResult result = mockMvc.perform(post("/api/v1/students")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        MvcResult delete = mockMvc.perform(delete("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testGetStudentById() throws Exception {
        Student student = new Student(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        MvcResult result = mockMvc.perform(post("/api/v1/students")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        MvcResult getById = mockMvc.perform(get("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, getById.getResponse().getStatus());
        assertEquals("Vineela", JsonPath.read(getById.getResponse().getContentAsString(), "$.firstName").toString());
        assertEquals("Boddula", JsonPath.read(getById.getResponse().getContentAsString(), "$.lastName").toString());
        assertEquals("boddulavineela@gmail.com", JsonPath.read(getById.getResponse().getContentAsString(), "$.emailAddress").toString());

        //delete
        MvcResult delete = mockMvc.perform(delete("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testUpdateStudentById() throws Exception {
        Student student = new Student(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        MvcResult result = mockMvc.perform(post("/api/v1/students")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        Student updateStudent = new Student(0, "Vineela1", "Boddula1", "boddulavineela1@gmail.com", "111-222-3333", "Raleigh");
        MvcResult update = mockMvc.perform(put("/api/v1/students/{id}", id)
                .content(objectMapper.writeValueAsString(updateStudent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, update.getResponse().getStatus());

        //verify after update
        MvcResult getById = mockMvc.perform(get("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, getById.getResponse().getStatus());
        assertEquals("Vineela1", JsonPath.read(getById.getResponse().getContentAsString(), "$.firstName").toString());
        assertEquals("Boddula1", JsonPath.read(getById.getResponse().getContentAsString(), "$.lastName").toString());
        assertEquals("boddulavineela1@gmail.com", JsonPath.read(getById.getResponse().getContentAsString(), "$.emailAddress").toString());

        //delete
        MvcResult delete = mockMvc.perform(delete("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }


    @Test
    @WithMockUser(username="admin",roles={"ADMIN"})
    public void testRegisterStudentToClass() throws Exception {
        //Create Class
        Class aclass = new Class(0L, "CSC100", "Java", "Basics of Java");
        MvcResult result = mockMvc.perform(post("/api/v1/classes")
                .content(objectMapper.writeValueAsString(aclass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        String classId = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();


        //Create Student
        Student student = new Student(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        MvcResult result1 = mockMvc.perform(post("/api/v1/students")
                .content(objectMapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result1.getResponse().getStatus());
        String studentId = JsonPath.read(result1.getResponse().getContentAsString(), "$.id").toString();

        MvcResult registerClass = mockMvc.perform(put("/api/v1/students/{id}/registerClass", studentId)
                .content(objectMapper.writeValueAsString(aclass))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, registerClass.getResponse().getStatus());
        //verify after registerClass
        MvcResult getById = mockMvc.perform(get("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, getById.getResponse().getStatus());
        assertEquals("Java", JsonPath.read(getById.getResponse().getContentAsString(), "$.classesRegistered").toString());

        //delete student
        MvcResult delete = mockMvc.perform(delete("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());

        //delete class
        MvcResult delete1 = mockMvc.perform(delete("/api/v1/classes/{id}", classId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete1.getResponse().getStatus());
    }
}