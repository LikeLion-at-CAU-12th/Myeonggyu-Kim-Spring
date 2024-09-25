package likelion12th.session.domain.music;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;
}
