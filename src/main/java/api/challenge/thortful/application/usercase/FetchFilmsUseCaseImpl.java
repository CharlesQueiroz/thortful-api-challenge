package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.mapper.FilmMapper;
import api.challenge.thortful.application.ports.in.FetchFilmsUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.FilmEntity;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchFilmsUseCaseImpl implements FetchFilmsUseCase {

    private final FilmMapper filmMapper;
    private final StarWarsApiPort starWarsApiPort;

    @Override
    @Transactional
    public List<FilmEntity> execute(List<String> filmUrls) {
        return starWarsApiPort.fetchFilmsByUrls(filmUrls)
                .map(filmMapper::swapiDtoToEntity);
    }
}
