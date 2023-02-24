package com.dalda.dalda_server.service;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import com.dalda.dalda_server.web.response.CommentsResponse;

public interface CommentService {
    CommentsResponse findRootCommentListOrderByUpvote(String page, String size, SessionUser sessionUser);

    CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr, SessionUser sessionUser);

    Long updateCommentVote(Long commentId, Long userId, String vote);
}
