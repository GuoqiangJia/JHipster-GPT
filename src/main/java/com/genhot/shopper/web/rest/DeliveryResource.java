package com.genhot.shopper.web.rest;

import com.genhot.shopper.domain.Delivery;
import com.genhot.shopper.repository.DeliveryRepository;
import com.genhot.shopper.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.genhot.shopper.domain.Delivery}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeliveryResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryResource.class);

    private static final String ENTITY_NAME = "delivery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeliveryRepository deliveryRepository;

    public DeliveryResource(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * {@code POST  /deliveries} : Create a new delivery.
     *
     * @param delivery the delivery to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delivery, or with status {@code 400 (Bad Request)} if the delivery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deliveries")
    public Mono<ResponseEntity<Delivery>> createDelivery(@Valid @RequestBody Delivery delivery) throws URISyntaxException {
        log.debug("REST request to save Delivery : {}", delivery);
        if (delivery.getId() != null) {
            throw new BadRequestAlertException("A new delivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return deliveryRepository
            .save(delivery)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/deliveries/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /deliveries/:id} : Updates an existing delivery.
     *
     * @param id the id of the delivery to save.
     * @param delivery the delivery to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delivery,
     * or with status {@code 400 (Bad Request)} if the delivery is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delivery couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deliveries/{id}")
    public Mono<ResponseEntity<Delivery>> updateDelivery(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Delivery delivery
    ) throws URISyntaxException {
        log.debug("REST request to update Delivery : {}, {}", id, delivery);
        if (delivery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delivery.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return deliveryRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return deliveryRepository
                    .save(delivery)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /deliveries/:id} : Partial updates given fields of an existing delivery, field will ignore if it is null
     *
     * @param id the id of the delivery to save.
     * @param delivery the delivery to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delivery,
     * or with status {@code 400 (Bad Request)} if the delivery is not valid,
     * or with status {@code 404 (Not Found)} if the delivery is not found,
     * or with status {@code 500 (Internal Server Error)} if the delivery couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deliveries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<Delivery>> partialUpdateDelivery(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Delivery delivery
    ) throws URISyntaxException {
        log.debug("REST request to partial update Delivery partially : {}, {}", id, delivery);
        if (delivery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, delivery.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return deliveryRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<Delivery> result = deliveryRepository
                    .findById(delivery.getId())
                    .map(existingDelivery -> {
                        if (delivery.getTrackingNumber() != null) {
                            existingDelivery.setTrackingNumber(delivery.getTrackingNumber());
                        }
                        if (delivery.getCarrier() != null) {
                            existingDelivery.setCarrier(delivery.getCarrier());
                        }
                        if (delivery.getShippingDate() != null) {
                            existingDelivery.setShippingDate(delivery.getShippingDate());
                        }

                        return existingDelivery;
                    })
                    .flatMap(deliveryRepository::save);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /deliveries} : get all the deliveries.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deliveries in body.
     */
    @GetMapping(value = "/deliveries", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Delivery>>> getAllDeliveries(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Deliveries");
        return deliveryRepository
            .count()
            .zipWith(deliveryRepository.findAllBy(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /deliveries/:id} : get the "id" delivery.
     *
     * @param id the id of the delivery to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delivery, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deliveries/{id}")
    public Mono<ResponseEntity<Delivery>> getDelivery(@PathVariable Long id) {
        log.debug("REST request to get Delivery : {}", id);
        Mono<Delivery> delivery = deliveryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(delivery);
    }

    /**
     * {@code DELETE  /deliveries/:id} : delete the "id" delivery.
     *
     * @param id the id of the delivery to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deliveries/{id}")
    public Mono<ResponseEntity<Void>> deleteDelivery(@PathVariable Long id) {
        log.debug("REST request to delete Delivery : {}", id);
        return deliveryRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
