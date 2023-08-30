package com.genhot.shopper.service.mapper;

import com.genhot.shopper.domain.Delivery;
import com.genhot.shopper.service.dto.DeliveryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Delivery} and its DTO {@link DeliveryDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeliveryMapper extends EntityMapper<DeliveryDTO, Delivery> {}
