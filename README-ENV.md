# 准备环境

## JDK 环境准备

从以下地址下载 JDK-17 且解压，完成后配置本机的 Java 开发环境：
https://download.java.net/openjdk/jdk17/ri/openjdk-17+35_windows-x64_bin.zip
完成后用以下命令确认 JDK 环境以及正确就绪：

```
java -version
```

## 准备 NodeJS 环境

从以下地址中下载并且安装 Node.js-v18，并且配置环境变量：
https://nodejs.org/en/download
完成后用以下命令确认 NodeJS 正确就绪：

```
node -v
npm -v
```

## 准备 git 环境

参照以下教程完成 git 的安装：
https://git-scm.com/download/win
用以下命令检查 git 环境

```
git --version
```

## 准备生产环境数据库（postgresql）

从以下链接下载数据库且安装：

https://www.enterprisedb.com/postgresql-tutorial-resources-training-1?uuid=c70fc67b-ca1f-4dc2-b73b-ccb7367fb6b8&campaignId=Product_Trial_PostgreSQL_15
安装完成后，可以在 pgAdmin 客户端测试数据库是否正确安装

# 安装 Jhipster

在 Terminal 中运行以下命令：

```
npm install -g generator-jhipster
```

<!-- ```
npm install "https://github.com/jhipster/generator-jhipster#main" --save
``` -->

用以下命令检查 jhipster 是否安装成功：

```
jhipster --version
```

# 准备 jhipster 需要的 jdl 文件

## 在 Claude 中，用如下 prompt 生成 jdl 文件的内容

```
你是一个高级Java工程师，精通Jhipster。以下是软件开发的需求分析，请根据需求分析生成jhipster的jdl文件内容，并且自动生成实体的必要属性：

我在开发一款电子商务网站的<后端管理软件>
1.用户在登陆后端管理软件后，可以管理<商品>，<客户>，<地址>，<订单>，<快递>
2.<商品>有一个属性是<类别>，一个<商品>可包含多个<类别>，一个<类别>也可对应多个<商品>
3.<类别>其中的一个属性是<类别状态>，是一个枚举，包含AVAILABLE, RESTRICTED, DISABLED这几个状态
4.一个<客户>可以有多个<地址>
5.一个<客户>可以有多个<订单>
6.一个<订单>可以包含多个<商品>
7.一个<快递>可以包含多个<订单>

以下是<后端管理软件>其他的系统要求：
1.包名是com.genhot.shopper
2.生产环境数据库使用的是postgresql
3.权限认证使用jwt
4.客户端开发使用react
5.服务端口号是8081
6.需要reactive是true
7.默认语言是英文，可切换的语言包含中文和英文
8.全部实体使用dto传输数据

以下是一个jdl文件内容的格式参考，请注意它不是一个单独完整的json文件，例如application和entity在结构上是平级的：
application {
  config {
    //config info
  }
  entities Entity1, Entity2
}
entity Entity1 {
  //entity info
}
entity Entity2 {
  //entity info
}
relationship ManyToMany {
  //relationship info
}
paginate all with pagination


所以，根据以上需求描述、系统要求和格式参考，对应的jhipster的jdl文件的内容是：
```

## 保存生成的内容到 shopper.jdl 文件

在你的工作目录下创建一个 shopper 的目录，并且创建 shopper.jdl 文件，然后将 claude 生成的 jh 文件内容保存到该目录下的 shopper.jdl 文件中

# 创建工程并跑起来

## 创建工程

用如下命令生成 shopper 工程，过程可能会花费数分钟时间

```
jhipster jdl shopper.jdl
```

## 开发环境跑起来

在自动创建好的工程目录下，分别运行如下命令

```
run 'mvnw' to start the backend application
run 'npm start' to start the frontend application
```

在应用启动成功后，通过 http://localhost:9000 做测试

# 开发一个新功能

基于我们现在已有的实体，开发一个订单统计功能做为示例，注意订单统计功能不需要生成新的实体

## 生成前端

```
jhipster entity OrderStat --skip-server
```

## 创建一个 DTO

OrderStatDTO.java

```
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
```

## AI 编写 Service 和 Controller 的类和方法

