package com.genhot.shopper.repository;

import com.genhot.shopper.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long>, OrderRepositoryInternal {
    Flux<Order> findAllBy(Pageable pageable);

    @Query("SELECT * FROM jhi_order entity WHERE entity.customer_id = :id")
    Flux<Order> findByCustomer(Long id);

    @Query("SELECT * FROM jhi_order entity WHERE entity.customer_id IS NULL")
    Flux<Order> findAllWhereCustomerIsNull();

    @Query("SELECT * FROM jhi_order entity WHERE entity.deliveries_id = :id")
    Flux<Order> findByDeliveries(Long id);

    @Query("SELECT * FROM jhi_order entity WHERE entity.deliveries_id IS NULL")
    Flux<Order> findAllWhereDeliveriesIsNull();

    @Override
    <S extends Order> Mono<S> save(S entity);

    @Override
    Flux<Order> findAll();

    @Override
    Mono<Order> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface OrderRepositoryInternal {
    <S extends Order> Mono<S> save(S entity);

    Flux<Order> findAllBy(Pageable pageable);

    Flux<Order> findAll();

    Mono<Order> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Order> findAllBy(Pageable pageable, Criteria criteria);
}
