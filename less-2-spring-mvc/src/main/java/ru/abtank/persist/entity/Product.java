package ru.abtank.persist.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table (name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    public Product(Integer id, String name, String description, BigDecimal price) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public Product(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        cb.append("name=").append(name).append(", ");
        cb.append("description=").append(description).append(", ");
        cb.append("price=").append(price).append(", ");
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
