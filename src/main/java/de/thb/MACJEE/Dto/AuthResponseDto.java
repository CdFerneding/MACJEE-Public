package de.thb.MACJEE.Dto;

import lombok.Data;

// dto's (Data Transfer Object) use is to solely transfer information and not to implement logik
// as difference to service files
@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
