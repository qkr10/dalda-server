package com.dalda.dalda_server.domain.comment;

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
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long comment_root;

    @Column(nullable = false)
    private Long user_id;

    @Column(columnDefinition = "TEXT default ''")
    private String content;

    @Column(nullable = false)
    private Long upvote;

    @Column(nullable = false)
    private Long upvote_sum;

    @Column
    private Long sub_comment_sum;

    @Builder
    public Comments(Long comment_root, Long user_id, String content, Long upvote, Long upvote_sum, Long sub_comment_sum) {
        this.comment_root = comment_root;
        this.user_id = user_id;
        this.content = content;
        this.upvote = upvote;
        this.upvote_sum = upvote_sum;
        this.sub_comment_sum = sub_comment_sum;
    }
}
