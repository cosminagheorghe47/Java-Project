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
@Table(name = "bookings")
@Getter
@Setter
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;

    @NotNull
    private int seat;

    @NotNull
    private int rowSeat;

    @NotNull
    private int nrOfBaggages;

    @NotNull
    private int basePrice;

    private int finalPrice;
}
