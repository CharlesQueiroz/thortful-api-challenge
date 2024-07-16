package api.challenge.thortful.application.dto;

import api.challenge.thortful.domain.model.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "DTO representing a Star Wars character")
public record CharacterDTO(
        @Schema(description = "Unique identifier of the character", example = "1")
        Long id,

        @Schema(description = "API ID of the character from the external Star Wars API", example = "1001")
        Long apiId,

        @Schema(description = "UUID of the character", example = "4b90a37e-e408-4c00-b373-5b0f961c00fc")
        String uuid,

        @Schema(description = "Name of the character", example = "Luke Skywalker")
        String name,

        @Schema(description = "Height of the character in centimeters", example = "172")
        String height,

        @Schema(description = "Birth year of the character", example = "19BBY")
        String birthYear,

        @Schema(description = "Gender of the character", example = "MALE", allowableValues = {"MALE", "FEMALE", "NONE"})
        GenderType gender,

        @Schema(description = "Homeworld of the character", example = "Tatooine")
        String homeworld
) {
}