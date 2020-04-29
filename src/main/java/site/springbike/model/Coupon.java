package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Date;

@Table(name = "Coupon")
public final class Coupon implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "date_start", nullable = true)
    private Date dateStart;

    @Column(name = "date_finish", nullable = true)
    private Date dateFinish;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "voucher")
    private Boolean voucher;

    public Coupon(Integer id, String code, Date dateStart, Date dateFinish, BigDecimal value, Boolean voucher) {
        this.id = id;
        this.code = code;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.value = value;
        this.voucher = voucher;
    }

    public Coupon() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getVoucher() {
        return voucher;
    }

    public void setVoucher(Boolean voucher) {
        this.voucher = voucher;
    }
}
