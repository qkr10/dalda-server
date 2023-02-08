package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.response.MyinfoResponse;
import com.dalda.dalda_server.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession httpSession;
    private final UserService userService;

    @GetMapping("/logout-success")
    public String logout() {
        httpSession.invalidate();
        return "Successful logout";
    }

    @GetMapping("/users/myinfo")
    public MyinfoResponse myinfo() {
        var email = ((SessionUser) httpSession.getAttribute("user")).getEmail();
        return userService.findMyinfoByEmail(email);
    }
}
