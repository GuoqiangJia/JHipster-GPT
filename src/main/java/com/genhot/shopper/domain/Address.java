package com.genhot.shopper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Address.
 */
@Table("address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("address_1")
    private String address1;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("zipcode")
    private String zipcode;

    @Column("country")
    private String country;

    @Transient
    @JsonIgnoreProperties(value = { "addresses", "orders" }, allowSetters = true)
    private Customer customer;

    @Column("customer_id")
    private Long customerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Address address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return this.city;
    }

    public Address city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Address state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public Address zipcode(String zipcode) {
        this.setZipcode(zipcode);
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return this.country;
    }

    public Address country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer != null ? customer.getId() : null;
    }

    public Address customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customer) {
        this.customerId = customer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", address1='" + getAddress1() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
