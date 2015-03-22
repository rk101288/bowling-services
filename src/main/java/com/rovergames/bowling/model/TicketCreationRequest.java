package com.rovergames.bowling.model;

import java.math.BigDecimal;

/**
 * User: Richa
 * Date: 3/21/15
 * The request object when a bowler purchases tickets at the bowling alley.
 * The amount paid is the total amount paid for all of the tickets in the response.
 */
public class TicketCreationRequest {
    private String bowlerId;

    private int numberOfTickets;

    private BigDecimal amountPaid;

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}
