package com.tangocard.hackathon.streaming.streamingwhatevs.service;

import com.tangocard.hackathon.streaming.model.LineItem;
import com.tangocard.hackathon.streaming.model.Order;
import com.tangocard.hackathon.streaming.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MockLineItemService {
    TreeMap<Long, Order> orders = new TreeMap<Long, Order>();
    @Value("${mock.orders.count:4}")
    private Long MAX_ORDER_ID;
    @Value("${mock.order.line-items-per-order.count:4}")
    private Long MAX_LINE_ITEM_ID;
    @Value("${mock.line-item.update-frequency-seconds:1}") //update every X seconds
    private Integer LINE_ITEM_UPDATE_FREQUENCYT_SECONDS;

    @PostConstruct
    public void postConstruct() {
        for (long o = 0L; o < MAX_ORDER_ID; o++) {
            List<LineItem> list = new LinkedList();
            for (long li = 0L; li < MAX_LINE_ITEM_ID; li++) {
                list.add(LineItem.builder()
                        .amount(BigDecimal.valueOf(li))
                        .orderId(o)
                        .status(Status.PENDING)
                        .updateDate(LocalDateTime.now())
                        .build());
            }
            orders.put(o, Order.builder()
                    .lineItems(list)
                    .referenceOrderId(UUID.randomUUID().toString())
                    .orderId(o)
                    .status(Status.CREATED)
                    .updateDate(LocalDateTime.now())
                    .build());
        }
    }

    public List<LineItem> getAll() {
        return orders.entrySet().stream()
                .flatMap(x -> x.getValue().getLineItems().stream())
                .collect(Collectors.toList());
    }

    private final Random random = new Random();

    public Flux<LineItem> getRandomUpdatesFlux() {
        // create first flux; should return ALL orders/line items immediately
        Flux<LineItem> allFlux = Flux.fromIterable(getAll()).delayElements(Duration.ZERO);
        // create second flux; returns line item updates every X seconds
        Flux<LineItem> updatesFlux = Flux.<LineItem>generate(sink -> sink.next(
                getRandomLineItem()
        )).delayElements(Duration.ofSeconds(LINE_ITEM_UPDATE_FREQUENCYT_SECONDS));
        // https://www.baeldung.com/reactor-combine-streams
        return allFlux.concatWith(updatesFlux);
    }

    private LineItem getRandomLineItem() {
        final Long randomOrderId = (Math.abs(random.nextLong()) % MAX_LINE_ITEM_ID);
        final Long randomLineItemId = (Math.abs(random.nextLong()) % MAX_LINE_ITEM_ID);
        Order o = orders.get(randomOrderId);
        LineItem li = o.getLineItems().get(randomLineItemId.intValue());
        li.setStatus(Status.getNextStatus(li.getStatus()));
        li.setUpdateDate(LocalDateTime.now());
        log.info("update line item status: orderId={}, lineItemId={}, status={}", randomOrderId, randomLineItemId, li.getStatus());
        return li;
    }

}
