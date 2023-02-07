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
    private Long tagId;

    @Column(nullable = false)
    private Long commentId;

    @Builder
    public Tag_Comment(Long tagId, Long commentId) {
        this.tagId = tagId;
        this.commentId = commentId;
    }
}
