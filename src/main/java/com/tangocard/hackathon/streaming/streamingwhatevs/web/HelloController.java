package com.tangocard.hackathon.streaming.streamingwhatevs.web;

import com.tangocard.hackathon.streaming.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@RestController
@Slf4j
public class HelloController {
    @GetMapping(value = "/hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Order> greetingPublisher() {
        Flux<Order> greetingFlux = Flux.<Order>generate(sink -> sink.next(
                Order.builder()
                        .referenceOrderId(UUID.randomUUID().toString())
                        .build()
        )).delayElements(Duration.ofSeconds(1));
        return greetingFlux;
    }
}
