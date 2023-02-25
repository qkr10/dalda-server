package com.dalda.dalda_server.web.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String mentionUserHandle;
    private String content;
    private List<String> tags;
}
