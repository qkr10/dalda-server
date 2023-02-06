package com.dalda.dalda_server.domain.tag_comment;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Tag_Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tag_id;

    @Column(nullable = false)
    private Long comment_id;

    @Builder
    public Tag_Comment(Long tag_id, Long comment_id) {
        this.tag_id = tag_id;
        this.comment_id = comment_id;
    }
}
