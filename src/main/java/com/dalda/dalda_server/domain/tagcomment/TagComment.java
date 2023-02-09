package com.dalda.dalda_server.domain.tagcomment;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.tag.Tags;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TagComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tags tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comment;

    public void setComment(Comments comment) {
        this.comment = comment;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }
}
