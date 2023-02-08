package com.dalda.dalda_server.web;

import com.dalda.dalda_server.response.CommentsResponse;
import com.dalda.dalda_server.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping("/comment")
    public CommentsResponse comment() {
        String pageStr = httpServletRequest.getParameter("page");
        String sizeStr = httpServletRequest.getParameter("size");
        Long page = Long.parseLong(pageStr, 10);
        Long size = Long.parseLong(sizeStr, 10);
        return commentService.findRootCommentListOrderByUpvote(page, size);
    }
}