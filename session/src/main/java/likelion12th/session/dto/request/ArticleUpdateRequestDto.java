package likelion12th.session.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleUpdateRequestDto {
    private String title;
    private String content;
    private List<Long> categoryIds;
}
