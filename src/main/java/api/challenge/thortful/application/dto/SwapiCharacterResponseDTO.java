package api.challenge.thortful.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;
import lombok.Builder;

@Builder
public record SwapiCharacterResponseDTO(
        int count,
        String next,
        String previous,
        @JsonProperty("results")
        List<SwapiCharacterDTO> results
) {
}
