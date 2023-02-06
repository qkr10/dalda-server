package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/logout-success")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "Successful logout";
    }

    @GetMapping("/users/myinfo")
    public UserResponse myinfo(HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        UserResponse response = new UserResponse();

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            httpServletResponse.sendError(401);
            return response;
        }
        response.setName(sessionUser.getName());
        response.setPicture(sessionUser.getPicture());
        response.setEmail(sessionUser.getEmail());

        return response;
    }

}
