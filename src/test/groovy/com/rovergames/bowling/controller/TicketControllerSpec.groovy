package com.rovergames.bowling.controller

import com.rovergames.bowling.model.TicketCreationRequest
import com.rovergames.bowling.service.TicketService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

/**
 * User: Richa
 * Date: 3/22/15
 */
class TicketControllerSpec extends Specification {
	private TicketController ticketController

	private TicketService ticketService

	def setup(){
		ticketService = Mock(TicketService)
		ticketController = new TicketController(service: ticketService)
	}

	def 'create tickets' () {
		given:
			TicketCreationRequest ticketCreationRequest = new TicketCreationRequest(bowlerId: 'a', numberOfTickets: 5, amountPaid: 20)

		when:
			ResponseEntity response = ticketController.addTickets(ticketCreationRequest)

		then:
			1 * ticketService.createTickets(ticketCreationRequest)

		and:
			response.statusCode == HttpStatus.CREATED
	}
}
