package com.uca.parcialfinalncapas.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String accessToken;
    @Builder.Default
    private String  type = "Bearer";
    private String role;
}
