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
 * A Delivery.
 */
@Table("delivery")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("tracking_number")
    private String trackingNumber;

    @NotNull(message = "must not be null")
    @Column("carrier")
    private String carrier;

    @NotNull(message = "must not be null")
    @Column("shipping_date")
    private Instant shippingDate;

    @Transient
    @JsonIgnoreProperties(value = { "products", "customer", "delivery" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Delivery id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public Delivery trackingNumber(String trackingNumber) {
        this.setTrackingNumber(trackingNumber);
        return this;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCarrier() {
        return this.carrier;
    }

    public Delivery carrier(String carrier) {
        this.setCarrier(carrier);
        return this;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Instant getShippingDate() {
        return this.shippingDate;
    }

    public Delivery shippingDate(Instant shippingDate) {
        this.setShippingDate(shippingDate);
        return this;
    }

    public void setShippingDate(Instant shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setDelivery(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setDelivery(this));
        }
        this.orders = orders;
    }

    public Delivery orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Delivery addOrder(Order order) {
        this.orders.add(order);
        order.setDelivery(this);
        return this;
    }

    public Delivery removeOrder(Order order) {
        this.orders.remove(order);
        order.setDelivery(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Delivery)) {
            return false;
        }
        return id != null && id.equals(((Delivery) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delivery{" +
            "id=" + getId() +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", shippingDate='" + getShippingDate() + "'" +
            "}";
    }
}
