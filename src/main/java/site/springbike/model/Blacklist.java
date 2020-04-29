package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.sql.Timestamp;

@Table(name = "Blacklist")
public final class Blacklist implements SpringBikeModel {

    @Column(name = "id",primaryKey = true)
    private Integer id;

    @Column(name = "id_company", foreignKey = true)
    private Integer idCompany;

    @Column(name = "id_client", foreignKey = true)
    private Integer idClient;

    @Column(name = "reason",nullable = true)
    private String reason;

    @Column(name = "date_created",hasDefaultValue = true)
    private Timestamp dateCreated;

    public Blacklist(Integer id, Integer idCompany, Integer idClient, String reason, Timestamp dateCreated) {
        this.id = id;
        this.idCompany = idCompany;
        this.idClient = idClient;
        this.reason = reason;
        this.dateCreated = dateCreated;
    }

    public Blacklist()
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

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}
