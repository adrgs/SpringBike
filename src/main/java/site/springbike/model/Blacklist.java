package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;
import java.sql.Date;

@Table(name = "Blacklist")
public final class Blacklist implements SpringBikeModel {

    @Column(name = "id_company", primaryKey = true, foreignKey = true)
    private Integer idCompany;

    @Column(name = "id_client", primaryKey = true, foreignKey = true)
    private Integer idClient;

    @Column(name = "reason",nullable = true)
    private String reason;

    @Column(name = "date_created")
    private Date dateCreated;

    public Blacklist(Integer idCompany, Integer idClient, String reason, Date dateCreated) {
        this.idCompany = idCompany;
        this.idClient = idClient;
        this.reason = reason;
        this.dateCreated = dateCreated;
    }

    public Blacklist()
    {

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
