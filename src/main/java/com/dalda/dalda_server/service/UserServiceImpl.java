package com.dalda.dalda_server.service;

import com.dalda.dalda_server.domain.user.UserRepository;
import com.dalda.dalda_server.response.MyinfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public MyinfoResponse findMyinfoByEmail(String email) {
        MyinfoResponse result = new MyinfoResponse();

        userRepository.findByEmail(email).ifPresent(user -> {
            result.setHandle(user.getHandle());
            result.setName(user.getName());
        });

        return result;
    }
}
