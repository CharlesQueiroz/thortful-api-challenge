package api.challenge.thortful.infrastructure.adapters.in.web;

import api.challenge.thortful.application.dto.SwapiCharacterDTO;
import api.challenge.thortful.application.ports.out.StarWarsApiPort;
import io.restassured.RestAssured;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CharacterControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private StarWarsApiPort starWarsApiPort;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @Sql("/setup-characters.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
    @Sql("/setup-characters.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCharacterById_NotExistingInDatabase_FetchFromApi_Success() {
        var characterFromApi = SwapiCharacterDTO.builder()
                .id(2L)
                .name("Darth Vader")
                .height("202")
                .gender("male")
                .homeworld("Tatooine")
                .build();

        when(starWarsApiPort.fetchCharacterById(2L)).thenReturn(Option.of(characterFromApi));

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/2")
                .then()
                .statusCode(200)
                .body("apiId", equalTo(2))
                .body("name", equalTo("Darth Vader"))
                .body("height", equalTo("202"))
                .body("gender", equalTo("MALE"))
                .body("homeworld", equalTo("Tatooine"));

        verify(starWarsApiPort, times(1)).fetchCharacterById(2L);
    }

    @Test
    @Sql("/setup-characters.sql")
    @Sql(scripts = "/cleanup-characters.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCharacterById_NotExistingAnywhere_ReturnNotFound() {
        when(starWarsApiPort.fetchCharacterById(anyLong())).thenReturn(Option.none());

        given()
                .contentType("application/json")
                .when()
                .get("/api/characters/1500")
                .then()
                .statusCode(500);

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
}