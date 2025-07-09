package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.entities.User;

public interface TokenService {
    void saveToken(User user, String jwt);
    void revokeToken(String jwt);
    boolean isTokenValid(String jwt);
}
