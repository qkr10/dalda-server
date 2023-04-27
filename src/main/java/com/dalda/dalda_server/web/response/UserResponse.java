package com.dalda.dalda_server.web.response;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserResponse extends ErrorResponse {
    private String handle;
    private String username;

    public UserResponse(Users users) {
        this.handle = users.getHandle();
        this.username = users.getName();
    }

    public UserResponse(ErrorResponse errorResponse) {
        super(errorResponse.getStatus(), errorResponse.getMsg());
    }

    public UserResponse(UserPrincipal principal) {
        this.handle = principal.getHandle();
        this.username = principal.getName();
    }
}
