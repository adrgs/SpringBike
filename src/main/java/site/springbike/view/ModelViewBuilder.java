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
    String fields = "";

    private ModelViewBuilder(SpringBikeModel model, boolean editForm) {
        this.model = model;
        fields = generateInputs(this.model, editForm);
    }

    public static ModelViewBuilder useModel(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        return new ModelViewBuilder(model, false);
    }

    public static ModelViewBuilder useModelToEdit(SpringBikeModel model) {
        RepositoryUtils.checkIfSQLObject(model);
        return new ModelViewBuilder(model, true);
    }

    public String getLabelFromColumn(String column){
        column = column.replace("_"," ");
        return column.substring(0, 1).toUpperCase() + column.substring(1);
    }

    public ModelViewBuilder addInput(String name, String type, boolean required, String value) {
        this.fields += "<label for=\"" + name + "\">" + getLabelFromColumn(name) + (!required ? "" : "*") + "</label><br/>";
        this.fields += "<input " + (value == null || value.isBlank() ? "" : "value=\"" + value + "\"") + " class=\"form-control\" type=\"" + type + "\" id=\"" + name + "\" name=\"" + name + "\" " + (!required ? "" : "required") + "><br/>";
        return this;
    }

    public ModelViewBuilder addInputs(SpringBikeModel model) {
        this.fields += generateInputs(model, false);
        return this;
    }

    public ModelViewBuilder addEditInputs(SpringBikeModel model) {
        this.fields += generateInputs(model, true);
        return this;
    }

    public String generateInputs(SpringBikeModel model, boolean editForm) {
        String inputs = "";
        Class<?> myClass = model.getClass();

        if (myClass.getSuperclass() != null) {
            for (Field field : myClass.getSuperclass().getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if(!column.primaryKey() && !column.foreignKey() && column.showInForm()){
                        String type = "";
                        if(field.getType().equals(String.class)) {
                            if (column.name().contains("password")) {
                                type = "password";
                            } else {
                                type = "text";
                            }
                        }
                        if(field.getType().equals(BigDecimal.class)) {
                            type = "number";
                        }
                        if(field.getType().equals(Timestamp.class)) {
                            type = "date";
                        }
                        inputs += "<label for=\"" + column.name() + "\">" + getLabelFromColumn(column.name()) + (column.nullable() ? "" : "*") + "</label><br/>";
                        try {
                            field.setAccessible(true);
                            inputs += "<input class=\"form-control\" type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\" " + (column.nullable() ? "" : "required ") + (editForm ? "value=\"" + field.get(model).toString() + "\" " : " ") + "><br/>";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            inputs += "<input class=\"form-control\" type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\" " + (column.nullable() ? "" : "required ") + "><br/>";
                        }
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
                        if (column.name().contains("password")) {
                            type = "password";
                        } else {
                            type = "text";
                        }
                    }
                    if(field.getType().equals(BigDecimal.class)) {
                        type = "number";
                    }
                    if(field.getType().equals(Timestamp.class)) {
                        type = "date";
                    }
                    inputs += "<label for=\"" + column.name() + "\">" + getLabelFromColumn(column.name()) + (column.nullable() ? "" : "*") + "</label><br/>";
                    try {
                        field.setAccessible(true);
                        inputs += "<input class=\"form-control\" type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\" " + (column.nullable() ? "" : "required ") + (editForm ? "value=\"" + field.get(model).toString() + "\" " : " ") + "><br/>";
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        inputs += "<input class=\"form-control\" type=\"" + type + "\" id=\"" + column.name() + "\" name=\"" + column.name() + "\" " + (column.nullable() ? "" : "required ") + "><br/>";
                    }
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

        form += fields;

        form += "<button type=\"submit\">Submit</button>";
        form += "</form>";

        return form;
    }

}
