package likelion12th.session.domain;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(unique = true)
    private String email;
    private int age;
    private String password;

    @Builder
    public Member(String username, String email, int age, String password) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;
    }
}
