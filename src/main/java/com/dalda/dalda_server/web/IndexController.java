package com.dalda.dalda_server.web;

import com.dalda.dalda_server.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "";
    }

    @GetMapping("/error")
    public ErrorResponse error(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        return new ErrorResponse(code, HttpStatusCode.valueOf(code).toString());
    }

}
