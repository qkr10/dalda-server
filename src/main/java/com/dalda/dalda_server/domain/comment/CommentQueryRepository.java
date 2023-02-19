package com.dalda.dalda_server.domain.comment;

import java.util.List;

public interface CommentQueryRepository {
    List<Comments> findRootCommentListOrderByUpvote(Long page, Long size);

    Long countRootCommentList();

    List<Comments> findSubCommentListOrderByDate(Long rootId, long page, long size);
}
