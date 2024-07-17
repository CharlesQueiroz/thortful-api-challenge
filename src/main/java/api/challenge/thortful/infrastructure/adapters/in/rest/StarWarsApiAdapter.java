package api.challenge.thortful.infrastructure.adapters.in.rest;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.dto.SwapiFilmDTO;
import api.challenge.thortful.application.dto.SwapiStarshipDTO;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.List;
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
        log.info("ATTEMPTING TO FETCH CHARACTER WITH ID: {}", apiId);

        var url = UriComponentsBuilder.fromHttpUrl(swapiBaseUrl)
                .path("/people/{apiId}/")
                .buildAndExpand(apiId)
                .toUriString();
        return fetchFromApi(url, SwapiCharacterDTO.class);
    }

    public SwapiFilmDTO fetchFilmByUrl(String url) {
        return fetchFromApi(url, SwapiFilmDTO.class)
                .getOrElseThrow(() -> new RuntimeException("FAILED TO FETCH FILM FROM SWAPI"));
    }

    public List<SwapiFilmDTO> fetchFilmsByUrls(List<String> urls) {
        return urls.map(this::fetchFilmByUrl);
    }

    public SwapiStarshipDTO fetchStarshipByUrl(String url) {
        return fetchFromApi(url, SwapiStarshipDTO.class)
                .getOrElseThrow(() -> new RuntimeException("FAILED TO FETCH STARSHIP FROM SWAPI"));
    }

    public List<SwapiStarshipDTO> fetchStarshipsByUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return List.empty();
        }
        return urls.map(this::fetchStarshipByUrl);
    }

    private <T> Option<T> fetchFromApi(String url, Class<T> responseType) {
        var request = new Request.Builder().url(url).build();

        try (var response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("UNSUCCESSFUL RESPONSE: {}", response);
                throw new RuntimeException("API CALL FAILED WITH RESPONSE CODE " + response.code());
            }

            var responseBody = Objects.requireNonNull(response.body()).string();
            log.debug("RESPONSE BODY: {}", responseBody);

            var result = objectMapper.readValue(responseBody, responseType);
            log.info("SUCCESSFULLY FETCHED DATA: {}", result);
            return Option.of(result);
        } catch (IOException e) {
            log.error("FAILED TO FETCH DATA FROM SWAPI", e);
            throw new RuntimeException("FAILED TO FETCH DATA FROM SWAPI", e);
        }
    }
}