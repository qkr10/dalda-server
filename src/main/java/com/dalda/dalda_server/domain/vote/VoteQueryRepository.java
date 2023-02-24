package com.dalda.dalda_server.domain.vote;

import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.user.Users;
import java.util.Optional;

public interface VoteQueryRepository {

    Optional<Votes> findByUserAndComment(Users user, Comments comment);
}
