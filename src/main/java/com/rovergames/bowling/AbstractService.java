package com.rovergames.bowling;

import com.rovergames.bowling.exception.BadRequestException;
import com.rovergames.bowling.exception.ResourceNotFoundException;
import com.rovergames.bowling.persistence.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
public abstract class AbstractService<E extends AbstractEntity, R extends CrudRepository<E, String>> implements ServiceInterface<E, String> {
    private static final String ID_ON_POST = "ID cannot be accepted while creating a new entity.";
    private static final String NOT_FOUND = "Entity with ID %s not found.";

    @Autowired
    public R repository;

    public Optional<E> getById(String id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    public E create(E entity) {
        if(entity.getId() != null) {
            throw new BadRequestException(String.format(ID_ON_POST));
        }

        return repository.save(entity);
    }

    public void delete(String id) {
        if(repository.findOne(id) == null) {
            throw new ResourceNotFoundException(String.format(NOT_FOUND, id));
        }
        repository.delete(id);
    }
}
