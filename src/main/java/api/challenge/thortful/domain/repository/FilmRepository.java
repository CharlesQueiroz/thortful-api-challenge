package api.challenge.thortful.domain.repository;

import api.challenge.thortful.domain.model.FilmEntity;
import api.challenge.thortful.infrastructure.adapters.out.persistence.BaseRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmRepository extends BaseRepository<FilmEntity> {

    Option<FilmEntity> findByTitle(String title);
}
