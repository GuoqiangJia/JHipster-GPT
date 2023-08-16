package com.genhot.shopper.repository;

import com.genhot.shopper.domain.Delivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Delivery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends ReactiveCrudRepository<Delivery, Long>, DeliveryRepositoryInternal {
    Flux<Delivery> findAllBy(Pageable pageable);

    @Override
    <S extends Delivery> Mono<S> save(S entity);

    @Override
    Flux<Delivery> findAll();

    @Override
    Mono<Delivery> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DeliveryRepositoryInternal {
    <S extends Delivery> Mono<S> save(S entity);

    Flux<Delivery> findAllBy(Pageable pageable);

    Flux<Delivery> findAll();

    Mono<Delivery> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Delivery> findAllBy(Pageable pageable, Criteria criteria);
}
