package com.example.Project.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
public class Role {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

}
