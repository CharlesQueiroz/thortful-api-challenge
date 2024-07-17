package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.FilmEntity;
import api.challenge.thortful.domain.repository.FilmRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

@Service
public class FilmService extends CrudService<FilmEntity, FilmRepository> {

    public FilmService(FilmRepository repository) {
        super(repository);
    }

    public Option<FilmEntity> findByTitle(String title) {
        return repository.findByTitle(title);
    }
}
