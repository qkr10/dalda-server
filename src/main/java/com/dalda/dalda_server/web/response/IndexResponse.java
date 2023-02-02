package com.dalda.dalda_server.web.response;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class IndexResponse {
    private boolean logined;
    private SessionUser sessionUser;
}
