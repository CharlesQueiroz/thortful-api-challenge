package api.challenge.thortful.application.ports.in;

import api.challenge.thortful.domain.model.StarshipEntity;
import io.vavr.collection.List;

public interface FetchStarshipsUseCase {

    List<StarshipEntity> execute(List<String> starshipUrls);
}
