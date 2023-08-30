package com.genhot.shopper.service;

import com.genhot.shopper.service.dto.OrderStatDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderStatService {
    Flux<OrderStatDTO> getAllOrderStats(Pageable pageable);

    Mono<OrderStatDTO> getOrderStatById(Long id);
}
