package com.genhot.shopper.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class AddressSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("address_1", table, columnPrefix + "_address_1"));
        columns.add(Column.aliased("city", table, columnPrefix + "_city"));
        columns.add(Column.aliased("state", table, columnPrefix + "_state"));
        columns.add(Column.aliased("zipcode", table, columnPrefix + "_zipcode"));
        columns.add(Column.aliased("country", table, columnPrefix + "_country"));

        columns.add(Column.aliased("customer_id", table, columnPrefix + "_customer_id"));
        return columns;
    }
}
