package com.dalda.dalda_server.config.auth.dto.annotation;

import com.dalda.dalda_server.config.auth.dto.LoginUserRequest;
import com.dalda.dalda_server.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation =
                parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = LoginUserRequest.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public LoginUserRequest resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer modelAndViewContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        var sessionUser = httpSession.getAttribute("user");
        if (sessionUser == null) {
            return new LoginUserRequest();
        }
        return new LoginUserRequest(true, (SessionUser) sessionUser);
    }
}
