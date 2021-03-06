package ru.abtank.persist.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotBlank (message = "Укажите Login") //валидация
    private String login;
    @Column
    @NotBlank (message = "Укажите password")//валидация
    private String password;
    @Transient //не добавляем в БД
    private String matchingPassword;
    @Column
    @Email (message = "Укажите корректный Email")//валидация
    @NotBlank (message = "Укажите Email")
    private String email;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    public User(Integer id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        StringBuilder cb = new StringBuilder("User{");
        cb.append("id=").append(id).append(", ");
        cb.append("login=").append(login).append(", ");
        cb.append("password=").append(password).append(", ");
        cb.append("matchingPassword=").append(matchingPassword).append(", ");
        cb.append("email=").append(email).append(", ");
        if(createDate != null){
            cb.append("createDate=").append(new SimpleDateFormat("dd MMMM yyyy").format(createDate)).append(", ");
        }
        if(modifyDate != null){
            cb.append("modifyDate=").append(new SimpleDateFormat("dd MMMM yyyy").format(modifyDate)).append(", ");
        }
        cb.append("}\n");

        return cb.toString();
    }
}
