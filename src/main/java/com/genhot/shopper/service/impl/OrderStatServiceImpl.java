package com.genhot.shopper.service.impl;

import com.genhot.shopper.domain.Order;
import com.genhot.shopper.repository.OrderRepository;
import com.genhot.shopper.service.OrderStatService;
import com.genhot.shopper.service.dto.OrderStatDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class OrderStatServiceImpl implements OrderStatService {

    private final Logger log = LoggerFactory.getLogger(OrderStatServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderStatServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Flux<OrderStatDTO> getAllOrderStats(Pageable pageable) {
        // 查询全部Order,过滤未派送的订单
        return orderRepository
            .findAllBy(pageable)
            .filter(order -> order.getDeliveries() != null)
            .map(this::convertToDTO)
            .filter(dto -> dto.getTotalCost() > 0);
    }

    @Override
    public Mono<OrderStatDTO> getOrderStatById(Long id) {
        return orderRepository.findById(id).map(this::convertToDTO);
    }

    private OrderStatDTO convertToDTO(Order order) {
        OrderStatDTO dto = new OrderStatDTO();
        dto.setId(order.getCustomerId());
        dto.setFirstName(order.getCustomer().getFirstName());
        dto.setLastName(order.getCustomer().getLastName());
        dto.setEmail(order.getCustomer().getEmail());
        dto.setTotalCost(order.getTotalPrice());
        if (dto.getTotalCost() > 10000) {
            dto.setType("VIP");
        } else {
            dto.setType("NORM");
        }
        return dto;
    }
}
