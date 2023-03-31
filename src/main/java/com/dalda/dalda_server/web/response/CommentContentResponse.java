package com.dalda.dalda_server.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentContentResponse extends ErrorResponse {
    private Long id;
    private String content;

    public CommentContentResponse(ErrorResponse errorResponse) {
        super(errorResponse.getStatus(), errorResponse.getMsg());
    }
}
