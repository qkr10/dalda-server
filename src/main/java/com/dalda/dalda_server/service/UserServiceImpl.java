package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.response.UserResponse;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse findMyinfoByEmail(String email) {
        AtomicReference<UserResponse> result = new AtomicReference<>();

        userRepository.findByEmail(email).ifPresent(user ->
                result.set(new UserResponse(user))
        );

        return result.get();
    }
}
