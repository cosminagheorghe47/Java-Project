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
@Table(name = "flights")
@Getter
@Setter
public class Flight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String code;

    @NotNull
    private Date departure_date;

    @NotNull
    private Date arrival_date;

    @NotNull
    private Airport departure_airport_id;

    @NotNull
    private Airport arrival_airport_id;

    @NotNull
    private Person pilot;

    private int distance;

    private int sold_seats_economy=0;

    private int sold_seats_first_class=0;

    private Aircraft aircraft;

    private List<Airport> stops=new ArrayList<>();

}
