package api.challenge.thortful.infrastructure.adapters.in.rest;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StarWarsApiAdapter implements StarWarsApiPort {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Value("${swapi.base-url}")
    private String swapiBaseUrl;

    @Override
    @Retryable(
            retryFor = {RuntimeException.class},
            backoff = @Backoff(delay = 100)
    )
    public Option<SwapiCharacterDTO> fetchCharacterById(Long apiId) {
        log.info("Attempting to fetch character with ID: {}", apiId);

        var url = UriComponentsBuilder.fromHttpUrl(swapiBaseUrl)
                .path("/people/{apiId}/")
                .buildAndExpand(apiId)
                .toUriString();

        var request = new Request.Builder().url(url).build();

        try (var response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unsuccessful response: {}", response);
                throw new RuntimeException("API call failed with response code " + response.code());
            }

            var responseBody = Objects.requireNonNull(response.body()).string();
            log.debug("Response body: {}", responseBody);

            var character = objectMapper.readValue(responseBody, SwapiCharacterDTO.class);
            log.info("Successfully fetched character: {}", character);
            return Option.of(character);
        } catch (IOException e) {
            log.error("Failed to fetch character from SWAPI", e);
            throw new RuntimeException("Failed to fetch character from SWAPI", e);
        }
    }
}
