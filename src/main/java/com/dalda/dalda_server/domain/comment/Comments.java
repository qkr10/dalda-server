package com.dalda.dalda_server.domain.comment;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.tagcomment.TagComment;
import com.dalda.dalda_server.domain.user.Users;
import com.dalda.dalda_server.domain.vote.Votes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_comment_id")
    private Comments rootComment;
    @OneToMany(mappedBy = "rootComment")
    private List<Comments> subComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mention_user_id")
    private Users mentionUser;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String description;

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

    @OneToMany(mappedBy = "comment")
    private Set<Votes> votes;

    @Transient private boolean isLike = false;
    @Transient public boolean getIsLike() {return isLike;}
    @Transient public void setIsLike(boolean isLike) {this.isLike = isLike;}

    @Builder
    public Comments(String content, String description, Long upvote, Long upvoteSum, Long subCommentSum) {
        this.content = content;
        this.description = description;
        this.upvote = upvote;
        this.upvoteSum = upvoteSum;
        this.subCommentSum = subCommentSum;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setRootComment(Comments rootComment) { this.rootComment = rootComment; }

    public void setMentionUser(Users mentionUser) { this.mentionUser = mentionUser; }

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

    @Transient public static String makeDescription(String content) {
        var parser = Parser.builder().build();
        var renderer = TextContentRenderer.builder().build();
        return renderer.render(parser.parse(content));
    }
}
