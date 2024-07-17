package api.challenge.thortful.domain.service;

import api.challenge.thortful.domain.model.FilmEntity;
import api.challenge.thortful.domain.repository.FilmRepository;
import io.vavr.control.Option;
import org.springframework.stereotype.Service;

/**
 * Service class for managing {@link FilmEntity} entities.
 * <p>
 * This service provides CRUD operations inherited from {@link CrudService}
 * and custom methods specific to {@link FilmEntity}.
 * </p>
 */
@Service
public class FilmService extends CrudService<FilmEntity, FilmRepository> {

    public FilmService(FilmRepository repository) {
        super(repository);
    }

    /**
     * Finds a {@link FilmEntity} by its title.
     *
     * @param title the title of the film to find.
     * @return an {@link Option} containing the found {@link FilmEntity}, or {@link Option#none()} if no film is found.
     */
    public Option<FilmEntity> findByTitle(String title) {
        return repository.findByTitle(title);
    }
}
