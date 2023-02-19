package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findMyinfoByEmail(String email) {
        UserResponse result = new UserResponse();

        userRepository.findByEmail(email).ifPresent(user -> {
            result.setHandle(user.getHandle());
            result.setName(user.getName());
        });

        return result;
    }
}
