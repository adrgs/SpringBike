package site.springbike.repository;

import site.springbike.database.DatabaseManager;
import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ModelRepository {
    private SpringBikeModel model;

    private ModelRepository(SpringBikeModel model) {
        this.model = model;
    }

    public static ModelRepository useModel(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        return new ModelRepository(model);
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

            System.out.println(sql);

            newModel = (SpringBikeModel) Class.forName(model.getClass().getName()).getDeclaredConstructor().newInstance();

            if (resultSet.next()) {
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

            System.out.println(newModel);

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return this.model;
    }
}
