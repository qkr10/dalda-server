package com.dalda.dalda_server.web;

import com.dalda.dalda_server.web.response.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ErrorResponse error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int code = Integer.parseInt(status.toString());
        ErrorResponse error = new ErrorResponse();
        error.setStatus((long) code);
        error.setMsg(HttpStatus.resolve(code).name());
        return error;
    }
}