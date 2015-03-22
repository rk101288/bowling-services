package com.rovergames.bowling.controller;

import com.rovergames.bowling.exception.BadRequestException;
import com.rovergames.bowling.persistence.Jackpot;
import com.rovergames.bowling.service.JackpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@RestController
@RequestMapping("/jackpot")
public class JackpotController {
    private static final String MISMATCH_IDS = "ID in the path should match the ID of the entity for update.";

    @Autowired
    private JackpotService service;

    /**
     * Finds a Jackpot by its id
     * @param id The id of the jackpot
     * @return The requested jackpot
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Jackpot> getById(@PathVariable String id) {
        Optional<Jackpot> entity = service.getById(id);

        if(!entity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(entity.get(), HttpStatus.OK);
        }
    }

    /**
     * Draws the jackpot and creates a jackpot object with the chosen winner
     * @return A location header with a link to the created jackpot entity
     */
    @RequestMapping(value = "/drawJackpot", method = RequestMethod.POST)
    public ResponseEntity<Jackpot> add () {
        Jackpot result = service.drawJackpot();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
            ServletUriComponentsBuilder
            .fromCurrentServletMapping().path("/jackpot/{id}")
            .buildAndExpand(result.getId()).toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    /**
     * Updates the jackpot after the bowler attempts for a strike
     * @param id The id of the jackpot to update
     * @param entity The jackpot object with the updated pins knocked down
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update (@PathVariable String id, @RequestBody Jackpot entity) {
        if(!id.equals(entity.getId())) {
            throw new BadRequestException(MISMATCH_IDS);
        }
        service.updateJackpot(entity);
    }

    /**
     * Returns jackpots that were won by the bowler associated with the passed in bowlerId
     * @param bowlerId The bowler id of the bowler
     * @return List of jackpots that the bowler won
     */
    @RequestMapping(value = "/bowlerId/{bowlerId}", method = RequestMethod.GET)
    public ResponseEntity<List<Jackpot>> getByBowlerId(@PathVariable String bowlerId) {
        List<Jackpot> jackpots = service.findJackpotsByBowlerId(bowlerId);
        return new ResponseEntity<>(jackpots, HttpStatus.OK);
    }

    /**
     * Returns jackpots that were won by the bowler associated with the passed in email address
     * @param email The email address of the bowler
     * @return List of jackpots that the bowler won
     */
    @RequestMapping(value = "/bowlerEmailAddress/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<Jackpot>> getByBowlerEmailAddress(@PathVariable String email) {
        List<Jackpot> jackpots = service.findJackpotsByBowlerEmailAddress(email);
        return new ResponseEntity<>(jackpots, HttpStatus.OK);
    }

    /**
     * Get the monetary value of the current jackpot in progress
     * @return The current jackpot in progress
     */
    @RequestMapping(value = "/currentJackpotValue", method = RequestMethod.GET)
    public ResponseEntity<BigDecimal> getCurrentJackpotValue() {
        return new ResponseEntity<>(service.getCurrentJackpotValue(), HttpStatus.OK);
    }

}

