package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;

@Table(name = "Bike")
public final class Bike implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "id_type", foreignKey = true, nullable = true)
    private Integer idType;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "avatar_url", nullable = true)
    private String avatarURL;

    public Bike(Integer id, String name, BigDecimal price, Integer idType, String description, String avatarURL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idType = idType;
        this.description = description;
        this.avatarURL = avatarURL;
    }

    public Bike() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
