package site.springbike.repository;

import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import site.springbike.model.sql.SQLObjectException;
import site.springbike.model.sql.Table;

import java.lang.reflect.Field;
import java.util.Objects;

public class RepositoryUtils {
    public static String getTableName(SpringBikeModel model) {
        Class<?> myClass = model.getClass();
        Table table = myClass.getAnnotation(Table.class);
        return table.name();
    }

    public static String getPrimaryKeyColumn(SpringBikeModel model) {
        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column.primaryKey()) {
                    return column.name();
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column.primaryKey()) {
                return column.name();
            }
        }

        return null;
    }

    public static void checkIfSQLObject(SpringBikeModel model) throws SQLObjectException {
        if (Objects.isNull(model)) {
            throw new SQLObjectException("The object to serialize is null");
        }

        Class<?> myClass = model.getClass();

        if (!myClass.isAnnotationPresent(Table.class)) {
            throw new SQLObjectException("The class " + myClass.getSimpleName() + " is not annotated with model.sql package");
        }
    }
}
