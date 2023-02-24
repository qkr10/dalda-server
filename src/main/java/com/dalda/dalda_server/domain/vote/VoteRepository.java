package com.dalda.dalda_server.domain.vote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Votes, Long>, VoteQueryRepository {

}
