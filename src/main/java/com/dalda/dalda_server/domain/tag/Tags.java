package com.dalda.dalda_server.domain.tag;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.tagcomment.TagComment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Tags extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<TagComment> tagComments = new ArrayList<>();

    @Builder
    public Tags(String name) {
        this.name = name;
    }

    public void addTagComment(TagComment tagComment) {
        this.tagComments.add(tagComment);
        tagComment.setTag(this);
    }
}
