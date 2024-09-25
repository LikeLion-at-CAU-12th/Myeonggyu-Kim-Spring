package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion12th.session.domain.music.Playlist;
import likelion12th.session.domain.music.Song;
import likelion12th.session.domain.music.SongPlaylist;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongPlaylistRepositoryImpl {

    @PersistenceContext
    EntityManager em;

    public Long save(SongPlaylist songPlaylist) {
        em.persist(songPlaylist);
        return songPlaylist.getId();
    }

    public List<SongPlaylist> findByPlaylist(Playlist playlist) {
        return em.createQuery("select sp from SongPlaylist sp where sp.playlist = :playlist", SongPlaylist.class)
                .setParameter("playlist", playlist)
                .getResultList();
    }
}
