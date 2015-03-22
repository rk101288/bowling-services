package com.rovergames.bowling.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Entity
@Table(name = "bowler")
public class Bowler extends AbstractEntity{
    @Id
    @GeneratedValue(generator = "bowler-uuid")
    @GenericGenerator(name = "bowler-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    @Column(unique = true)
    private String email;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
