package com.dalda.dalda_server.domain.vote;

import static com.dalda.dalda_server.domain.vote.QVotes.votes;

import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.user.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VoteQueryRepositoryImpl implements VoteQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<Votes> findByUserAndComment(Users user, Comments comment) {
        return Optional.ofNullable(
                query.selectFrom(votes)
                    .where(votes.user.id.eq(user.getId()))
                    .where(votes.comment.id.eq(comment.getId()))
                    .fetchFirst()
        );
    }
}
