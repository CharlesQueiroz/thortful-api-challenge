package api.challenge.thortful.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SwapiStarshipDTO(
        String name,
        String model,
        @JsonProperty("cost_in_credits")
        String costInCredits,
        String length,
        String passengers,
        @JsonProperty("cargo_capacity")
        String cargoCapacity,
        @JsonProperty("starship_class")
        String starshipClass
) {}