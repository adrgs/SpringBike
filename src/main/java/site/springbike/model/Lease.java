package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "Lease")
public final class Lease implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "id_company", foreignKey = true)
    private Integer idCompany;

    @Column(name = "id_client", foreignKey = true)
    private Integer idClient;

    @Column(name = "id_inventory", foreignKey = true)
    private Integer idInventory;

    @Column(name = "date_start")
    private Timestamp dateStart;

    @Column(name = "date_finish")
    private Timestamp dateFinish;

    @Column(name = "price_total")
    private BigDecimal priceTotal;

    @Column(name = "random_code")
    private String randomCode;

    public Lease(Integer id, Integer idCompany, Integer idClient, Integer idInventory, Timestamp dateStart, Timestamp dateFinish, BigDecimal priceTotal, String randomCode) {
        this.id = id;
        this.idCompany = idCompany;
        this.idClient = idClient;
        this.idInventory = idInventory;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.priceTotal = priceTotal;
        this.randomCode = randomCode;
    }

    public Lease() {
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

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdInventory() {
        return idInventory;
    }

    public void setIdInventory(Integer idInventory) {
        this.idInventory = idInventory;
    }

    public Timestamp getTimestampStart() {
        return dateStart;
    }

    public void setTimestampStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getTimestampFinish() {
        return dateFinish;
    }

    public void setTimestampFinish(Timestamp dateFinish) {
        this.dateFinish = dateFinish;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Timestamp dateFinish) {
        this.dateFinish = dateFinish;
    }
}
