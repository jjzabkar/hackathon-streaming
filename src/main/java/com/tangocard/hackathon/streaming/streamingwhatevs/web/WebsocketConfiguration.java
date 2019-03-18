package com.tangocard.hackathon.streaming.streamingwhatevs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangocard.hackathon.streaming.model.LineItem;
import com.tangocard.hackathon.streaming.streamingwhatevs.service.MockLineItemService;
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
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.util.HashMap;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
@Slf4j
@EnableWebFlux
public class WebsocketConfiguration {
    private final ObjectMapper om;
    private final MockLineItemService lineItemService;

    @Bean
    public WebSocketHandlerAdapter wsha() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public HandlerMapping hm() {
        SimpleUrlHandlerMapping result = new SimpleUrlHandlerMapping();
        HashMap<String, WebSocketHandler> map = new HashMap<>();
        map.put("/websocket", lineItemWebSocketHandler());
        result.setUrlMap(map);
        return result;
    }

    @Bean
    public WebSocketHandler lineItemWebSocketHandler() {
        log.info("lineItemWebSocketHandler");
        return session -> {
            log.info("handle lineItemWebSocket");
            Flux lineItemFlux = lineItemService.getRandomUpdatesFlux();
            log.info("generate Consumer<SynchronousSink<LineItem>>");
            Consumer<SynchronousSink<LineItem>> fluxGenerate = sink -> {
                lineItemFlux.next();
            };
            log.info("create Flux<WebSocketMessage> publisher");
            Flux<WebSocketMessage> publisher = Flux.generate(fluxGenerate)
                    .map(li -> {
                        log.info("map line item: orderId={}, lineItemId={}, status={}", li.getOrderId(), li.getLineItemId(), li.getStatus());
                        try {
                            return om.writeValueAsString(li);
                        } catch (JsonProcessingException e) {
                            log.info(e.getMessage(), e);
                        }
                        return "foo";
                    })
                    .map(x -> {
                        return session.textMessage(x);
                    })
//                        .delayElements(Duration.ofSeconds(1)) // NO DELAY! Already delayed in getRandomUpdatesFlux()
                    ;
            return session.send(publisher);
        };
    }
}
