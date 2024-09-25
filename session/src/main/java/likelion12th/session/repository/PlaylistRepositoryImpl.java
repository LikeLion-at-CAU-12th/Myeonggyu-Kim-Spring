package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion12th.session.domain.Member;
import likelion12th.session.domain.music.Playlist;
import likelion12th.session.domain.music.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaylistRepositoryImpl {

    @PersistenceContext
    EntityManager em;

    public Long save(Playlist playlist) {
        em.persist(playlist);
        return playlist.getId();
    }

    public List<Playlist> findByMember(Member member) {
        return em.createQuery("select p from Playlist p where p.member = :member", Playlist.class)
                .setParameter("member", member)
                .getResultList();
    }
}
