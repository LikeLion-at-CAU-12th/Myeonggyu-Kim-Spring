package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import likelion12th.session.domain.Member;
import likelion12th.session.domain.music.Playlist;
import likelion12th.session.domain.music.Song;
import likelion12th.session.domain.music.SongPlaylist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SongPlaylistRepositoryImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SongRepositoryImpl songRepository;

    @Autowired
    PlaylistRepositoryImpl playlistRepository;

    @Autowired
    SongPlaylistRepositoryImpl songPlaylistRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void addSong() {
        //given
        Member member = Member.builder()
                .username("lion")
                .age(24)
                .email("lion@lion.com")
                .build();
        memberRepository.save(member);

        Playlist playlist = Playlist.builder()
                .title("lion-song")
                .description("song for kind lion")
                .member(member)
                .build();
        playlistRepository.save(playlist);

        Song song = Song.builder()
                .title("lion-king")
                .build();
        songRepository.save(song);

        //when
        SongPlaylist songPlaylist = new SongPlaylist(song, playlist);
        songPlaylistRepository.save(songPlaylist);

        //then
        List<SongPlaylist> result = songPlaylistRepository.findByPlaylist(playlist);
        Assertions.assertThat(song).isEqualTo(result.get(0).getSong());
    }

    @Test
    @Transactional
    void addNotExistSong() {
        //given
        Member member = Member.builder()
                .username("lion")
                .age(24)
                .email("lion@lion.com")
                .build();
        memberRepository.save(member);

        Playlist playlist = Playlist.builder()
                .title("lion-song")
                .description("song for kind lion")
                .member(member)
                .build();
        playlistRepository.save(playlist);

        Song song = Song.builder()
                .title("lion-king")
                .build();
        //songRepository.save(song);
        //song 객체를 영속하지 않음

        //when
        //영속되지 않은 song 객체를 가진 songPlaylist 객체 생성
        SongPlaylist songPlaylist = new SongPlaylist(song, playlist);

        //then
        songPlaylistRepository.save(songPlaylist);
        //em.flush()를 호출했을 때 song 객체가 영속되거나 DB에 존재하지 않기 때문에 예외 발생 체크
        assertThrows(IllegalStateException.class,
                () -> em.flush());
    }
}