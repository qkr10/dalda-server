package com.dalda.dalda_server.config.auth.dto;

import com.dalda.dalda_server.domain.user.Role.Role;
import com.dalda.dalda_server.domain.user.Users;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserPrincipal implements OAuth2User, Serializable {

    private final Long id;
    private final String handle;
    private final String email;
    private final String name;
    private final Role role;

    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(
            Users user,
            Map<String, Object> attributes,
            Collection<? extends GrantedAuthority> authorities) {

        this.id = user.getId();
        this.handle = user.getHandle();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();

        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }
}
