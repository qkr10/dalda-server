package com.dalda.dalda_server.domain.vote;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.user.Users;
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
public class Votes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comment;

    public void setComment(Comments comment) {
        this.comment = comment;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
