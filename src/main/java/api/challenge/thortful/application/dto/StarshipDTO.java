package api.challenge.thortful.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing a Star Wars starship")
public record StarshipDTO(

        @Schema(description = "Unique identifier of the starship", example = "1")
        Long id,

        @Schema(description = "Name of the starship", example = "X-wing")
        String name,

        @Schema(description = "Model of the starship", example = "T-65 X-wing")
        String model,

        @Schema(description = "Cost of the starship in credits", example = "149999")
        String costInCredits,

        @Schema(description = "Length of the starship in meters", example = "12.5")
        String length,

        @Schema(description = "Number of passengers the starship can carry", example = "1")
        String passengers,

        @Schema(description = "Cargo capacity of the starship", example = "110")
        String cargoCapacity,

        @Schema(description = "Class of the starship", example = "Starfighter")
        String starshipClass
) {}