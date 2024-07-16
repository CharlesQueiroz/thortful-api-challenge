package api.challenge.thortful.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleRuntimeException() {
        var ex = new RuntimeException("Test runtime exception");
        when(webRequest.getDescription(false)).thenReturn("uri=/test");

        var response = globalExceptionHandler.handleRuntimeException(ex, webRequest);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        var errorDetail = (ErrorDetail) response.getBody();
        assertEquals("Test runtime exception", errorDetail.message());
        assertEquals(BAD_REQUEST.value(), errorDetail.status());
        assertEquals("Bad Request", errorDetail.error());
        assertEquals("/test", errorDetail.path());
    }

    @Test
    public void testHandleResourceNotFoundException() {
        var ex = new ResourceNotFoundException("Resource not found");
        when(webRequest.getDescription(false)).thenReturn("uri=/resource");

        var response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        assertEquals(NOT_FOUND, response.getStatusCode());
        var errorDetail = (ErrorDetail) response.getBody();
        assertEquals("Resource not found", errorDetail.message());
        assertEquals(NOT_FOUND.value(), errorDetail.status());
        assertEquals("Not Found", errorDetail.error());
        assertEquals("/resource", errorDetail.path());
    }

    @Test
    public void testHandleGenericException() {
        var ex = new Exception("Test generic exception");
        when(webRequest.getDescription(false)).thenReturn("uri=/generic");

        var response = globalExceptionHandler.handleGenericException(ex, webRequest);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        var errorDetail = (ErrorDetail) response.getBody();
        assertEquals("Test generic exception", errorDetail.message());
        assertEquals(BAD_REQUEST.value(), errorDetail.status());
        assertEquals("Bad Request", errorDetail.error());
        assertEquals("/generic", errorDetail.path());
    }
}