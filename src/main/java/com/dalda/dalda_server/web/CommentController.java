package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.annotation.LoginUser;
import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.response.CommentsResponse;
import com.dalda.dalda_server.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

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

    @PatchMapping("/{id}/like")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String patchLike(
            @PathVariable("id") Long commentId,
            @RequestBody Map<String, String> requestBody,
            @LoginUser SessionUser user)
            throws IOException {

        Long vote = Long.parseLong(requestBody.get("like"));
        Long result = commentService.updateCommentVote(commentId, user.getId(), vote);
        if (result == 0) {
            httpServletResponse.sendError(403);
            return "false";
        }
        else {
            return "true";
        }
    }
}