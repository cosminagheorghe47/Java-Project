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
@Table(name = "coupons")
@Getter
@Setter
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private User user;

    @NotNull
    private int discountPercentage;

    @NotNull
    private String expirationDate;

    @NotNull
    private Flight flightUsedON;
    public Coupon() {}

    public Coupon(Integer id, User user, int discountPercentage, String expirationDate, Flight flightUsedON) {
        this.id = id;
        this.user = user;
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
        this.flightUsedON = null;
    }
}
