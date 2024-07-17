package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.domain.model.StarshipEntity;
import io.vavr.collection.List;
import io.vavr.control.Option;

public interface FetchStarshipsUseCase {

    List<StarshipEntity> execute(Option<List<String>> starshipUrls);
}
