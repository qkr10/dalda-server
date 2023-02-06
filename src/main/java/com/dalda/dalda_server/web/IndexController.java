package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.response.IndexResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/myinfo")
    public IndexResponse index(HttpSession httpSession) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        IndexResponse response = new IndexResponse();

        if (user != null) {
            response.setName(user.getName());
            response.setPicture(user.getPicture());
            response.setEmail(user.getEmail());
        }

        return response;
    }

}
