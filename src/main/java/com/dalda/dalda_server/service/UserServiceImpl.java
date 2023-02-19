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
        return userRepository.findByEmail(email)
                .map(UserResponse::new)
                .orElse(new UserResponse());
    }
}
