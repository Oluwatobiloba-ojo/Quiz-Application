package org.example.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String password;
    private String email;
    private String confirmPassword;
    private String role;
}
