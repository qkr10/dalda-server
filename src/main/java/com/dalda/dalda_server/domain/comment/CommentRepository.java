package com.dalda.dalda_server.domain.comment;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long>, CommentQueryRepository {

    Optional<Comments> findById(Long id);
}
