package com.dalda.dalda_server.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.domain.user.Role.Role;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.domain.user.Users;
import java.util.Collections;
import java.util.HashMap;
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
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testMyInfo() throws Exception {
        Users user = Users.builder()
                .name("test")
                .email("test@test.test")
                .role(Role.USER)
                .handle("testtest")
                .picture("asdfasdf")
                .build();

        UserPrincipal principal = new UserPrincipal(
                user,
                new HashMap<String, Object>(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())));

        mockMvc.perform(get("/auth/me").with(oauth2Login().oauth2User(principal)))
                .andExpect(status().isOk());
    }

}
