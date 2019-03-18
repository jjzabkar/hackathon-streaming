package com.tangocard.hackathon.streaming.streamingwhatevs.web;

import com.tangocard.hackathon.streaming.model.LineItem;
import com.tangocard.hackathon.streaming.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class HelloController {
    AtomicLong orderIdCounter = new AtomicLong(1L);

    /**
     * <tt>
     * curl -H 'Accept:text/event-stream' localhost:8181/hello
     * </tt>
     *
     * @return
     */
    @GetMapping(value = "/hello", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LineItem> greetingPublisher() {
        Flux<LineItem> greetingFlux = Flux.<LineItem>generate(sink -> sink.next(
                LineItem.builder()
                        .lineItemId(orderIdCounter.incrementAndGet())
                        .orderId(orderIdCounter.incrementAndGet() % 5L)
                        .status(Status.PENDING)
                        .build()
        )).delayElements(Duration.ofSeconds(1));
        return greetingFlux;
    }
}
