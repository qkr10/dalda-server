package com.dalda.dalda_server.domain.comment;

import static com.dalda.dalda_server.domain.comment.QComments.comments;
import static com.dalda.dalda_server.domain.tag.QTags.tags;
import static com.dalda.dalda_server.domain.tagcomment.QTagComment.tagComment;
import static com.dalda.dalda_server.domain.user.QUsers.users;
import static com.dalda.dalda_server.domain.vote.QVotes.votes;

import com.dalda.dalda_server.config.auth.dto.UserPrincipal;
import com.dalda.dalda_server.domain.comment.CommentsComparatorsFactory.CommentsComparatorsType;
import com.dalda.dalda_server.domain.user.QUsers;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory query;

    public List<Comments> findRootCommentListOrderByUpvote(Long page, Long size, UserPrincipal principal) {
        List<Long> idList = query
                .select(comments.id)
                .from(comments)
                .where(comments.rootComment.isNull())
                .orderBy(comments.upvoteSum.desc())
                .offset(page * size)
                .limit(size)
                .fetch();

        return getComments(idList, principal, CommentsComparatorsType.UPVOTE);
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
    public List<Comments> findSubCommentListOrderByDate(Long rootId, long page, long size, UserPrincipal principal) {
        List<Long> idList = query
                .select(comments.id)
                .from(comments)
                .where(comments.rootComment.id.eq(rootId))
                .orderBy(comments.upvoteSum.desc())
                .offset(page * size)
                .limit(size)
                .fetch();

        return getComments(idList, principal, CommentsComparatorsType.DATE);
    }

    @Override
    public Long updateContent(Long id, String content, String description) {
        return query.update(comments)
                .set(comments.content, content)
                .set(comments.description, description)
                .where(comments.id.eq(id))
                .execute();
    }

    private List<Comments> getComments(List<Long> idList, UserPrincipal principal, CommentsComparatorsType type) {
        QComments rootComment = new QComments("rootComment");
        QUsers mentionUser = new QUsers("mentionUser");

        List<Comments> commentList = query
                .select(comments)
                .from(comments)
                .leftJoin(comments.rootComment, rootComment).fetchJoin()
                .leftJoin(comments.mentionUser, mentionUser).fetchJoin()
                .leftJoin(comments.tagComments, tagComment).fetchJoin()
                .leftJoin(tagComment.tag, tags).fetchJoin()
                .leftJoin(comments.user, users).fetchJoin()
                .where(comments.id.in(idList))
                .fetch();
        commentList.sort(CommentsComparatorsFactory.getComparator(type));

        if (principal == null) {
            return commentList;
        }

        Set<Long> isLikeSet = query
                .selectFrom(votes)
                .where(votes.comment.id.in(idList))
                .where(votes.user.id.eq(principal.getId()))
                .fetch()
                .stream()
                .map(vote -> vote.getComment().getId())
                .collect(Collectors.toSet());

        return commentList
                .stream()
                .peek(comment -> comment.setIsLike(isLikeSet.contains(comment.getId())))
                .toList();
    }
}