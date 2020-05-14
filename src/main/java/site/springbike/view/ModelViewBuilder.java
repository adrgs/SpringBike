package site.springbike.view;

import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.RepositoryUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ModelViewBuilder {

    private SpringBikeModel model;

    private ModelViewBuilder(SpringBikeModel model) {
        this.model = model;
    }

    public static ModelViewBuilder useModel(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        return new ModelViewBuilder(model);
    }

    public ModelViewBuilder generateForm() {

        String form = "<form> \n ";

        List<Column> columnList = RepositoryUtils.getColumnList(model);

        for(Column column : columnList) {

            if(!column.primaryKey() && !column.foreignKey() && column.showInForm()){

            }
        }

    }

}
