package site.springbike.repository;

import site.springbike.database.DatabaseManager;
import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ModelRepository {
    private SpringBikeModel model;

    private ModelRepository(SpringBikeModel model) {
        this.model = model;
    }

    public static ModelRepository useModel(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        return new ModelRepository(model);
    }

    public SpringBikeModel updateModel() {
        Connection connection = null;
        SpringBikeModel newModel = null;
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql = new SQLQueryBuilder().useModel(model).update().where().column(primaryKeyColumn).equals().generate();
            Integer id = RepositoryUtils.getPrimaryKeyValue(model);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            List<Object> values = RepositoryUtils.getValues(model);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }
            preparedStatement.setObject(values.size() + 1, id);
            preparedStatement.execute();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newModel;
    }

    public boolean deleteModel() {
        if (model == null) return false;
        try {
            Connection connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql = new SQLQueryBuilder().useModel(model).delete().where().column(primaryKeyColumn).equals().generate();
            Integer id = RepositoryUtils.getPrimaryKeyValue(model);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            int count = preparedStatement.executeUpdate();

            connection.close();

            return (count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public SpringBikeModel insertModel() {
        Connection connection = null;
        SpringBikeModel newModel = null;
        int id = -1;
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql = new SQLQueryBuilder().useModel(model).insert().generate();

            LinkedHashMap<Column, Object> columnValueMap = RepositoryUtils.getColumnValueMap(model);

            for (HashMap.Entry<Column, Object> entry : columnValueMap.entrySet()) {

                if (entry.getKey().hasDefaultValue() == true) {
                    if (entry.getValue() != null) {
                        sql = sql.replace("DEFAULT" + entry.getKey().name(), "?");
                    } else {
                        sql = sql.replace("DEFAULT" + entry.getKey().name(), "DEFAULT");
                    }
                }
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int i = 0;
            for (HashMap.Entry<Column, Object> entry : columnValueMap.entrySet()) {

                if (entry.getKey().primaryKey() == false && (entry.getKey().hasDefaultValue() == false || entry.getValue() != null)) {
                    preparedStatement.setObject(i + 1, entry.getValue());
                    i++;
                }
            }
            int count = preparedStatement.executeUpdate();
            if (count == 0) {
                return null;
            }

            sql = new SQLQueryBuilder().getLastInsertID();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return selectByPrimaryKey(id);
    }

    private List<SpringBikeModel> selectByColumn(String columnName, Object value, boolean like, boolean lower) {
        Connection connection = null;
        SpringBikeModel newModel = null;
        List<SpringBikeModel> newModels = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql;
            SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder().useModel(model);
            sqlQueryBuilder = sqlQueryBuilder.select().where();
            if (lower) {
                sqlQueryBuilder = sqlQueryBuilder.lower(columnName);
            } else {
                sqlQueryBuilder = sqlQueryBuilder.column(columnName);
            }
            if (like) {
                sqlQueryBuilder = sqlQueryBuilder.like();
            } else {
                sqlQueryBuilder = sqlQueryBuilder.equals();
            }
            sql = sqlQueryBuilder.generate();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                newModel = (SpringBikeModel) Class.forName(model.getClass().getName()).getDeclaredConstructor().newInstance();
                Class<?> myClass = model.getClass();
                if (myClass.getSuperclass() != null) {
                    for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Column column = field.getAnnotation(Column.class);
                        if (column == null) continue;

                        if (column.isBool()) {
                            field.set(newModel, resultSet.getBoolean(column.name()));
                        } else {
                            field.set(newModel, resultSet.getObject(column.name()));
                        }
                    }
                }
                for (Field field : myClass.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    if (column.isBool()) {
                        field.set(newModel, resultSet.getBoolean(column.name()));
                    } else {
                        field.set(newModel, resultSet.getObject(column.name()));
                    }
                }
                newModels.add(newModel);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return newModels;
        }

        return newModels;
    }

    public List<SpringBikeModel> getAllByColumn(String columnName, Object value) {
        return selectByColumn(columnName, value, false, false);
    }

    public SpringBikeModel findWildcardByColumn(String columnName, String value) {
        List<SpringBikeModel> modelList = selectByColumn(columnName, value, true, false);
        if (modelList.size() == 0) return null;
        return modelList.get(0);
    }

    public SpringBikeModel findByColumn(String columnName, Object value) {
        List<SpringBikeModel> modelList = selectByColumn(columnName, value, true, false);
        if (modelList.size() == 0) return null;
        return modelList.get(0);
    }

    public SpringBikeModel findWildcardByColumnLowerCase(String columnName, String value) {
        List<SpringBikeModel> modelList = selectByColumn(columnName, value, true, true);
        if (modelList.size() == 0) return null;
        return modelList.get(0);
    }

    public SpringBikeModel findByColumnLowerCase(String columnName, Object value) {
        List<SpringBikeModel> modelList = selectByColumn(columnName, value, false, true);
        if (modelList.size() == 0) return null;
        return modelList.get(0);
    }

    public List<SpringBikeModel> selectByColumns(HashMap<String, Object> columnsValues) {
        Connection connection = null;
        SpringBikeModel newModel = null;
        List<SpringBikeModel> newModels = new ArrayList<>();
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql;
            SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder().useModel(model);
            sqlQueryBuilder = sqlQueryBuilder.select().where();
            int i = 0;
            for (HashMap.Entry<String, Object> entry : columnsValues.entrySet()) {
                sqlQueryBuilder.column(entry.getKey());
                sqlQueryBuilder.equals();
                if (i < columnsValues.size() - 1) {
                    sqlQueryBuilder.and();
                }
                i += 1;
            }
            sql = sqlQueryBuilder.generate();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            i = 0;
            for (HashMap.Entry<String, Object> entry : columnsValues.entrySet()) {
                preparedStatement.setObject(i + 1, entry.getValue());
                i++;
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                newModel = (SpringBikeModel) Class.forName(model.getClass().getName()).getDeclaredConstructor().newInstance();
                Class<?> myClass = model.getClass();
                if (myClass.getSuperclass() != null) {
                    for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Column column = field.getAnnotation(Column.class);
                        if (column == null) continue;

                        if (column.isBool()) {
                            field.set(newModel, resultSet.getBoolean(column.name()));
                        } else {
                            field.set(newModel, resultSet.getObject(column.name()));
                        }
                    }
                }
                for (Field field : myClass.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    if (column.isBool()) {
                        field.set(newModel, resultSet.getBoolean(column.name()));
                    } else {
                        field.set(newModel, resultSet.getObject(column.name()));
                    }
                }
                newModels.add(newModel);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return newModels;
        }

        return newModels;
    }

    public SpringBikeModel selectByPrimaryKey(Integer id) {
        Connection connection = null;
        SpringBikeModel newModel = null;
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql = new SQLQueryBuilder().useModel(model).select().where().column(primaryKeyColumn).equals().generate();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                newModel = (SpringBikeModel) Class.forName(model.getClass().getName()).getDeclaredConstructor().newInstance();
                Class<?> myClass = model.getClass();
                if (myClass.getSuperclass() != null) {
                    for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Column column = field.getAnnotation(Column.class);
                        if (column == null) continue;

                        if (column.isBool()) {
                            field.set(newModel, resultSet.getBoolean(column.name()));
                        } else {
                            field.set(newModel, resultSet.getObject(column.name()));
                        }
                    }
                }
                for (Field field : myClass.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    field.setAccessible(true);
                    if (column.isBool()) {
                        field.set(newModel, resultSet.getBoolean(column.name()));
                    } else {
                        field.set(newModel, resultSet.getObject(column.name()));
                    }
                }
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newModel;
    }
}
