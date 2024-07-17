package api.challenge.thortful.infrastructure.adapters.out.web;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.dto.SwapiCharacterResponseDTO;
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

/**
 * Adapter for interacting with the Star Wars API (SWAPI).
 * <p>
 * This adapter handles the retrieval of character, film, and starship data from SWAPI.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StarWarsApiAdapter implements StarWarsApiPort {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Value("${swapi.base-url}")
    private String swapiBaseUrl;

    /**
     * Fetches a character by API ID from SWAPI.
     *
     * @param apiId the API ID of the character.
     * @return an {@link Option} containing the character data if found.
     */
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

    /**
     * Fetches a paginated list of characters from SWAPI.
     *
     * @param page the page number to retrieve.
     * @param size the number of records per page.
     * @return an {@link Option} containing the paginated character response if found.
     */
    @Override
    public Option<SwapiCharacterResponseDTO> fetchCharactersPaginated(int page, int size) {
        log.info("ATTEMPTING TO FETCH CHARACTERS PAGE: {}", page);

        var url = UriComponentsBuilder.fromHttpUrl(swapiBaseUrl)
                .path("/people/")
                .queryParam("page", page)
                .build()
                .toUriString();
        return fetchFromApi(url, SwapiCharacterResponseDTO.class);
    }

    /**
     * Fetches a film by URL from SWAPI.
     *
     * @param url the URL of the film.
     * @return the {@link SwapiFilmDTO} containing the film data.
     * @throws RuntimeException if the film could not be fetched.
     */
    public SwapiFilmDTO fetchFilmByUrl(String url) {
        return fetchFromApi(url, SwapiFilmDTO.class)
                .getOrElseThrow(() -> new RuntimeException("FAILED TO FETCH FILM FROM SWAPI"));
    }

    /**
     * Fetches a list of films by their URLs from SWAPI.
     *
     * @param urls the list of film URLs.
     * @return a list of {@link SwapiFilmDTO} containing the film data.
     */
    public List<SwapiFilmDTO> fetchFilmsByUrls(List<String> urls) {
        return urls.map(this::fetchFilmByUrl);
    }

    /**
     * Fetches a starship by URL from SWAPI.
     *
     * @param url the URL of the starship.
     * @return the {@link SwapiStarshipDTO} containing the starship data.
     * @throws RuntimeException if the starship could not be fetched.
     */
    public SwapiStarshipDTO fetchStarshipByUrl(String url) {
        return fetchFromApi(url, SwapiStarshipDTO.class)
                .getOrElseThrow(() -> new RuntimeException("FAILED TO FETCH STARSHIP FROM SWAPI"));
    }

    /**
     * Fetches a list of starships by their URLs from SWAPI.
     *
     * @param urls the list of starship URLs.
     * @return a list of {@link SwapiStarshipDTO} containing the starship data.
     */
    public List<SwapiStarshipDTO> fetchStarshipsByUrls(List<String> urls) {
        return urls.map(this::fetchStarshipByUrl);
    }

    /**
     * Fetches data from SWAPI for a given URL and response type.
     *
     * @param url          the URL to fetch data from.
     * @param responseType the type of response expected.
     * @param <T>          the type of the response.
     * @return an {@link Option} containing the fetched data if successful.
     */
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