package com.rovergames.bowling.service;

import com.rovergames.bowling.AbstractService;
import com.rovergames.bowling.persistence.Bowler;
import com.rovergames.bowling.persistence.BowlerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Service
public class BowlerService extends AbstractService<Bowler, BowlerRepository>{
    public Optional<Bowler> getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
