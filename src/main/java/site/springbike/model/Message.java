package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;
import java.sql.Date;

@Table(name = "Message")
public final class Message implements SpringBikeModel{

    @Column(name = "id",primaryKey = true)
    private Integer id;

    @Column(name = "id_user_sender",foreignKey = true)
    private Integer idUserSender;

    @Column(name = "id_user_receiver",foreignKey = true,nullable = true)
    private Integer idUserReceiver;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "date_created",hasDefaultValue = true)
    private Date dateCreated;

    @Column(name = "email",hasDefaultValue = true)
    private Boolean email;

    public Message(Integer id, Integer idUserSender, Integer idUserReceiver, String subject, String body, Date dateCreated, Boolean email) {
        this.id = id;
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
        this.subject = subject;
        this.body = body;
        this.dateCreated = dateCreated;
        this.email = email;
    }

    public Message()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(Integer idUserSender) {
        this.idUserSender = idUserSender;
    }

    public Integer getIdUserReceiver() {
        return idUserReceiver;
    }

    public void setIdUserReceiver(Integer idUserReceiver) {
        this.idUserReceiver = idUserReceiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }
}
