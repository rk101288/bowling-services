package com.rovergames.bowling.persistence;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Entity
@Table(name = "ticket")
public class Ticket extends AbstractEntity{
    @Id
    @GeneratedValue(generator = "ticket-uuid")
    @GenericGenerator(name = "ticket-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    @Column(nullable = false)
    private String bowlerId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Date createdDate = new Date();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
