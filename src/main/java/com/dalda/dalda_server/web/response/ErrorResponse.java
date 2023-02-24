package com.dalda.dalda_server.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Long status;
    private String msg;

    public ErrorResponse(int sc, String msg) {
        this(Integer.toUnsignedLong(sc), msg);
    }
}
