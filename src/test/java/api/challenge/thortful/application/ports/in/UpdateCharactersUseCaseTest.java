package api.challenge.thortful.application.ports.in;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class UpdateCharactersUseCaseTest {

    @Autowired
    private UpdateCharactersUseCase updateCharactersUseCase;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setupWireMockServer() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8081));
        wireMockServer.start();
        configureFor("localhost", 8081);
    }

    @AfterAll
    static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @BeforeEach
    public void setup() {
        wireMockServer.resetAll();
    }

    @Test
    public void testUpdateCharacters() {
        System.out.println("Setting up WireMock stubs...");

        // Mock the first page response
        wireMockServer.stubFor(get(urlEqualTo("/api/people/?page=1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("api/people-page-1.json")));

        // Mock the second page response
        wireMockServer.stubFor(get(urlEqualTo("/api/people/?page=2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("api/people-page-2.json")));

        System.out.println("Executing updateCharactersUseCase.execute()...");

        assertDoesNotThrow(() -> updateCharactersUseCase.execute());

        System.out.println("Verifying requests to WireMock...");

        wireMockServer.verify(1, getRequestedFor(urlEqualTo("/api/people/?page=1")));
        wireMockServer.verify(1, getRequestedFor(urlEqualTo("/api/people/?page=2")));
    }
}