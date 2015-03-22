package com.rovergames;

import com.rovergames.bowling.persistence.AbstractEntity;

/**
 * User: Richa
 * Date: 3/21/15
 */
public class TestEntity extends AbstractEntity {
    private String testProperty;

    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }
}
