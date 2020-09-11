package com.jfuerste.trackmydebtbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private String sender;
    private String receiver;

    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;

    @NotNull
    private Double amount;
    @NotNull
    private LocalDateTime timestamp;
    private String shortReason;
    private String longReason;
}
