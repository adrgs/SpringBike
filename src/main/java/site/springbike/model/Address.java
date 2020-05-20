package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

@Table(name="Address")
public final class Address implements SpringBikeModel {

    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "zipcode",nullable = true)
    private String zipcode;

    @Column(name = "street")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    public Address(Integer id, String zipcode, String name, String city, String country) {
        this.id = id;
        this.zipcode = zipcode;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public Address()
    {

    }

    public Integer getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

