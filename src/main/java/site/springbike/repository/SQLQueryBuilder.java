package site.springbike.repository;

import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class SQLQueryBuilder {
    private SpringBikeModel model;
    private String sql;
    private String conditions;

    public SQLQueryBuilder() {
        this.sql = "";
        this.conditions = "";
    }

    public SQLQueryBuilder useModel(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        this.model = model;
        return this;
    }

    public String generate() {
        String finalSQL = sql;
        if (sql.contains("SELECT")) {
            finalSQL += " FROM " + RepositoryUtils.getTableName(model);
        } else if (sql.contains("UPDATE")) {
            finalSQL += " " + RepositoryUtils.getTableName(model) + " SET ";
        }
        finalSQL += conditions + ";";
        return finalSQL;
    }

    public SQLQueryBuilder where() {
        conditions += " WHERE ";
        return this;
    }

    public SQLQueryBuilder column(String column) {
        conditions += " " + column + " ";
        return this;
    }

    public SQLQueryBuilder equals() {
        conditions += " = ? ";
        return this;
    }

    public SQLQueryBuilder like() {
        conditions += " LIKE ? ";
        return this;
    }

    public SQLQueryBuilder select(String... columns) {
        sql = "SELECT ";
        boolean hasColumns = false;

        for (String column : columns) {
            if (sql == "SELECT ") {
                sql += column;
            } else {
                sql += ", " + column;
            }
            hasColumns = true;
        }

        if (hasColumns == false) {
            sql += String.join(", ", RepositoryUtils.getColumns(model));
        }
        sql += " ";

        return this;
    }

    public SQLQueryBuilder update(String... columns) {
        sql = "UPDATE ";

        boolean hasColumns = false;

        for (String column : columns) {
            if (conditions == "UPDATE ") {
                conditions += column;
            } else {
                conditions += " = ? , " + column;
            }
            hasColumns = true;
        }

        if (hasColumns == false) {
            conditions += String.join("= ? , ", RepositoryUtils.getColumns(model));
        }

        conditions += " = ? ";

        return this;
    }

}
