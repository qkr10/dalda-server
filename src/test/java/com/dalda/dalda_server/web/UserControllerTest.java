package com.dalda.dalda_server.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.domain.user.Role.Role;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.domain.user.Users;
import com.dalda.dalda_server.web.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    private Users user;

    @BeforeEach
    public void beforeAll() {
        user = Users.builder()
                .name("test")
                .email("test@test.test")
                .role(Role.USER)
                .handle("testtest")
                .picture("asdfasdf")
                .build();

        userRepository.save(user);
    }

    @AfterEach
    public void afterEach() {
        userRepository.delete(user);
    }

    @Test
    public void testAuthMe() throws Exception {
        //request
        var requestURL = "/auth/me";
        var requestPrincipal = new UserPrincipal(
                user,
                new HashMap<String, Object>(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())));

        //response
        var responseUser = new UserResponse(user);
        var responseBody = new ObjectMapper().writeValueAsString(responseUser);

        //perform
        mockMvc.perform(get(requestURL).with(oauth2Login().oauth2User(requestPrincipal)))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void testUsersHandle() throws Exception {
        //request
        var requestURL = "/users/" + user.getHandle();

        //response
        var responseUser = new UserResponse(user);
        var responseBody = new ObjectMapper().writeValueAsString(responseUser);

        //perform
        mockMvc.perform(get(requestURL))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

}
