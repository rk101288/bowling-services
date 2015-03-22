package com.rovergames.bowling.controller

import com.rovergames.bowling.persistence.Jackpot
import com.rovergames.bowling.service.JackpotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.unitils.reflectionassert.ReflectionAssert
import spock.lang.Specification

/**
 * User: Richa
 * Date: 3/22/15
 */
class JackpotControllerSpec extends Specification {
	private JackpotController jackpotController

	private JackpotService service

	def setup() {
		service = Mock(JackpotService)
		jackpotController = new JackpotController(service: service)
	}

	def 'get jackpot by ID'() {
		given:
			Jackpot jackpot = new Jackpot(id: id)
			1 * service.getById(id) >> Optional.of(jackpot)

		when:
			ResponseEntity<Jackpot> actualResponse = jackpotController.getById(id)

		then:
			ReflectionAssert.assertReflectionEquals(jackpot, actualResponse.body)

		where:
			id = 'someid'
	}

	def 'return not found when a jackpot is not found'() {
		given:
			1 * service.getById(id) >> Optional.empty()

		when:
			ResponseEntity<Jackpot> actualResponse = jackpotController.getById(id)

		then:
			actualResponse.statusCode == HttpStatus.NOT_FOUND

		where:
			id = 'someid'
	}
}
