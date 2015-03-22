package com.rovergames.bowling.persistence;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Entity
@Table(name = "jackpot")
public class Jackpot extends AbstractEntity {
    @Id
    @GeneratedValue(generator = "jackpot-uuid")
    @GenericGenerator(name = "jackpot-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    private BigDecimal amountPaid;

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal rolloverAmount;

    private Date drawnDate;

    @Column(length = 10)
    private int numberOfPins;

    @Column(nullable = false)
    private String bowlerId;

    private boolean inProgress;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRolloverAmount() {
        return rolloverAmount;
    }

    public void setRolloverAmount(BigDecimal rolloverAmount) {
        this.rolloverAmount = rolloverAmount;
    }

    public Date getDrawnDate() {
        return drawnDate;
    }

    public void setDrawnDate(Date drawnDate) {
        this.drawnDate = drawnDate;
    }

    public int getNumberOfPins() {
        return numberOfPins;
    }

    public void setNumberOfPins(int numberOfPins) {
        this.numberOfPins = numberOfPins;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }
}
