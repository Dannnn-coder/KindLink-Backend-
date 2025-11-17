package com.kindlink.kindLink.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    @ManyToOne
    @JoinColumn(name = "campaignId", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "donorId")
    private User donor;

    private BigDecimal donationAmount;

    private LocalDateTime donationDate = LocalDateTime.now();

    private String paymentMethod;

    // getters and setters
}
