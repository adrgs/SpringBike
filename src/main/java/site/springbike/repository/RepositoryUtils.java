package site.springbike.repository;

import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import site.springbike.model.sql.SQLObjectException;
import site.springbike.model.sql.Table;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.*;

public class RepositoryUtils {
    public static String getTableName(SpringBikeModel model) {
        Class<?> myClass = model.getClass();
        Table table = myClass.getAnnotation(Table.class);
        return table.name();
    }

    public static List<Column> getColumnList(SpringBikeModel model) {
        List<Column> list = new ArrayList<>();

        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null)
                    list.add(column);
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null)
                list.add(column);
        }

        return list;
    }

    public static List<String> getColumns(SpringBikeModel model) {
        List<String> list = new ArrayList<>();

        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null)
                    list.add(column.name());
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null)
                list.add(column.name());
        }

        return list;
    }

    public static LinkedHashMap<Column, Object> getColumnValueMap(SpringBikeModel model) {
        LinkedHashMap<Column, Object> result = new LinkedHashMap<>();

        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    field.setAccessible(true);
                    try {
                        result.put(column, field.get(model));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                try {
                    result.put(column, field.get(model));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static List<Object> getValues(SpringBikeModel model) {
        List<Object> list = new ArrayList<>();

        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    field.setAccessible(true);
                    try {
                        list.add(field.get(model));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                try {
                    list.add(field.get(model));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
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

    public static Integer getPrimaryKeyValue(SpringBikeModel model) {
        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                field.setAccessible(true);
                if (column.primaryKey()) {
                    try {
                        return (Integer) field.get(model);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            field.setAccessible(true);
            if (column.primaryKey()) {
                try {
                    return (Integer) field.get(model);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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