```
## Role: 高级Java工程师，为人类完成程序编写任务

## Profile:
- author: codeman
- Jike ID:
- version: 0.1
- language: 中文
- description: 高级Java工程师，精通Java、Spring、JHipster

## Requirements：
# 根据以下业务逻辑，编写分页查看所有客户统计结果的API。和单个客户统计结果查看功能的API
- 统计所有客户的消费总量
- 只返回有总消费量大于0的统计结果
- 不要统计未派送的订单
- 如果消费总量大于10000，设置该统计结果的类型为VIP，否则设置为NORM

## Goals:
根据提供的代码上下文，逐步完成编写任务

## Constraints:
- 在前置条件不满足时，提示人类提供更多的代码上下文
- 直接输出最终结果代码，不要输出实现过程
- 输出结果不要包含待实现的方法，如果有，请自行实现
- 在调用方法前，先检查该方法是否存在，如果不存在，完成它
- 如果人类提供示例代码，请严格参考示例代码
- 请严格遵守Spring MVC的三层调用架构

## Workflow:
1. 理解提供的Domain及其关系，作为前置条件
2. 理解可能会使用的接口、类或方法，必要时自行编写
3. 理解提供的示例代码，如果没有示例，忽略
4. 理解现有代码，如果没有，完成必要的接口与类设计
5. 根据<Requirements>中的业务逻辑，参考<示例代码>中相似的功能，完成所有代码的编写
6. 检查生成的代码，修复问题，输出最终代码


## Domain及其关系：
"""
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
  Product{categories(name)} to Category{products}
}

relationship OneToMany {
  Customer{addresses} to Address{customer}
  Customer{orders} to Order{customer}
  Order{products} to Product{orders}
  Delivery{orders} to Order{deliveries}
}
"""


## 现有代码：
# CustomerRepository.java：
"""
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
    <S extends Customer> Mono<S> save(S entity);

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
"""

# OrderRepository.java：
"""
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
"""

# OrderStatDTO.java:
"""
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
"""


## 示例代码：
# OrderService.java:
"""
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
"""

# OrderServiceImpl.java：
"""
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
"""

# OrderResource.java:
"""
package com.genhot.shopper.web.rest;

import com.genhot.shopper.repository.OrderRepository;
import com.genhot.shopper.service.OrderService;
import com.genhot.shopper.service.dto.OrderDTO;
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

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    private static final String ENTITY_NAME = "order";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    public OrderResource(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    /**
     * {@code POST  /orders} : Create a new order.
     *
     * @param orderDTO the orderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDTO, or with status {@code 400 (Bad Request)} if the order has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orders")
    public Mono<ResponseEntity<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return orderService
            .save(orderDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/orders/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /orders/:id} : Updates an existing order.
     *
     * @param id the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orders/{id}")
    public Mono<ResponseEntity<OrderDTO>> updateOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Order : {}, {}", id, orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return orderRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return orderService
                    .update(orderDTO)
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
     * {@code PATCH  /orders/:id} : Partial updates given fields of an existing order, field will ignore if it is null
     *
     * @param id the id of the orderDTO to save.
     * @param orderDTO the orderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDTO,
     * or with status {@code 400 (Bad Request)} if the orderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<OrderDTO>> partialUpdateOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderDTO orderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Order partially : {}, {}", id, orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return orderRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<OrderDTO> result = orderService.partialUpdate(orderDTO);

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
     * {@code GET  /orders} : get all the orders.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<OrderDTO>>> getAllOrders(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Orders");
        return orderService
            .countAll()
            .zipWith(orderService.findAll(pageable).collectList())
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
     * {@code GET  /orders/:id} : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public Mono<ResponseEntity<OrderDTO>> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        Mono<OrderDTO> orderDTO = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders/{id}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        return orderService
            .delete(id)
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
"""

```

## 后端生成 Service 和 Controller

```
jhipster spring-service OrderStat
jhipster spring-controller OrderStat
```

# 其他

## 前端怎么删除一个 entity 的功能

在如下文件或目录中分别删除相应 entity 关联的的所有代码

- .yo-rc.json
- .jhipster
- /src/main/webapp/app/entities
- /src/main/webapp/app/entities/reducers.ts
- /src/main/webapp/app/entities/routes.tsx
- /src/main/webapp/app/entities/menu.tsx
- /src/main/webapp/app/entities/menu.tsx
- /src/main/webapp/app/shared/model
- /src/main/webapp/i18n/en
- /src/main/webapp/i18n/zh-cn

## 后端怎么删除一个 entity 的功能

在如下文件或目录中分别删除相应 entity 关联的的所有代码

- src\test\java\com\genhot\shopper\web\rest
- src\main\java\com\genhot\shopper\service
- src\main\java\com\genhot\shopper\web

# Continious Integration

```
jhipster ci-cd
```
