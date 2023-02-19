package com.dalda.dalda_server.response;

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
public class UserResponse {
    private String handle;
    private String name;

    public UserResponse(Users users) {
        this.handle = users.getHandle();
        this.name = users.getName();
    }
}
