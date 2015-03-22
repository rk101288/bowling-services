package com.rovergames.bowling.persistence;

/**
 * User: Richa
 * Date: 3/22/15
 */
public abstract class AbstractEntity {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
