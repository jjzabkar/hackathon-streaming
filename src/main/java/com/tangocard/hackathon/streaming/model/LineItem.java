package com.tangocard.hackathon.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private List<Delivery> deliveries;
    private Reward reward;
    private Long orderId;
    private Status status;
    private BigDecimal amount;
    private Long lineItemId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime updateDate;
}
