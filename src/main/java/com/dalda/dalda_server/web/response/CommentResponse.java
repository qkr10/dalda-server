package com.dalda.dalda_server.web.response;

import java.util.List;
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
public class CommentResponse extends ErrorResponse {
    private Long id;
    private UserResponse writer;
    private UserResponse mentionUser;
    private String description;
    private List<String> tags;
    private Long subCommentsCount;
    private Long likes;
    private Boolean isLike;
    private String createdAt;
    private String updatedAt;
    private Boolean isModified;

    public CommentResponse(ErrorResponse errorResponse) {
        super(errorResponse.getStatus(), errorResponse.getMsg());
    }
}
