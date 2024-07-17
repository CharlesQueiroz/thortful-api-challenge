package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface FetchFilmsUseCase {

    List<FilmEntity> execute(Option<List<String>> filmUrls);
}
