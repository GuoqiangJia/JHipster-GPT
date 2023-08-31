@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

echo ## Role:
set "role=高级Java工程师,精通Java、Spring、JHipster,为人类完成程序编写任务"

echo ## Profile:
echo - author: codeman
echo - Jike ID:
echo - version: 0.1
echo - language: 中文
echo - description: !role!

echo ## Requirements:
set "requirements="

echo ## Goals:
echo 根据提供的代码上下文,逐步完成编写任务

echo ## Constraints:
echo - 在前置条件不满足时,提示人类提供更多的代码上下文
echo - 直接输出最终结果代码,不要输出实现过程
echo - 输出结果不要包含待实现的方法,如果有,请自行实现
echo - 在调用方法前,先检查该方法是否存在,如果不存在,完成它
echo - 如果人类提供示例代码,请严格参考示例代码
echo - 请严格遵守Spring MVC的三层调用架构

echo ## Workflow:
echo 1. 理解提供的Domain及其关系,作为前置条件
echo 2. 理解可能会使用的接口、类或方法,必要时自行编写
echo 3. 理解提供的示例代码,如果没有示例,忽略
echo 4. 理解现有代码,如果没有,完成必要的接口与类设计
echo 5. 根据^<Requirements^>中的业务逻辑,参考^<示例代码^>中相似的功能,完成所有代码的编写
echo 6. 检查生成的代码,修复问题,输出最终代码

echo.
echo ## Domain及其关系:
echo !<!-- Domain -->!

echo """
entity Product {
  name String required
  description String
  price BigDecimal required
  inventory Integer required
}

entity Category {
  name String required
  description String
  status CategoryStatus required
}

enum CategoryStatus {
  AVAILABLE
  RESTRICTED
  DISABLED
}

entity Customer {
  firstName String required
  lastName String required
  email String required
  phoneNumber String
}

entity Address {
  address1 String
  city String
  state String
  zipcode String
  country String
}

entity Order {
  date Instant required
  status String required
  totalPrice BigDecimal required
}

entity Delivery {
  trackingNumber String required
  carrier String required
  shippingDate Instant required
}

relationship ManyToMany {
  Product{categories^(name^)} to Category{products}
}

relationship OneToMany {
  Customer{addresses} to Address{customer}
  Customer{orders} to Order{customer}
  Order{products} to Product{orders}
  Delivery{orders} to Order{deliveries}
}

"""

echo !<!-- Domain end -->!

echo.
echo ## 现有代码:
echo !<!-- Code start -->!

REM CustomerRepository.java:

echo """
package com.genhot.shopper.repository;

import com.genhot.shopper.domain.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long>, CustomerRepositoryInternal {
    Flux<Customer> findAllBy(Pageable pageable);

    @Override
    ^<S extends Customer^> Mono<S> save(S entity);

    @Override
    Flux<Customer> findAll();

    @Override
    Mono<Customer> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CustomerRepositoryInternal {
    <S extends Customer> Mono<S> save(S entity);

    Flux<Customer> findAllBy(Pageable pageable);

    Flux<Customer> findAll();

    Mono<Customer> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Customer> findAllBy(Pageable pageable, Criteria criteria);
}
echo """

REM OrderRepository.java:
echo """

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
    ^<S extends Order^> Mono<S> save(S entity);

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
echo """

REM OrderStatDTO.java:
echo """

package com.genhot.shopper.service.dto;

import java.io.Serializable;

public class OrderStatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private String email;

    private Double totalCost;

    private String type;

    public OrderStatDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
echo """

echo !<!-- Code end -->!

echo.
echo ## 示例代码:
echo !<!-- Example start -->!

REM OrderService.java:

echo """
package com.genhot.shopper.service;

import com.genhot.shopper.service.dto.OrderDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.genhot.shopper.domain.Order}.
 */
public interface OrderService {
    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<OrderDTO> save(OrderDTO orderDTO);

    /**
     * Updates a order.
     *
     * @param orderDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<OrderDTO> update(OrderDTO orderDTO);

    /**
     * Partially updates a order.
     *
     * @param orderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<OrderDTO> partialUpdate(OrderDTO orderDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<OrderDTO> findAll(Pageable pageable);

    /**
     * Returns the number of orders available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<OrderDTO> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
echo """

REM OrderServiceImpl.java:

echo """
package com.genhot.shopper.service.impl;

import com.genhot.shopper.domain.Order;
import com.genhot.shopper.repository.OrderRepository;
import com.genhot.shopper.service.OrderService;
import com.genhot.shopper.service.dto.OrderDTO;
import com.genhot.shopper.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Mono<OrderDTO> save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        return orderRepository.save(orderMapper.toEntity(orderDTO)).map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDTO> update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        return orderRepository.save(orderMapper.toEntity(orderDTO)).map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);
                return existingOrder;
            })
            .flatMap(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAllBy(pageable).map(orderMapper::toDto);
    }

    public Mono<Long> countAll() {
        return orderRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        return orderRepository.deleteById(id);
    }
}
echo """

REM OrderResource.java:

echo """
package com.genhot.shopper.web.rest;

import com.genhot.shopper.repository.OrderRepository;
import com.genhot.shopper.service.OrderService;
import com.genhot.shopper.service.dto.OrderDTO;
import com.genhot.shopper.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.genhot.shopper.domain.Order}.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderResource(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    // ...
}
echo """

echo !<!-- Example end -->!
