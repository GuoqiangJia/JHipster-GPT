package com.genhot.shopper.web.rest;

import com.genhot.shopper.service.OrderStatService;
import com.genhot.shopper.service.dto.OrderStatDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * OrderStatResource controller
 */
@RestController
@RequestMapping("/api")
public class OrderStatResource {

    private final Logger log = LoggerFactory.getLogger(OrderStatResource.class);

    private final OrderStatService orderStatService;

    public OrderStatResource(OrderStatService orderStatService) {
        this.orderStatService = orderStatService;
    }

    @GetMapping("/order-stats")
    public Flux<OrderStatDTO> getAllOrderStats(Pageable pageable) {
        return orderStatService.getAllOrderStats(pageable);
    }

    @GetMapping("/order-stats/{id}")
    public Mono<OrderStatDTO> getOrderStat(@PathVariable Long id) {
        return orderStatService.getOrderStatById(id);
    }
}
