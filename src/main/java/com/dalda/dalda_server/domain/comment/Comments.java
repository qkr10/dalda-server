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
    private Long commentRoot;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT default ''")
    private String content;

    @Column(nullable = false)
    private Long upvote;

    @Column(nullable = false)
    private Long upvoteSum;

    @Column
    private Long subCommentSum;

    @Builder
    public Comments(Long commentRoot, Long userId, String content, Long upvote, Long upvoteSum, Long subCommentSum) {
        this.commentRoot = commentRoot;
        this.userId = userId;
        this.content = content;
        this.upvote = upvote;
        this.upvoteSum = upvoteSum;
        this.subCommentSum = subCommentSum;
    }
}
