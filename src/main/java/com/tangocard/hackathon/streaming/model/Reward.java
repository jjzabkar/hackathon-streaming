package com.tangocard.hackathon.streaming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    private Map<String, String> credentials;
    private BigDecimal amount;
    private String currencyCode;
}
