package com.dalda.dalda_server.domain.comment;

import com.dalda.dalda_server.config.auth.dto.SessionUser;
import java.util.List;

public interface CommentQueryRepository {
    List<Comments> findRootCommentListOrderByUpvote(Long page, Long size, SessionUser sessionUser);

    Long countRootCommentList();

    List<Comments> findSubCommentListOrderByDate(Long rootId, long page, long size, SessionUser sessionUser);

    Long updateContent(Long id, String content, String description);
}
