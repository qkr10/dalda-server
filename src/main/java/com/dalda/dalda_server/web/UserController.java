package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.annotation.LoginUser;
import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession httpSession;

    @GetMapping("/logout-success")
    public String logout() {
        httpSession.invalidate();
        return "Successful logout";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/users/myinfo")
    public UserResponse myinfo(@LoginUser SessionUser sessionUser) {
        return new UserResponse(sessionUser);
    }
}
