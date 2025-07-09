package com.uca.parcialfinalncapas.controller;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.response.GeneralResponse;
import com.uca.parcialfinalncapas.dto.response.LoginResponse;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.exceptions.token.TokenNotFoundException;
import com.uca.parcialfinalncapas.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return buildResponse("Inicio de sesión exitoso", HttpStatus.OK, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse> logout(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token == null) {
            throw new TokenNotFoundException("No se encontró el token en la solicitud");
        }
        String message = authService.logout(token);
        return buildResponse(message, HttpStatus.OK, null);
    }

    @GetMapping("/get-user")
    public ResponseEntity<GeneralResponse> getUser(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token == null) {
            throw new TokenNotFoundException("Token no encontrado");
        }
        User user = authService.getUserFromToken(token);
        return buildResponse("Usuario obtenido correctamente", HttpStatus.OK, user);
    }

    public ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        GeneralResponse response = GeneralResponse.builder()
                .uri(uri)
                .message(message)
                .status(status.value())
                .data(data)
                .build();
        return ResponseEntity.status(status).body(response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
