package com.dalda.dalda_server.domain.comment;

import static com.dalda.dalda_server.domain.comment.QComments.comments;
import static com.dalda.dalda_server.domain.tag.QTags.tags;
import static com.dalda.dalda_server.domain.tagcomment.QTagComment.tagComment;
import static com.dalda.dalda_server.domain.user.QUsers.users;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory query;

    public List<Comments> findRootCommentListOrderByUpvote(Long page, Long size) {
        return query
                .select(comments)
                .from(comments)
                .leftJoin(comments.tagComments, tagComment).fetchJoin()
                .leftJoin(tagComment.tag, tags).fetchJoin()
                .leftJoin(comments.user, users).fetchJoin()
                .where(comments.commentRoot.isNull())
                .orderBy(comments.upvoteSum.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

    @Override
    public Long countRootCommentList() {
        return (long) query
                .select(comments)
                .from(comments)
                .join(tagComment).join(tags).join(users).fetchJoin()
                .fetch().size();
    }

    @Override
    public List<Comments> findSubCommentListOrderByDate(Long rootId, long page, long size) {
        return query
                .select(comments)
                .from(comments)
                .leftJoin(comments.tagComments, tagComment).fetchJoin()
                .leftJoin(tagComment.tag, tags).fetchJoin()
                .leftJoin(comments.user, users).fetchJoin()
                .where(comments.commentRoot.eq(rootId))
                .orderBy(comments.createDate.asc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }
}