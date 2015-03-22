package com.rovergames.bowling

import com.rovergames.TestEntity
import com.rovergames.TestRepository
import com.rovergames.TestService
import com.rovergames.bowling.exception.BadRequestException
import com.rovergames.bowling.exception.ResourceNotFoundException
import org.unitils.reflectionassert.ReflectionAssert
import spock.lang.Specification


/**
 * User: Richa
 * Date: 3/21/15
 */
class AbstractServiceSpec extends Specification {
	private TestService testService
	private TestRepository repository

	def setup() {
		repository = Mock(TestRepository)

		testService = new TestService()
		testService.repository = repository
	}

	def 'get entity by ID' () {
		given: 'an entity in the repository'
			TestEntity entity = new TestEntity(id: id)
			1 * repository.findOne(id) >> entity

		when: 'entity is requested by ID'
			Optional<TestEntity> actualEntity =  testService.getById(id)

		then: 'matching entity is returned'
			ReflectionAssert.assertReflectionEquals(entity, actualEntity.get())

		where:
			id = 'someid'
	}

	def 'return optional empty when no matching entity is found' () {
		given: 'no matching entity'
			1 * repository.findOne(id) >> null

		when: 'entity is requested by ID'
			Optional<TestEntity> actualEntity =  testService.getById(id)

		then: 'optional empty is returned'
			!actualEntity.isPresent()

		where:
			id = 'someid'
	}

	def 'create new entity'()  {
		given:
			TestEntity testEntity = new TestEntity(testProperty: testProperty)

		when:
		  TestEntity actualEntity = testService.create(testEntity)

		then:
			1 * repository.save(testEntity) >> { TestEntity givenEntity ->
				assert givenEntity.testProperty == testProperty
				givenEntity.id = id
				return givenEntity
			}

		and:
			actualEntity.id == id

		where:
			id = 'someid'
			testProperty = 'someid'
	}

	def 'throw bad request exception if DB ID is given on create'()  {
		given:
			TestEntity testEntity = new TestEntity(id: id, testProperty: testProperty)

		when:
			testService.create(testEntity)

		then:
			BadRequestException badRequestException = thrown(BadRequestException)
			badRequestException.message == "ID cannot be accepted while creating a new entity."

		and:
			0 * repository.save(testEntity)

		where:
			id = 'someid'
			testProperty = 'someid'
	}

	def 'throw resource not found exception if the entity is not found on delete'()  {
		given:
			1 * repository.findOne(id) >> null

		when:
			testService.delete(id)

		then:
			ResourceNotFoundException exception = thrown(ResourceNotFoundException)
			exception.message == "Entity with ID ${id} not found."

		and:
			0 * repository.delete(id)

		where:
			id = 'someid'
			testProperty = 'someid'
	}

	def 'delete an entity'()  {
		given:
			TestEntity testEntity = new TestEntity(id: id, testProperty: testProperty)
			1 * repository.findOne(id) >> testEntity

		when:
			testService.delete(id)

		then:
			1 * repository.delete(id)

		where:
			id = 'someid'
			testProperty = 'someid'
	}
}
