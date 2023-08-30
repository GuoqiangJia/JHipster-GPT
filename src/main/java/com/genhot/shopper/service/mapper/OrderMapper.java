package com.genhot.shopper.service.mapper;

import com.genhot.shopper.domain.Customer;
import com.genhot.shopper.domain.Delivery;
import com.genhot.shopper.domain.Order;
import com.genhot.shopper.service.dto.CustomerDTO;
import com.genhot.shopper.service.dto.DeliveryDTO;
import com.genhot.shopper.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "deliveries", source = "deliveries", qualifiedByName = "deliveryId")
    OrderDTO toDto(Order s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("deliveryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeliveryDTO toDtoDeliveryId(Delivery delivery);
}
