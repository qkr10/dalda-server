package com.dalda.dalda_server.response;

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
public class CommentsResponse {
    private Long page;
    private Long size;
    private Boolean isLast;
    private List<CommentResponse> comments;
}
