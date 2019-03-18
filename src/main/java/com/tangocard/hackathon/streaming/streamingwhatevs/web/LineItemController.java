package com.tangocard.hackathon.streaming.streamingwhatevs.web;

import com.tangocard.hackathon.streaming.model.LineItem;
import com.tangocard.hackathon.streaming.streamingwhatevs.service.MockLineItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class LineItemController {
    private final MockLineItemService lineItemService;

    /**
     * <tt>
     * curl -H 'Accept:text/event-stream' localhost:8181/lineitems
     * </tt>
     *
     * @return
     */
    @GetMapping(value = "/lineitems", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LineItem> lineItemFlux() {
        return lineItemService.getRandomUpdatesFlux();
    }

}
