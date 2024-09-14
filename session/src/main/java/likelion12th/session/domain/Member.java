package likelion12th.session.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;
    private String email;
    private int age;

    @Builder
    public Member(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
