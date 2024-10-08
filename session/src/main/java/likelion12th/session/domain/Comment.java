package likelion12th.session.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    @Builder
    public Comment(String content, Article article) {
        this.content = content;
        this.article = article;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
