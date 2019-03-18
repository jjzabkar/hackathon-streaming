package com.tangocard.hackathon.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    public List<LineItem> lineItems;
    private String referenceOrderId;
    private Long orderId;
}
