package com.dalda.dalda_server.domain.comment;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import java.util.List;

public interface CommentQueryRepository {
    List<Comments> findRootCommentListOrderByUpvote(Long page, Long size, UserPrincipal principal);

    Long countRootCommentList();

    List<Comments> findSubCommentListOrderByDate(Long rootId, long page, long size, UserPrincipal principal);

    Long updateContent(Long id, String content, String description);
}
