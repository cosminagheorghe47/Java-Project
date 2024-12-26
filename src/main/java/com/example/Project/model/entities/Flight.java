package com.example.Project.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "flights")
@Getter
@Setter
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String code;

    @NotNull
    private Date departureDate;

    @NotNull
    private Date arrivalDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    private Airport arrivalAirport;

    @ManyToOne(optional = true)
    @JoinColumn(name = "pilot_id", referencedColumnName = "id")
    private Person pilot = new Person();

    private int distance;

    private int soldSeatsEconomy = 0;

    private int soldSeatsFirstClass = 0;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private Aircraft aircraft;
}
