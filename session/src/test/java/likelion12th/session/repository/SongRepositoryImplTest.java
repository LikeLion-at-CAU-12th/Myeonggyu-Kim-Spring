package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import likelion12th.session.domain.music.Song;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongRepositoryImplTest {

    @Autowired
    SongRepositoryImpl repository;

    @Test
    @Transactional
    @Rollback
    void save() {
        //given
        Song song = Song.builder()
                .title("lion king")
                .build();

        //when
        Long saveId = repository.save(song);
        Song result = repository.findOne(saveId);

        //then
        assertThat(song).isEqualTo(result);
    }

    @Test
    @Transactional
    @Rollback
    void findOne() {
        //given
        Song song = Song.builder()
                .title("lion king")
                .build();

        //when
        repository.save(song);
        List<Song> result = repository.findByTitle(song.getTitle());

        //then
        assertThat(song.getTitle()).isEqualTo(result.get(0).getTitle());
    }

    @Test
    @Transactional
    @Rollback
    void findUnExistSong() {
        //given
        String title = "lion king";

        //when
        List<Song> result = repository.findByTitle(title);

        //then
        //존재하지 않는 노래를 제목으로 조회하려고 하는 경우 빈 리스트를 반환하는 것을 확인
        assertThat(result.isEmpty()).isTrue();
    }
}