package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Date;

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
    private Date dateStart;

    @Column(name = "date_finish")
    private Date dateFinish;

    @Column(name = "price_total")
    private BigDecimal priceTotal;

    public Lease(Integer id, Integer idCompany, Integer idClient, Integer idInventory, Date dateStart, Date dateFinish, BigDecimal priceTotal) {
        this.id = id;
        this.idCompany = idCompany;
        this.idClient = idClient;
        this.idInventory = idInventory;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.priceTotal = priceTotal;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }
}
