package org.example.services;

import org.example.dto.request.LoginRequest;
import org.example.dto.request.RegisterRequest;

public interface UserService {


    void register(RegisterRequest registerRequest);

    void login(LoginRequest loginRequest);
}
