package com.genhot.shopper.service.impl;

import com.genhot.shopper.domain.Delivery;
import com.genhot.shopper.repository.DeliveryRepository;
import com.genhot.shopper.service.DeliveryService;
import com.genhot.shopper.service.dto.DeliveryDTO;
import com.genhot.shopper.service.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Delivery}.
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public Mono<DeliveryDTO> save(DeliveryDTO deliveryDTO) {
        log.debug("Request to save Delivery : {}", deliveryDTO);
        return deliveryRepository.save(deliveryMapper.toEntity(deliveryDTO)).map(deliveryMapper::toDto);
    }

    @Override
    public Mono<DeliveryDTO> update(DeliveryDTO deliveryDTO) {
        log.debug("Request to update Delivery : {}", deliveryDTO);
        return deliveryRepository.save(deliveryMapper.toEntity(deliveryDTO)).map(deliveryMapper::toDto);
    }

    @Override
    public Mono<DeliveryDTO> partialUpdate(DeliveryDTO deliveryDTO) {
        log.debug("Request to partially update Delivery : {}", deliveryDTO);

        return deliveryRepository
            .findById(deliveryDTO.getId())
            .map(existingDelivery -> {
                deliveryMapper.partialUpdate(existingDelivery, deliveryDTO);

                return existingDelivery;
            })
            .flatMap(deliveryRepository::save)
            .map(deliveryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DeliveryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAllBy(pageable).map(deliveryMapper::toDto);
    }

    public Mono<Long> countAll() {
        return deliveryRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DeliveryDTO> findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        return deliveryRepository.findById(id).map(deliveryMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        return deliveryRepository.deleteById(id);
    }
}
