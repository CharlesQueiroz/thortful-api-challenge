package api.challenge.thortful.infrastructure.adapters.in.web;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.dto.FilmDTO;
import api.challenge.thortful.application.dto.StarshipDTO;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
import api.challenge.thortful.application.ports.in.GetCharactersByPageUseCase;
import api.challenge.thortful.application.ports.in.GetEntitiesByCharacterUuidUseCase;
import api.challenge.thortful.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for managing Star Wars characters.
 * <p>
 * Provides endpoints to retrieve character details, films, starships,
 * and paginated character lists.
 * </p>
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/characters", produces = "application/json")
@Tag(name = "Characters", description = "Endpoints for managing Star Wars characters")
public class CharacterController {

    private final GetCharacterByUuidUseCase getCharacterByUuidUseCase;
    private final GetCharactersByPageUseCase getCharactersByPageUseCase;
    private final GetEntitiesByCharacterUuidUseCase<FilmDTO> getFilmsByCharacterUuidUseCase;
    private final GetEntitiesByCharacterUuidUseCase<StarshipDTO> getStarshipsByCharacterUuidUseCase;

    public CharacterController(
            GetCharacterByUuidUseCase getCharacterByUuidUseCase,
            GetCharactersByPageUseCase getCharactersByPageUseCase,
            @Qualifier("getFilmsByCharacterUuidUseCase") GetEntitiesByCharacterUuidUseCase<FilmDTO> getFilmsByCharacterUuidUseCase,
            @Qualifier("getStarshipsByCharacterUuidUseCase") GetEntitiesByCharacterUuidUseCase<StarshipDTO> getStarshipsByCharacterUuidUseCase) {
        this.getCharacterByUuidUseCase = getCharacterByUuidUseCase;
        this.getCharactersByPageUseCase = getCharactersByPageUseCase;
        this.getFilmsByCharacterUuidUseCase = getFilmsByCharacterUuidUseCase;
        this.getStarshipsByCharacterUuidUseCase = getStarshipsByCharacterUuidUseCase;
    }

    @GetMapping("{apiId}")
    @Operation(summary = "Get character by API ID", description = "Retrieves a Star Wars character based on the provided API ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
    })
    public ResponseEntity<?> getCharacterByApiId(
            @Parameter(description = "API ID of the character", required = true, example = "1")
            @PathVariable Long apiId) {
        log.info("RECEIVED REQUEST TO GET CHARACTER WITH ID: {}", apiId);
        var characterDTO = getCharacterByUuidUseCase.execute(apiId)
                .getOrElseThrow(() -> new ResourceNotFoundException("CHARACTER NOT FOUND WITH ID: " + apiId));

        log.debug("CHARACTER RETRIEVED: {}", characterDTO);
        return ok(characterDTO);
    }

    @GetMapping("/{uuid}/films")
    @Operation(summary = "Get films by character UUID", description = "Retrieves a list of films in which the character with the given UUID appears")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Films found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FilmDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<?> getFilmsByCharacterUuid(
            @Parameter(description = "UUID of the character", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String uuid) {
        log.info("RECEIVED REQUEST TO GET FILMS FOR CHARACTER WITH UUID: {}", uuid);
        var films = getFilmsByCharacterUuidUseCase.execute(uuid);

        log.debug("FILMS RETRIEVED FOR CHARACTER WITH UUID: {}: {}", uuid, films);
        return ok(films.toJavaList());
    }

    @GetMapping("{uuid}/starships")
    @Operation(summary = "Get starships by character UUID", description = "Returns a list of starships associated with a character based on the provided UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Starships found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = StarshipDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Character not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<?> getStarshipsByCharacterUuid(
            @Parameter(description = "UUID of the character", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String uuid) {

        log.info("RECEIVED REQUEST TO GET STARSHIPS FOR CHARACTER WITH UUID: {}", uuid);
        var starships = getStarshipsByCharacterUuidUseCase.execute(uuid);

        log.debug("STARSHIPS RETRIEVED FOR CHARACTER WITH UUID: {}: {}", uuid, starships);
        return ok(starships.toJavaList());
    }

    @GetMapping
    @Operation(summary = "Get characters by page", description = "Retrieves a list of Star Wars characters by page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characters found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    public ResponseEntity<?> getCharactersByPage(
            @Parameter(description = "Page number", required = true, example = "0")
            @RequestParam int page,
            @Parameter(description = "Page size", required = true, example = "10")
            @RequestParam int size) {

        log.info("RECEIVED REQUEST TO GET CHARACTERS FOR PAGE: {}, SIZE: {}", page, size);
        var paginatedCharacters = getCharactersByPageUseCase.execute(page, size);

        log.debug("CHARACTERS RETRIEVED FOR PAGE: {}, SIZE: {}: {}", page, size, paginatedCharacters);
        return ok(paginatedCharacters);
    }
}
