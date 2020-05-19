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
        if (sql.contains("SELECT") || sql.contains("DELETE")) {
            finalSQL += " FROM " + RepositoryUtils.getTableName(model);
        } else if (sql.contains("UPDATE")) {
            finalSQL += " " + RepositoryUtils.getTableName(model) + " SET ";
        } else if (sql.contains("INSERT")) {
            finalSQL += RepositoryUtils.getTableName(model) + "(";

            List<Column> columns = RepositoryUtils.getColumnList(model);
            for (Column col : columns) {
                if (!col.primaryKey()) {
                    if (finalSQL.endsWith("(")) {
                        finalSQL += col.name();
                    } else {
                        finalSQL += ", " + col.name();
                    }
                }
            }
            finalSQL += ") VALUES(";
            int i = 0;
            for (Column col : columns) {
                if (col.primaryKey()) continue;
                if (i != 0) {
                    finalSQL += ", ";
                    ;
                }
                if (col.hasDefaultValue()) {
                    finalSQL += "DEFAULT" + col.name();
                } else {
                    finalSQL += "?";
                }
                i++;
            }
            finalSQL += ")";
        }
        finalSQL += conditions + ";";
        return finalSQL;
    }

    public SQLQueryBuilder insert() {
        //Insert into User(col1,col2,col2...) VALUES (?,?,?) ...
        sql = "INSERT INTO ";
        return this;
    }

    public SQLQueryBuilder delete() {
        sql = "DELETE ";
        return this;
    }

    public SQLQueryBuilder where() {
        conditions += " WHERE ";
        return this;
    }

    public SQLQueryBuilder column(String column) {
        conditions += " " + column + " ";
        return this;
    }

    public SQLQueryBuilder lower(String column) {
        conditions += " LOWER(" + column + ") ";
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

    public SQLQueryBuilder and() {
        conditions += " AND ";
        return this;
    }

    public SQLQueryBuilder or() {
        conditions += " or ";
        return this;
    }

    public SQLQueryBuilder select(String... columns) {
        sql = "SELECT ";
        boolean hasColumns = false;

        for (String column : columns) {
            if (sql.equals("SELECT ")) {
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
            if (conditions.equals("UPDATE ")) {
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

    public String getLastInsertID() {
        String finalSQL = "SELECT LAST_INSERT_ID();";
        return finalSQL;
    }

}
