package api.challenge.thortful.application.ports.in.scheduler;

import api.challenge.thortful.application.ports.in.UpdateCharactersUseCase;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterUpdateScheduler {

    private final UpdateCharactersUseCase updateCharactersUseCase;

    @Scheduled(fixedRate = 30000)
    public void scheduleCharacterUpdate() {
        log.info("STARTING SCHEDULED CHARACTER UPDATE");
        Try.run(updateCharactersUseCase::execute)
                .onSuccess(v -> log.info("COMPLETED SCHEDULED CHARACTER UPDATE"))
                .onFailure(e -> log.error("ERROR DURING SCHEDULED CHARACTER UPDATE", e));
    }
}