package api.challenge.thortful.common.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorDetail(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
