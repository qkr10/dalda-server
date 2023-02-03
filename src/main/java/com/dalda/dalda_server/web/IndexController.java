package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.response.IndexResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public IndexResponse index(HttpSession httpSession) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        IndexResponse response = new IndexResponse();

        response.setLogined(user != null);
        if (user != null) {
            response.setSessionUser(user);
        }

        return response;
    }

}
