package org.example.dto.response;

import lombok.Data;
import org.example.data.model.Role;

@Data
public class LoginResponse {
    private String message;
    private Role role;
}
