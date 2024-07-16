package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.collection.List;

public interface FetchFilmsUseCase {

    List<FilmEntity> execute(List<String> filmUrls);
}
