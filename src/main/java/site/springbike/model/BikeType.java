package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

@Table(name = "BikeType")
public final class BikeType implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;
    @Column(name = "type")
    private String type;

    public BikeType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public BikeType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}