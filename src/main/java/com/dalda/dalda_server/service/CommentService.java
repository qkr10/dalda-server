package com.dalda.dalda_server.service;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.web.request.CommentRequest;
import com.dalda.dalda_server.web.response.CommentContentResponse;
import com.dalda.dalda_server.web.response.CommentsResponse;

public interface CommentService {
    CommentsResponse findRootCommentListOrderByUpvote(String page, String size, UserPrincipal principal);

    CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr, UserPrincipal principal);

    CommentContentResponse findById(Long id);

    Long updateCommentVote(Long commentId, UserPrincipal userId, String vote);

    Long createComment(UserPrincipal principal, CommentRequest commentRequest);

    Long updateComment(Long commentId, UserPrincipal user, CommentRequest commentRequest);

    Long deleteComment(Long commentId, UserPrincipal user);
}
