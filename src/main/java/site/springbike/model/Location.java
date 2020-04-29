package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Date;

@Table(name = "Location")
public final class Location implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "id_company", foreignKey = true)
    private Integer idCompany;

    @Column(name = "id_address", foreignKey = true)
    private Integer idAddress;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "latitude")
    private Integer latitude;

    public Location(Integer id, Integer idCompany, Integer idAddress, Integer longitude, Integer latitude) {
        this.id = id;
        this.idCompany = idCompany;
        this.idAddress = idAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public Integer getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }
}