package com.rovergames.bowling.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Repository
public interface JackpotRepository extends CrudRepository<Jackpot, String> {
    @Query("select j from Jackpot j where j.bowlerId = :bowlerId")
    List<Jackpot> findByBowlerId(@Param("bowlerId") String bowlerId);

    Jackpot findTopByOrderByDrawnDateDesc();

    Jackpot findByInProgress(boolean inProgress);
}
