package likelion12th.session.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JoinRequest {
    private String username;
    private String password;
}
