package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import likelion12th.session.domain.Member;
import likelion12th.session.domain.music.Playlist;
import likelion12th.session.domain.music.Song;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PlaylistRepositoryImplTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PlaylistRepositoryImpl playlistRepository;

    @Test
    @Transactional
    void createPlaylist() {
        //given
        Member member = Member.builder()
                .username("lion")
                .age(24)
                .email("lion@lion.com")
                .build();
        //when
        Playlist playlist = Playlist.builder()
                .title("lion-song")
                .description("song for kind lion")
                .member(member)
                .build();

        //생성한 객체 member, playlist를 영속성 컨텍스트에 등록함
        Long memberId = memberRepository.save(member);
        Long playlistId = playlistRepository.save(playlist);

        //then
        //findByMember 메서드로 조회해서 등록된 playlist가 생성한 playlist와 일치함을 확인함
        Assertions.assertThat(playlist).isEqualTo(playlistRepository.findByMember(member).get(0));
    }

    @Test
    @Transactional
    void duplicateTitle() {
        //given
        Member member = Member.builder()
                .username("lion")
                .age(24)
                .email("lion@lion.com")
                .build();

        Playlist playlistA = Playlist.builder()
                .title("lion-song")
                .description("song for kind lion")
                .member(member)
                .build();

        memberRepository.save(member);
        playlistRepository.save(playlistA);

        //when
        Playlist playlistB = Playlist.builder()
                .title("lion-song")
                .description("song for kind lion")
                .member(member)
                .build();

        //then
        //동일한 title을 가진 playlist 객체를 등록하려고 하면 예외 발생 체크
        assertThrows(DataIntegrityViolationException.class,
                () -> playlistRepository.save(playlistB));
    }
}
