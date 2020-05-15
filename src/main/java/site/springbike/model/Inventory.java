package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;

@Table(name = "Inventory")
public final class Inventory implements SpringBikeModel{

    @Column(name = "id",primaryKey = true)
    private Integer id;

    @Column(name = "id_company",foreignKey = true)
    private Integer idCompany;

    @Column(name = "id_bike", foreignKey = true)
    private Integer idBike;

    @Column(name = "id_location",foreignKey = true,nullable = true)
    private Integer idLocation;

    @Column(name = "rent_price_hour")
    private BigDecimal rentPriceHour;

    @Column(name = "deleted", hasDefaultValue = true, isBool = true, showInForm = false)
    private Boolean deleted;

    @Column(name = "visible", hasDefaultValue = true, isBool = true, showInForm = false)
    private Boolean visible;

    public Inventory(Integer id, Integer idCompany, Integer idBike, Integer idLocation, BigDecimal rentPriceHour, Boolean deleted, Boolean visible) {
        this.id = id;
        this.idCompany = idCompany;
        this.idBike = idBike;
        this.idLocation = idLocation;
        this.rentPriceHour = rentPriceHour;
        this.deleted = deleted;
        this.visible = visible;
    }

    public Inventory()
    {

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

    public Integer getIdBike() {
        return idBike;
    }

    public void setIdBike(Integer idBike) {
        this.idBike = idBike;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public BigDecimal getRentPriceHour() {
        return rentPriceHour;
    }

    public void setRentPriceHour(BigDecimal rentPriceHour) {
        this.rentPriceHour = rentPriceHour;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
