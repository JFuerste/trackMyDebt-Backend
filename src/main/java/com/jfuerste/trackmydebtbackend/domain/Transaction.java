package com.jfuerste.trackmydebtbackend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "id")
    private User receiver;

    private Double amount;
    private LocalDate timestamp;
}
