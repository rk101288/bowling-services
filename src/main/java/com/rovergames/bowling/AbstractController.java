package com.rovergames.bowling;

import com.rovergames.bowling.persistence.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
public abstract class AbstractController<E extends AbstractEntity, S extends ServiceInterface<E, String>> {
    @Autowired
    public S service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<E> getById(@PathVariable String id) {
        //TODO Conditional GET.
        Optional<E> entity = service.getById(id);

        if(!entity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<E> add (@RequestBody E entity) {
        E result = service.create(entity);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(result.getId()).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable String id){
        service.delete(id);
    }
}
