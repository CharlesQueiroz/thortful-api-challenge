package api.challenge.thortful.infrastructure.adapters.out.persistence;

import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmRepository extends BaseRepository<FilmEntity> {

    Option<FilmEntity> findByTitle(String title);
}
