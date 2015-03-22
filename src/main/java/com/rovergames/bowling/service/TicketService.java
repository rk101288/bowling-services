package com.rovergames.bowling.service;

import com.rovergames.bowling.AbstractService;
import com.rovergames.bowling.exception.BadRequestException;
import com.rovergames.bowling.model.TicketCreationRequest;
import com.rovergames.bowling.persistence.Bowler;
import com.rovergames.bowling.persistence.Ticket;
import com.rovergames.bowling.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Service
public class TicketService extends AbstractService<Ticket, TicketRepository> {
    private static final String INVALID_BOWLER_ID = "Invalid bowler. Unable to assign ticket.";

    @Autowired
    private BowlerService bowlerService;

    public Optional<Bowler> findJackpotCandidate() {
        List<String> ticketIds = repository.findAllIds();

        if(ticketIds.isEmpty()){
            return Optional.empty();
        }

        Collections.shuffle(ticketIds);
        String ticketId = ticketIds.get(0);

        Ticket ticket = repository.findOne(ticketId);

        if(ticket == null) {
            return Optional.empty();
        }

        return bowlerService.getById(ticket.getBowlerId());
    }

    public void createTickets (TicketCreationRequest request) {
        Optional<Bowler> bowler = bowlerService.getById(request.getBowlerId());

        if(!bowler.isPresent()) {
            throw new BadRequestException(INVALID_BOWLER_ID);
        }

        for(int i = 0; i < request.getNumberOfTickets(); i++) {
            Ticket ticket = new Ticket();
            ticket.setBowlerId(bowler.get().getId());
            //set total amount on the first ticket as price breaks are not implemented.
            ticket.setPrice(i == 0 ? request.getAmountPaid() : BigDecimal.ZERO);
            repository.save(ticket);
        }
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = repository.getTotalAmount();

        if(total == null) {
            return BigDecimal.ZERO;
        } else {
            return total;
        }
    }

    public void deleteTicketsOlderThan(Date date) {
        List<Ticket> tickets = repository.findByCreatedDateLessThan(date);
        tickets.forEach(repository::delete);
    }
}
