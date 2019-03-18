package com.tangocard.hackathon.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private List<LineItem> lineItems;
    private String referenceOrderId;
    private Long orderId;
    private Status status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime updateDate;
}
