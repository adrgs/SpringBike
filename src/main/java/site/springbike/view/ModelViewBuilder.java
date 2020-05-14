package site.springbike.view;

import org.springframework.util.StreamUtils;
import site.springbike.model.SpringBikeModel;
import site.springbike.model.sql.Column;
import site.springbike.repository.ModelRepository;
import site.springbike.repository.RepositoryUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
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

    public String getLabelFromColumn(String column){
        column = column.replace("_"," ");
        return column.substring(0, 1).toUpperCase() + column.substring(1);
    }

    public String generateInputs() {
        String inputs = "";
        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if(!column.primaryKey() && !column.foreignKey() && column.showInForm()){
                        String type = "";
                        if(field.getType().equals(String.class)) {
                            type = "text";
                        }
                        if(field.getType().equals(BigDecimal.class)) {
                            type = "number";
                        }
                        if(field.getType().equals(Timestamp.class)) {
                            type = "date";
                        }
                        inputs += "<label for=\"" + column.name() + "\">" + getLabelFromColumn(column.name()) + "</label><br/>";
                        inputs += "<input type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\"><br/>";

                    }
                }
            }
        }
        for (Field field : myClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null){
                if(!column.primaryKey() && !column.foreignKey() && column.showInForm()){
                    String type = "";
                    if(field.getType().equals(String.class)) {
                        type = "text";
                    }
                    if(field.getType().equals(BigDecimal.class)) {
                        type = "number";
                    }
                    if(field.getType().equals(Timestamp.class)) {
                        type = "date";
                    }
                    inputs += "<label for=\"" + column.name() + "\">" + getLabelFromColumn(column.name()) + "</label><br/>";
                    inputs += "<input type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\"><br/>";
                }
            }
        }
        return inputs;
    }

    public String generateForm(String action) {
        String form;
        if (action == null || action.isEmpty() || action.isBlank()) {
            form = "<form method=\"POST\"> <br/> ";
        } else {
            form = "<form method=\"POST\" action=\"" + action + "\"> <br/> ";
        }


        form += generateInputs();

        form += "<input type=\"submit\" value=\"Submit\">";
        form += "</form><br/>";

        return form;
    }

}
