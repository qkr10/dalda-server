package com.dalda.dalda_server.web;

import com.dalda.dalda_server.config.auth.dto.LoginUserRequest;
import com.dalda.dalda_server.config.auth.dto.annotation.LoginUser;
import com.dalda.dalda_server.service.CommentService;
import com.dalda.dalda_server.web.response.CommentsResponse;
import com.dalda.dalda_server.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final HttpServletResponse httpServletResponse;

    @GetMapping
    public CommentsResponse comment(
            @RequestParam Map<String, String> requestBody,
            @LoginUser LoginUserRequest loginUser) {

        String pageStr = requestBody.get("page");
        String sizeStr = requestBody.get("size");
        return commentService.findRootCommentListOrderByUpvote(pageStr, sizeStr, loginUser.getUser());
    }

    @GetMapping("/{rootId}")
    public CommentsResponse comment(
            @PathVariable("rootId") Long rootId,
            @RequestParam Map<String, String> requestBody,
            @LoginUser LoginUserRequest loginUser) {

        String pageStr = requestBody.get("page");
        String sizeStr = requestBody.get("size");
        return commentService.findSubCommentListOrderByDate(rootId, pageStr, sizeStr, loginUser.getUser());
    }

    @PatchMapping("/{id}/like")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ErrorResponse patchLike(
            @PathVariable("id") Long commentId,
            @RequestBody Map<String, String> requestBody,
            @LoginUser LoginUserRequest loginUser) {

        String voteStr = requestBody.get("like");
        Long result = commentService.updateCommentVote(commentId, loginUser.getUser(), voteStr);
        if (result == 0) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ErrorResponse(HttpServletResponse.SC_FORBIDDEN, "SC_FORBIDDEN");
        }
        return new ErrorResponse(HttpServletResponse.SC_OK, "SC_OK");
    }
}