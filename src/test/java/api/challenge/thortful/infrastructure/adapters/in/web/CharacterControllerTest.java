package api.challenge.thortful.infrastructure.adapters.in.web;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.vavr.control.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CharacterControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private StarWarsApiPort starWarsApiPort;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setupWireMockServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8081));
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        wireMockServer.resetAll();
    }

    @Test
    @Sql("/setup-characters.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCharacterById_ExistingInDatabase_Success() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/1")
                .then()
                .statusCode(200)
                .body("apiId", equalTo(1))
                .body("name", equalTo("Luke Skywalker"))
                .body("height", equalTo("172"))
                .body("gender", equalTo("MALE"))
                .body("homeworld", equalTo("Tatooine"));

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    public void getCharacterById_NotExistingInDatabase_FetchFromApi_Success() {
        var characterFromApi = SwapiCharacterDTO.builder()
                .id(1L)
                .name("Darth Vader")
                .height("202")
                .gender("male")
                .homeworld("Tatooine")
                .build();

        wireMockServer.stubFor(get(urlEqualTo("/people/1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("api/character-1.json")));

        when(starWarsApiPort.fetchCharacterById(1L)).thenReturn(Option.of(characterFromApi));

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/1")
                .then()
                .statusCode(200)
                .body("apiId", equalTo(1))
                .body("name", equalTo("Darth Vader"))
                .body("height", equalTo("202"))
                .body("gender", equalTo("MALE"))
                .body("homeworld", equalTo("Tatooine"));

        verify(starWarsApiPort, times(1)).fetchCharacterById(1L);
    }

    @Test
    @Sql("/setup-characters.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCharacterById_NotExistingAnywhere_ReturnNotFound() {
        when(starWarsApiPort.fetchCharacterById(anyLong())).thenReturn(Option.none());

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/1500")
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("CHARACTER NOT FOUND WITH ID: 1500"))
                .body("path", equalTo("/api/characters/1500"));

        verify(starWarsApiPort, times(1)).fetchCharacterById(1500L);
    }

    @Test
    public void getCharacterById_InvalidId_ReturnBadRequest() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/invalid")
                .then()
                .statusCode(400);

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    @Sql("/setup-characters-and-films.sql")
    @Sql(scripts = "/cleanup-characters-and-films.sql", executionPhase = AFTER_TEST_METHOD)
    public void getFilmsByCharacterUuid_ExistingInDatabase_Success() {
        var uuid = "4b90a37e-e408-4c00-b373-5b0f961c00fc";

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/" + uuid + "/films")
                .then()
                .statusCode(200)
                .body("[0].title", equalTo("A New Hope"))
                .body("[0].episodeId", equalTo(4))
                .body("[0].director", equalTo("George Lucas"))
                .body("[0].producer", equalTo("Gary Kurtz, Rick McCallum"))
                .body("[0].releaseDate", equalTo("1977-05-25"));

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    @Sql("/setup-characters-and-starships.sql")
    @Sql(scripts = "/cleanup-characters-and-starships.sql", executionPhase = AFTER_TEST_METHOD)
    public void getStarshipsByCharacterUuid_ExistingInDatabase_Success() {
        var uuid = "4b90a37e-e408-4c00-b373-5b0f961c00fc";

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/" + uuid + "/starships")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("X-wing"))
                .body("[0].model", equalTo("T-65 X-wing"))
                .body("[0].costInCredits", equalTo("149999"))
                .body("[0].length", equalTo("12.5"))
                .body("[1].name", equalTo("Imperial shuttle"))
                .body("[1].model", equalTo("Lambda-class T-4a shuttle"))
                .body("[1].costInCredits", equalTo("240000"))
                .body("[1].length", equalTo("20"));

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    @Sql("/setup-characters-without-starships.sql")
    @Sql(scripts = "/cleanup-characters-and-starships.sql", executionPhase = AFTER_TEST_METHOD)
    public void getStarshipsByCharacterUuid_NoStarships_ReturnEmptyList() {
        var uuid = "4b90a37e-e408-4c00-b373-5b0f961c00fc";

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/" + uuid + "/starships")
                .then()
                .statusCode(200)
                .body("", hasSize(0));

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    @Sql(scripts = "/cleanup-characters-and-starships.sql", executionPhase = BEFORE_TEST_METHOD)
    public void getStarshipsByCharacterUuid_CharacterNotFound_ReturnNotFound() {
        var uuid = "non-existent-uuid";

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/" + uuid + "/starships")
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("CHARACTER NOT FOUND WITH UUID: " + uuid))
                .body("path", equalTo("/api/characters/" + uuid + "/starships"));

        verify(starWarsApiPort, never()).fetchCharacterById(anyLong());
    }

    @Test
    @Sql("/setup-characters-pagination.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCharactersByPage_Success() {
        given()
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .when()
                .get("/api/characters")
                .then()
                .statusCode(200)
                .body("data[0].name", equalTo("Luke Skywalker"))
                .body("data[1].name", equalTo("Darth Vader"))
                .body("data[2].name", equalTo("Leia Organa"))
                .body("currentPage", equalTo(0))
                .body("pageSize", equalTo(10))
                .body("totalRecords", equalTo(3));
    }

    @Test
    public void getCharactersByPage_BadRequest() {
        given()
                .contentType("application/json")
                .param("page", "invalidPage")
                .param("size", "10")
                .when()
                .get("/api/characters")
                .then()
                .statusCode(400);
    }

    @Test
    public void getCharactersByPage_EmptyResult() {
        given()
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .when()
                .get("/api/characters")
                .then()
                .statusCode(200)
                .body("data", hasSize(0))
                .body("currentPage", equalTo(0))
                .body("pageSize", equalTo(10))
                .body("totalRecords", equalTo(0));
    }
}