package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.sql.Timestamp;

@Table(name = "Transaction")
public final class Transaction implements SpringBikeModel {
    @Column(name = "id", primaryKey = true)
    private Integer id;

    @Column(name = "id_lease", foreignKey = true)
    private Integer idLease;

    @Column(name = "id_location_start", foreignKey = true, nullable = true)
    private Integer idLocationStart;

    @Column(name = "id_location_finish", foreignKey = true, nullable = true)
    private Integer idLocationFinish;

    @Column(name = "date_finish", nullable = true)
    private Timestamp dateFinish;

    @Column(name = "finished", hasDefaultValue = true,isBool = true)
    private Boolean finished;

    public Transaction(Integer id, Integer idLease, Integer idLocationStart, Integer idLocationFinish, Timestamp dateFinish, Boolean finished) {
        this.id = id;
        this.idLease = idLease;
        this.idLocationStart = idLocationStart;
        this.idLocationFinish = idLocationFinish;
        this.dateFinish = dateFinish;
        this.finished = finished;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLease() {
        return idLease;
    }

    public void setIdLease(Integer idLease) {
        this.idLease = idLease;
    }

    public Integer getIdLocationStart() {
        return idLocationStart;
    }

    public void setIdLocationStart(Integer idLocationStart) {
        this.idLocationStart = idLocationStart;
    }

    public Integer getIdLocationFinish() {
        return idLocationFinish;
    }

    public void setIdLocationFinish(Integer idLocationFinish) {
        this.idLocationFinish = idLocationFinish;
    }

    public Timestamp getTimestampFinish() {
        return dateFinish;
    }

    public void setTimestampFinish(Timestamp dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
