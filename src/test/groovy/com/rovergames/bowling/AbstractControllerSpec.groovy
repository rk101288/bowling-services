package com.rovergames.bowling

import com.rovergames.TestController
import com.rovergames.TestEntity
import com.rovergames.TestService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.unitils.reflectionassert.ReflectionAssert
import spock.lang.Specification

/**
 * User: Richa
 * Date: 3/21/15
 */
class AbstractControllerSpec extends Specification {
	private TestController testController

	private TestService testService

	def setup() {
		testService = Mock(TestService)

		testController = new TestController()
		testController.service = testService
	}

	def 'get an entity by ID'() {
		given:
			TestEntity entity = new TestEntity(id: id, testProperty: property)
		  1 * testService.getById(id) >> Optional.of(entity)

		when:
			ResponseEntity<TestEntity> response = testController.getById(id)

		then:
			ReflectionAssert.assertReflectionEquals(entity, response.getBody())

		and:
			response.statusCode == HttpStatus.OK

		where:
			id = 'someId'
			property = 'property'
	}

	def 'return not found response when an entity is not found'() {
		given:
			1 * testService.getById(id) >> Optional.empty()

		when:
			ResponseEntity<TestEntity> response = testController.getById(id)

		then:
			response.body == null

		and:
			response.statusCode == HttpStatus.NOT_FOUND

		where:
			id = 'someId'
	}

	def 'save an entity'() {
		given:
			TestEntity entity = new TestEntity(testProperty: property)
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when:
			ResponseEntity<TestEntity> response = testController.add(entity)

		then:
			1 * testService.create(entity) >> new TestEntity(id: id, testProperty: property)

		and:
			response.headers.getLocation().toString() == "http://localhost/${id}"

		and:
			response.statusCode == HttpStatus.CREATED

		where:
			id = 'someId'
			property = 'property'
	}

	def 'delete an entity'() {
		when:
			testController.delete(id)

		then:
			1 * testService.delete(id)

		where:
			id = 'someId'
	}
}
