package com.dalda.dalda_server.service;

import com.dalda.dalda_server.web.response.UserResponse;

public interface UserService {
    UserResponse findByHandle(String handle);

}
