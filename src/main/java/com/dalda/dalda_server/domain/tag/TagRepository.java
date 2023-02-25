package com.dalda.dalda_server.domain.tag;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tags, Long> {

    Optional<Tags> findByName(String name);
}
