package com.vineela.classregistrationsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.vineela.classregistrationsystem.message.request.SignUpRequest;
import com.vineela.classregistrationsystem.model.Professor;
import com.vineela.classregistrationsystem.model.Role;
import com.vineela.classregistrationsystem.model.RoleName;
import com.vineela.classregistrationsystem.model.User;
import com.vineela.classregistrationsystem.repository.UserRepository;
import com.vineela.classregistrationsystem.security.jwt.JwtProvider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vineela Boddula
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfessorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ObjectMapper mapper;

    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    //admin username should be existing in the database
    @Ignore
    @Test
    public void testCreateProfessor() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        Set<Role> role = new HashSet<>();
        role.add(new Role(RoleName.ROLE_ADMIN));
        user.setRoles(role);


        String token = "Bearer " + jwtProvider.createToken("admin");
        assertNotNull(token);
        Professor professor = new Professor(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        String jsonRequest = mapper.writeValueAsString(professor);
        MvcResult result = mockMvc.perform(post("/api/v1/professors").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

        MvcResult delete = mockMvc.perform(delete("/api/v1/professors/{id}", id).contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, delete.getResponse().getStatus());
    }

    @Test
    public void testGetProfessorById_withoutJWT() throws Exception {
        mockMvc.perform(get("/api/v1/professors/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}

