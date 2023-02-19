package com.dalda.dalda_server.domain.user;

import com.dalda.dalda_server.domain.BaseTimeEntity;
import com.dalda.dalda_server.domain.comment.Comments;
import com.dalda.dalda_server.domain.user.Role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false, unique = true)
    private String handle;

    @OneToMany(mappedBy = "user")
    private List<Comments> comments;

    @Builder
    public Users(String name, String email, String picture, Role role, String handle) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.handle = handle;
    }

    public Users update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


    public static String getRandomHandle() {
        String handle = "";
        for (int i = 0; i < 10; i++) {
            char ch = (char) (36 * Math.random());
            if (ch < 10) {
                ch += '0';
            }
            else {
                ch += 'a' - 10;
            }
            handle += ch;
        }
        return handle;
    }

    public void addComment(Comments comment) {
        this.comments.add(comment);
    }
}
