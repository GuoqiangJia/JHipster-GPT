package com.genhot.shopper.repository.rowmapper;

import com.genhot.shopper.domain.Delivery;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Delivery}, with proper type conversions.
 */
@Service
public class DeliveryRowMapper implements BiFunction<Row, String, Delivery> {

    private final ColumnConverter converter;

    public DeliveryRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Delivery} stored in the database.
     */
    @Override
    public Delivery apply(Row row, String prefix) {
        Delivery entity = new Delivery();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTrackingNumber(converter.fromRow(row, prefix + "_tracking_number", String.class));
        entity.setCarrier(converter.fromRow(row, prefix + "_carrier", String.class));
        entity.setShippingDate(converter.fromRow(row, prefix + "_shipping_date", Instant.class));
        return entity;
    }
}
