package niffler.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.ALWAYS)
public record UserJson(
        UUID id,
        String username,
        String firstname,
        String surname,
        String currency,
        String photo) {
}