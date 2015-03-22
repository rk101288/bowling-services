package com.rovergames.bowling.controller;

import com.rovergames.bowling.AbstractController;
import com.rovergames.bowling.model.TicketCreationRequest;
import com.rovergames.bowling.persistence.Ticket;
import com.rovergames.bowling.service.TicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * User: Richa
 * Date: 3/21/15
 */
@RestController
@RequestMapping("/ticket")
public class TicketController extends AbstractController<Ticket, TicketService> {

    @RequestMapping(value = "/createTickets", method = RequestMethod.POST)
    public ResponseEntity add (@RequestBody TicketCreationRequest request) {
        service.createTickets(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
