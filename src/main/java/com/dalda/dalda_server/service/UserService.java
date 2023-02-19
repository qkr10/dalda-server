package com.dalda.dalda_server.service;

import com.dalda.dalda_server.response.UserResponse;

public interface UserService {
    UserResponse findMyinfoByEmail(String email);
}
