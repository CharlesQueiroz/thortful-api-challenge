package api.challenge.thortful.infrastructure.adapters.in.web;

import api.challenge.thortful.application.dto.CharacterDTO;
import api.challenge.thortful.application.ports.in.GetCharacterByUuidUseCase;
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
public class CharacterController {

    private final GetCharacterByUuidUseCase getCharacterByUuidUseCase;

    @GetMapping("{apiId}")
    public ResponseEntity<CharacterDTO> getCharacterByApiId(@PathVariable Long apiId) {
        log.info("RECEIVED REQUEST TO GET CHARACTER WITH ID: {}", apiId);
        var characterDTO = getCharacterByUuidUseCase.execute(apiId);
        log.info("SUCCESSFULLY RETRIEVED CHARACTER WITH ID: {}", apiId);
        log.debug("CHARACTER RETRIEVED: {}", characterDTO);
        return ok(characterDTO);
    }
}
