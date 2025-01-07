package com.example.Project.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @ManyToOne
    @JoinColumn(name = "person_data_id", referencedColumnName = "id")
    private Person personData;

    private String authToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN,
        CLIENT
    }
}
