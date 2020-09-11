package com.jfuerste.trackmydebtbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @NotNull
    @JoinColumn(name = "sender", referencedColumnName = "id")
    private User sender;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @NotNull
    @JoinColumn(name = "receiver", referencedColumnName = "id")
    private User receiver;

    @NotNull
    private Double amount;

    @NotNull
    @Past
    private LocalDateTime timestamp;
    private String shortReason;
    private String longReason;
}
