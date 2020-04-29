package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;
import java.sql.Date;


@Table(name = "ActivationCode")
public final class ActivationCode implements SpringBikeModel {

    @Column(name = "id",primaryKey = true)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "id_user",foreignKey = true)
    private String idUser;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "minutes_available")
    private Integer minutesAvailable;

    @Column(name = "claimed")
    private Boolean claimed;

    public ActivationCode(Integer id, String code, String idUser, Date dateStart, Integer minutesAvailable, Boolean claimed) {
        this.id = id;
        this.code = code;
        this.idUser = idUser;
        this.dateStart = dateStart;
        this.minutesAvailable = minutesAvailable;
        this.claimed = claimed;
    }

    public ActivationCode()
    {

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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Integer getMinutesAvailable() {
        return minutesAvailable;
    }

    public void setMinutesAvailable(Integer minutesAvailable) {
        this.minutesAvailable = minutesAvailable;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }
}
