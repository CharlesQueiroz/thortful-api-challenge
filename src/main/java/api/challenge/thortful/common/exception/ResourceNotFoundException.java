package api.challenge.thortful.common.exception;

/**
 * Exception thrown when a requested resource is not found.
 *
 * <p>This exception is used to signal that a resource, such as a character or film, could not be found.</p>
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
