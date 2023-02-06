package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.web.response.MyinfoResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/logout-success")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "Successful logout";
    }

    @GetMapping("/users/myinfo")
    public MyinfoResponse myinfo(HttpSession httpSession) {
        var email = ((SessionUser) httpSession.getAttribute("user")).getEmail();
        var user = userRepository.findByEmail(email);

        MyinfoResponse myinfo = new MyinfoResponse();
        user.ifPresent(users -> {
            myinfo.setId(users.getId());
            myinfo.setName(users.getName());
        });
        return myinfo;
    }

}
