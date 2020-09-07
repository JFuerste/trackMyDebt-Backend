package com.jfuerste.trackmydebtbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private String sender;
    private String receiver;

    private Double amount;
    private LocalDateTime timestamp;
    private String reason;
}
