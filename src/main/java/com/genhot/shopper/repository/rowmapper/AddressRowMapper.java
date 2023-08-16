package com.genhot.shopper.repository.rowmapper;

import com.genhot.shopper.domain.Address;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Address}, with proper type conversions.
 */
@Service
public class AddressRowMapper implements BiFunction<Row, String, Address> {

    private final ColumnConverter converter;

    public AddressRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Address} stored in the database.
     */
    @Override
    public Address apply(Row row, String prefix) {
        Address entity = new Address();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setAddress1(converter.fromRow(row, prefix + "_address_1", String.class));
        entity.setCity(converter.fromRow(row, prefix + "_city", String.class));
        entity.setState(converter.fromRow(row, prefix + "_state", String.class));
        entity.setZipcode(converter.fromRow(row, prefix + "_zipcode", String.class));
        entity.setCountry(converter.fromRow(row, prefix + "_country", String.class));
        entity.setCustomerId(converter.fromRow(row, prefix + "_customer_id", Long.class));
        return entity;
    }
}
