package com.dalda.dalda_server.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@JsonInclude(Include.NON_NULL)
public class CommentResponse {
    private Long id;
    private String username;
    private Boolean isModified;
    private String createAt;
    private String content;
    private List<String> tags;
    private Long subCommentsCount;
    private Long likes;
}
