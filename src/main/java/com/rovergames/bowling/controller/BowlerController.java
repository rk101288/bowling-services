package com.rovergames.bowling.controller;

import com.rovergames.bowling.AbstractController;
import com.rovergames.bowling.persistence.Bowler;
import com.rovergames.bowling.service.BowlerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@RestController
@RequestMapping("/bowler")
public class BowlerController extends AbstractController<Bowler, BowlerService> {

    /**
     * Find a bowler based on their email address
     * @param email The email address of the bowler
     * @return The bowler
     */
    @RequestMapping(value = "/email/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<Bowler> getByEmail(@PathVariable String email) {
        Optional<Bowler> bowler = service.getByEmail(email);

        if(!bowler.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bowler.get(), HttpStatus.OK);
        }
    }
}
