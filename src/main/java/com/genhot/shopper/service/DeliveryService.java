package com.genhot.shopper.service;

import com.genhot.shopper.service.dto.DeliveryDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.genhot.shopper.domain.Delivery}.
 */
public interface DeliveryService {
    /**
     * Save a delivery.
     *
     * @param deliveryDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DeliveryDTO> save(DeliveryDTO deliveryDTO);

    /**
     * Updates a delivery.
     *
     * @param deliveryDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<DeliveryDTO> update(DeliveryDTO deliveryDTO);

    /**
     * Partially updates a delivery.
     *
     * @param deliveryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<DeliveryDTO> partialUpdate(DeliveryDTO deliveryDTO);

    /**
     * Get all the deliveries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<DeliveryDTO> findAll(Pageable pageable);

    /**
     * Returns the number of deliveries available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" delivery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DeliveryDTO> findOne(Long id);

    /**
     * Delete the "id" delivery.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
