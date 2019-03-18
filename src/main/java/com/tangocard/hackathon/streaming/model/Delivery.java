package com.tangocard.hackathon.streaming.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    private String status;
    private UserInfo sender;
    private UserInfo recipient;
}
