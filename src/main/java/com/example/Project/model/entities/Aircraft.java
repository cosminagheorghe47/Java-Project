package com.example.Project.model.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "aircrafts")
@Getter
@Setter
public class Aircraft implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private int nrOfSeatsEconomy;

    @NotNull
    private int nrOfSeatsFirstClass;

    private int maxSpeed;

    private int weight;

    private int wingSpan;

    public Aircraft() {}

    public Aircraft(int id, String name, int nrOfSeatsEconomy, int nrOfSeatsFirstClass, int maxSpeed, int weight, int wingSpan) {
        this.id = id;
        this.name = name;
        this.nrOfSeatsEconomy = nrOfSeatsEconomy;
        this.nrOfSeatsFirstClass = nrOfSeatsFirstClass;
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.wingSpan = wingSpan;
    }
}
