package api.challenge.thortful.infrastructure.adapters.in.web;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
import api.challenge.thortful.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/characters")
@Tag(name = "Characters", description = "Endpoints for managing Star Wars characters")
public class CharacterController {

    private final GetCharacterByUuidUseCase getCharacterByUuidUseCase;

    @GetMapping("{apiId}")
    @Operation(summary = "Get character by API ID", description = "Returns a character based on the provided API ID")
    public ResponseEntity<CharacterDTO> getCharacterByApiId(@PathVariable Long apiId) {
        log.info("RECEIVED REQUEST TO GET CHARACTER WITH ID: {}", apiId);
        var characterDTO = getCharacterByUuidUseCase.execute(apiId)
                .getOrElseThrow(() -> new ResourceNotFoundException("CHARACTER NOT FOUND WITH ID: " + apiId));

        log.debug("CHARACTER RETRIEVED: {}", characterDTO);
        return ok(characterDTO);
    }
}
