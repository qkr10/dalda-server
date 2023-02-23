package com.dalda.dalda_server.domain.comment;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comments, Long>, CommentQueryRepository {

    @NonNull Optional<Comments> findById(@NonNull Long id);

    @Modifying
    @Query("UPDATE Comments SET upvote=upvote+1 WHERE id=:id")
    Long increaseUpvote(Long id);

    @Modifying
    @Query("UPDATE Comments SET upvote=upvote-1 WHERE id=:id")
    Long decreaseUpvote(Long id);

    @Modifying
    @Query("UPDATE Comments SET upvoteSum=upvoteSum+1 WHERE id=:id")
    Long increaseUpvoteSum(Long id);

    @Modifying
    @Query("UPDATE Comments SET upvoteSum=upvoteSum-1 WHERE id=:id")
    Long decreaseUpvoteSum(Long id);
}
