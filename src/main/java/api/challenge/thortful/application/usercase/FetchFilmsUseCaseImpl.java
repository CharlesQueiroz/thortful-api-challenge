package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.mapper.FilmMapper;
import api.challenge.thortful.application.ports.in.FetchFilmsUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.collection.List.empty;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchFilmsUseCaseImpl implements FetchFilmsUseCase {

    private final FilmMapper filmMapper;
    private final StarWarsApiPort starWarsApiPort;

    @Override
    @Transactional
    public List<FilmEntity> execute(Option<List<String>> filmUrls) {
        return filmUrls.map(urls -> starWarsApiPort.fetchFilmsByUrls(urls)
                        .map(filmMapper::swapiDtoToEntity))
                .getOrElse(empty());
    }
}
