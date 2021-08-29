package ru.abtank.persist.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotBlank(message = "Укажите Login") //валидация
    private String login;
    @Column(length = 255)
    @NotBlank(message = "Укажите password")//валидация
    private String password;
    @Transient //не добавляем в БД
    private String matchingPassword;
    @Column
    @Email(message = "Укажите корректный Email")//валидация
    @NotBlank(message = "Укажите Email")
    private String email;


    @Column
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

//добавляем самого себя в entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id", nullable=false, foreignKey=@ForeignKey(name = "FK_CREATOR_ID"))
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable=false) //
    private Date createDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date", nullable=false)
    private Date modifyDate;

    public User(Integer id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

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
    @PrePersist
    public void setCreateDate() {
        this.createDate = this.modifyDate = new Date();
    }

    public Date getModifyDate() {
        return modifyDate;
    }
    @PreUpdate
    public void setModifyDate() {
        this.modifyDate = new Date();
    }

    @Override
    public String toString() {
        StringBuilder cb = new StringBuilder("User{");
        cb.append("id=").append(id).append(", ");
        cb.append("login=").append(login).append(", ");
        cb.append("password=").append(password).append(", ");
        cb.append("matchingPassword=").append(matchingPassword).append(", ");
        cb.append("email=").append(email).append(", ");
        if (createDate != null) {
            cb.append("createDate=").append(new SimpleDateFormat("dd MMMM yyyy").format(createDate)).append(", ");
        }
        if (modifyDate != null) {
            cb.append("modifyDate=").append(new SimpleDateFormat("dd MMMM yyyy").format(modifyDate)).append(", ");
        }
        cb.append("}\n");

        return cb.toString();
    }
}
