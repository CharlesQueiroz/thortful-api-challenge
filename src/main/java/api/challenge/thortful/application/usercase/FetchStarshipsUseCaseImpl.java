package api.challenge.thortful.application.usercase;

import api.challenge.thortful.application.mapper.StarshipMapper;
import api.challenge.thortful.application.ports.in.FetchStarshipsUseCase;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import api.challenge.thortful.domain.model.StarshipEntity;
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
public class FetchStarshipsUseCaseImpl implements FetchStarshipsUseCase {

    private final StarshipMapper starshipMapper;
    private final StarWarsApiPort starWarsApiPort;

    @Override
    @Transactional
    public List<StarshipEntity> execute(Option<List<String>> starshipUrls) {
        return starshipUrls.map(urls -> starWarsApiPort.fetchStarshipsByUrls(urls)
                        .map(starshipMapper::swapiDtoToEntity))
                .getOrElse(empty());
    }
}
