package com.dalda.dalda_server.service;

import com.dalda.dalda_server.response.CommentsResponse;

public interface CommentService {
    CommentsResponse findRootCommentListOrderByUpvote(Long page, Long size);
}
