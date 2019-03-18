package com.tangocard.hackathon.streaming.streamingwhatevs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangocard.hackathon.streaming.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
@Slf4j
@EnableWebFlux
public class WebsocketConfiguration {
    private final ObjectMapper om;

    @Bean
    WebSocketHandlerAdapter wsha() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    HandlerMapping hm() {
        SimpleUrlHandlerMapping result = new SimpleUrlHandlerMapping();
        HashMap<String, WebSocketHandler> map = new HashMap<>();
        map.put("/hello2/", helloWebSocketHandler());
        result.setUrlMap(map);
        return result;
    }

    private AtomicLong orderIdGenerator = new AtomicLong(0L);

    @Bean
    WebSocketHandler helloWebSocketHandler() {
        log.info("helloWebSocketHandler");
        return new WebSocketHandler() {
            @Override
            public Mono<Void> handle(WebSocketSession session) {
                log.info("handle");
                Consumer<SynchronousSink<Order>> fluxGenerate = sink -> {
                    sink.next(Order.builder()
                            .lineItems(Collections.emptyList())
                            .orderId(orderIdGenerator.incrementAndGet())
                            .referenceOrderId(UUID.randomUUID().toString())
                            .build());
                };
                Flux<WebSocketMessage> publisher = Flux.generate(fluxGenerate)
                        .map(x -> {
                            try {
                                return om.writeValueAsString(x);
                            } catch (JsonProcessingException e) {
                                log.info(e.getMessage(), e);
                            }
                            return "foo";
                        })
                        .map(x -> {
                            return session.textMessage(x);
                        })
                        .delayElements(Duration.ofSeconds(1));
//                Flux<WebSocketMessage> publisher = publisherService.myEventPublisher(session);
                return session.send(publisher);
            }
        };
    }
}
