package com.genhot.shopper.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DeliverySqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("tracking_number", table, columnPrefix + "_tracking_number"));
        columns.add(Column.aliased("carrier", table, columnPrefix + "_carrier"));
        columns.add(Column.aliased("shipping_date", table, columnPrefix + "_shipping_date"));

        return columns;
    }
}
