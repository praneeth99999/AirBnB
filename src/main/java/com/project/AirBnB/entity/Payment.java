package com.project.AirBnB.entity;

import com.project.AirBnB.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false,precision = 8,scale = 2)
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY)
    private Booking booking;


}
