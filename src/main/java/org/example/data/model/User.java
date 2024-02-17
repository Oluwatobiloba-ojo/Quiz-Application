package org.example.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String dateOfBirth;
    private boolean isLocked = true;

    @Enumerated(EnumType.STRING)
    private Role userRole;

}
