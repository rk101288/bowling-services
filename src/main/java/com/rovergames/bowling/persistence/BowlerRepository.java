package com.rovergames.bowling.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Repository
public interface BowlerRepository extends CrudRepository<Bowler, String> {
    Optional<Bowler> findByEmail(String email);
}
