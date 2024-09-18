package likelion12th.session.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion12th.session.domain.music.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongRepositoryImpl {

    @PersistenceContext
    EntityManager em;

    public Long save(Song song) {
        em.persist(song);
        return song.getId();
    }

    public Song findOne(Long id) {
        return em.find(Song.class, id);
    }

    public List<Song> findAll() {
        return em.createQuery("select m from Song m", Song.class).getResultList();
    }

    public List<Song> findByTitle(String title) {
        return em.createQuery("select s from Song s where s.title = :title", Song.class)
                .setParameter("title", title)
                .getResultList();
    }
}
