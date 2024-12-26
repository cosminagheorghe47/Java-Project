package com.example.Project.model.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "airports")
@Getter
@Setter
public class Airport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String cityName;

    @NotNull
    private int nrOfTerminals;

    public Airport() {}
    @JsonCreator
    public Airport(@JsonProperty("id") int id) {
        this.id = id;
    }
}
