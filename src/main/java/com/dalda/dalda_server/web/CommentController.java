package com.dalda.dalda_server.web;

import com.dalda.dalda_server.response.CommentsResponse;
import com.dalda.dalda_server.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final HttpServletRequest httpServletRequest;

    @GetMapping
    public CommentsResponse comment() {
        String pageStr = httpServletRequest.getParameter("page");
        String sizeStr = httpServletRequest.getParameter("size");
        return commentService.findRootCommentListOrderByUpvote(pageStr, sizeStr);
    }

    @GetMapping("/{rootId}")
    public CommentsResponse comment(@PathVariable("rootId") Long rootId) {
        String pageStr = httpServletRequest.getParameter("page");
        String sizeStr = httpServletRequest.getParameter("size");
        return commentService.findSubCommentListOrderByDate(rootId, pageStr, sizeStr);
    }
}