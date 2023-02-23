package com.dalda.dalda_server.service;

import com.dalda.dalda_server.response.CommentsResponse;

public interface CommentService {
    CommentsResponse findRootCommentListOrderByUpvote(String page, String size);

    CommentsResponse findSubCommentListOrderByDate(Long rootId, String pageStr, String sizeStr);

    Long updateCommentVote(Long commentId, Long userId, Long vote);
}
