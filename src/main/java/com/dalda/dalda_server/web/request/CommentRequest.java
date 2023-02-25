package com.dalda.dalda_server.web.request;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @Nullable
    private Long rootCommentId;

    @Nullable
    private String mentionUserHandle;
    private String content;
    private List<String> tags;
}
