package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Table(name = "Coupon")
public final class Coupon implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "date_start", nullable = true)
    private Timestamp dateStart;

    @Column(name = "date_finish", nullable = true)
    private Timestamp dateFinish;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "voucher",isBool = true)
    private Boolean voucher;

    public Coupon(Integer id, String code, Timestamp dateStart, Timestamp dateFinish, BigDecimal value, Boolean voucher) {
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
