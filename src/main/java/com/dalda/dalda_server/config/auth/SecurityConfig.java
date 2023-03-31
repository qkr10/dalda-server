package com.dalda.dalda_server.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated())
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .logout().invalidateHttpSession(true).logoutSuccessUrl("/logout-success")
                .and()
                    .oauth2Login()
                    .successHandler(customSuccessHandler)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);

        return http.build();
    }

}
