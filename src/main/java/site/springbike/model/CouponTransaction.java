package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.sql.Date;

@Table(name = "CouponTransaction")
public final class CouponTransaction implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "id_user", foreignKey = true)
    private Integer idUser;

    @Column(name = "id_coupon", foreignKey = true)
    private Integer idCoupon;

    @Column(name = "date_created", hasDefaultValue = true)
    private Date dateCreated;

    public CouponTransaction(Integer id, Integer idUser, Integer idCoupon, Date dateCreated) {
        this.id = id;
        this.idUser = idUser;
        this.idCoupon = idCoupon;
        this.dateCreated = dateCreated;
    }

    public CouponTransaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCoupon() {
        return idCoupon;
    }

    public void setIdCoupon(Integer idCoupon) {
        this.idCoupon = idCoupon;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
