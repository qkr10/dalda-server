package com.dalda.dalda_server.config.auth.dto;

import com.dalda.dalda_server.domain.user.Role.Role;
import com.dalda.dalda_server.domain.user.Users;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class SessionUser implements Serializable {
    private final Long id;
    private final String handle;
    private final String email;
    private final String name;
    private final Role role;

    public SessionUser(Users user) {
        this.id = user.getId();
        this.handle = user.getHandle();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
