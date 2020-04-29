package site.springbike.model;

import site.springbike.model.sql.Column;
import site.springbike.model.sql.Table;

import java.math.BigDecimal;
import java.sql.Date;

@Table(name = "User")
public final class Client extends User {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Client(Integer id, String username, String email, Boolean emailConfirmed, String password, Boolean enabled, Boolean deleted, BigDecimal balance, Date dateCreated, String description, String avatarURL, String firstName, String lastName) {
        super(id, username, email, emailConfirmed, password, enabled, deleted, balance, "Client", dateCreated, description, avatarURL);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
