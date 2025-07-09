package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.response.LoginResponse;
import com.uca.parcialfinalncapas.entities.User;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    String logout(String jwt);
    User getUserFromToken(String jwt);
}
