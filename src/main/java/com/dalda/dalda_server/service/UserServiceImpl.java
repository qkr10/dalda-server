package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.web.response.ErrorResponse;
import com.dalda.dalda_server.web.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findByHandle(String handle) {
        return userRepository.findByHandle(handle)
                .map(UserResponse::new)
                .orElseGet(() -> new UserResponse(new ErrorResponse(
                        HttpServletResponse.SC_BAD_REQUEST, "bad request"
                )));
    }
}
