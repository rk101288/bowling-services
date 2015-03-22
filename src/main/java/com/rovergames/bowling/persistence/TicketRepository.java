package com.rovergames.bowling.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * User: Richa
 * Date: 3/21/15
 */
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query("SELECT t.id from Ticket t")
    List<String> findAllIds();

    @Query("SELECT SUM(t.price) from Ticket t")
    BigDecimal getTotalAmount();

    List<Ticket> findByCreatedDateLessThan(Date date);
}
