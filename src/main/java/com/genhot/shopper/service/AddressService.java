package com.genhot.shopper.service;

import com.genhot.shopper.service.dto.AddressDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.genhot.shopper.domain.Address}.
 */
public interface AddressService {
    /**
     * Save a address.
     *
     * @param addressDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<AddressDTO> save(AddressDTO addressDTO);

    /**
     * Updates a address.
     *
     * @param addressDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<AddressDTO> update(AddressDTO addressDTO);

    /**
     * Partially updates a address.
     *
     * @param addressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<AddressDTO> partialUpdate(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<AddressDTO> findAll(Pageable pageable);

    /**
     * Returns the number of addresses available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<AddressDTO> findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
