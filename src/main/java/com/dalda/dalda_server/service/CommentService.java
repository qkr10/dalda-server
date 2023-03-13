package com.dalda.dalda_server.service;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.request.CommentRequest;
import com.dalda.dalda_server.web.response.CommentContentResponse;
import com.dalda.dalda_server.web.response.CommentsResponse;

public interface CommentService {
    CommentsResponse findRootCommentListOrderByUpvote(String page, String size, SessionUser sessionUser);

    CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr, SessionUser sessionUser);

    CommentContentResponse findById(Long id);

    Long updateCommentVote(Long commentId, SessionUser userId, String vote);

    Long createComment(SessionUser sessionUser, CommentRequest commentRequest);

    Long updateComment(Long commentId, SessionUser user, CommentRequest commentRequest);

    Long deleteComment(Long commentId, SessionUser user);
}
