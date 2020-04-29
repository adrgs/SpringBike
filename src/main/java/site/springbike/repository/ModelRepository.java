package site.springbike.repository;

import site.springbike.database.DatabaseManager;
import site.springbike.model.SpringBikeModel;

import java.sql.Connection;

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
        try {
            connection = DatabaseManager.getConnection();

            String primaryKeyColumn = RepositoryUtils.getPrimaryKeyColumn(model);
            String sql = new SQLQueryBuilder().useModel(model).select().where().column(primaryKeyColumn).equals().generate();

            System.out.println(sql);

            connection.close();
        } catch (Exception e) {
            return null;
        }

        return this.model;
    }
}
