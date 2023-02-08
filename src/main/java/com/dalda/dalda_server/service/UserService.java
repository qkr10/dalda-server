package com.dalda.dalda_server.service;

import com.dalda.dalda_server.response.MyinfoResponse;

public interface UserService {
    MyinfoResponse findMyinfoByEmail(String email);
}
