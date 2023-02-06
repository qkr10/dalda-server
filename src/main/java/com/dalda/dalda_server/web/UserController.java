package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.error.UnauthorizedException;
import com.dalda.dalda_server.web.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/public/users/logout")
    public String logout() {
        return "Successful logout";
    }

    @GetMapping("/public/users/myinfo")
    public UserResponse myinfo(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        UserResponse response = new UserResponse();

        if (user == null) {
            throw new UnauthorizedException("UnauthorizedError", 401);
        }

        SessionUser sessionUser = (SessionUser) user;
        response.setName(sessionUser.getName());
        response.setPicture(sessionUser.getPicture());
        response.setEmail(sessionUser.getEmail());

        return response;
    }

}
