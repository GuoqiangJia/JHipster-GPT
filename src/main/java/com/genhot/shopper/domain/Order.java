package com.genhot.shopper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Order.
 */
@Table("jhi_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("date")
    private Instant date;

    @NotNull(message = "must not be null")
    @Column("status")
    private String status;

    @NotNull(message = "must not be null")
    @Column("total_price")
    private Double totalPrice;

    @Transient
    @JsonIgnoreProperties(value = { "categories", "orders" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "addresses", "orders" }, allowSetters = true)
    private Customer customer;

    @Transient
    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
    private Delivery deliveries;

    @Column("customer_id")
    private Long customerId;

    @Column("deliveries_id")
    private Long deliveriesId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Order date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public Order status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public Order totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setOrders(null));
        }
        if (products != null) {
            products.forEach(i -> i.setOrders(this));
        }
        this.products = products;
    }

    public Order products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Order addProducts(Product product) {
        this.products.add(product);
        product.setOrders(this);
        return this;
    }

    public Order removeProducts(Product product) {
        this.products.remove(product);
        product.setOrders(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer != null ? customer.getId() : null;
    }

    public Order customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Delivery getDeliveries() {
        return this.deliveries;
    }

    public void setDeliveries(Delivery delivery) {
        this.deliveries = delivery;
        this.deliveriesId = delivery != null ? delivery.getId() : null;
    }

    public Order deliveries(Delivery delivery) {
        this.setDeliveries(delivery);
        return this;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customer) {
        this.customerId = customer;
    }

    public Long getDeliveriesId() {
        return this.deliveriesId;
    }

    public void setDeliveriesId(Long delivery) {
        this.deliveriesId = delivery;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
