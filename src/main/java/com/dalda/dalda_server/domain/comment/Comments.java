package com.dalda.dalda_server.domain.comment;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.tagcomment.TagComment;
import com.dalda.dalda_server.domain.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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

    @Column(columnDefinition = "TEXT default ''")
    private String content;

    @Column(nullable = false)
    private Long upvote;

    @Column(nullable = false)
    private Long upvoteSum;

    @Column
    private Long subCommentSum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "comment")
    private List<TagComment> tagComments;

    @Builder
    public Comments(Long commentRoot, String content, Long upvote, Long upvoteSum, Long subCommentSum) {
        this.commentRoot = commentRoot;
        this.content = content;
        this.upvote = upvote;
        this.upvoteSum = upvoteSum;
        this.subCommentSum = subCommentSum;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void addTagComment(TagComment tagComment) {
        this.tagComments.add(tagComment);
        tagComment.setComment(this);
    }

    public List<String> getTagList() {
        List<String> result = null;

        if (tagComments != null) {
            result = tagComments
                    .stream()
                    .map(tagComment -> tagComment.getTag().getName())
                    .toList();
        }

        return result;
    }
}
