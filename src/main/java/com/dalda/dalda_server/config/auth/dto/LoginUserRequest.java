package com.dalda.dalda_server.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
    boolean isLogin = false;
    SessionUser user = null;
}
