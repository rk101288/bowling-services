package com.rovergames.bowling.service;

import com.rovergames.bowling.AbstractService;
import com.rovergames.bowling.exception.BadRequestException;
import com.rovergames.bowling.exception.ResourceNotFoundException;
import com.rovergames.bowling.persistence.Bowler;
import com.rovergames.bowling.persistence.Jackpot;
import com.rovergames.bowling.persistence.JackpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * User: Richa
 * Date: 3/21/15
 */
@Service
public class JackpotService extends AbstractService<Jackpot, JackpotRepository> {
    private static final String INVALID_BOWLER = "Bowler with email address %s is not found.";

    private static final int MAX_NUM_OF_RETRIES = 10;

    private static final String NO_CANDIDATE_FOUND = "Unable to find a jackpot candidate.";
    private static final String NO_JACKPOT_FOUND = "No Jackpot found with ID %s";
    private static final String JACKPOT_IN_PROGRESS = "Jackpot %s is already in progress. Update that before drawing new one.";
    private static final String INVALID_NUMBER_OF_PINS = "Invalid number of pins.";

    @Autowired
    private BowlerService bowlerService;

    @Autowired
    private TicketService ticketService;

    public Jackpot drawJackpot() {
        checkJackpotEligibility();

        int numberOfRetries = 0;
        Optional<Bowler> bowler = ticketService.findJackpotCandidate();

        //This kind of retries are better suited in a Business Process Management layer.
        while (!bowler.isPresent() && numberOfRetries < MAX_NUM_OF_RETRIES) {
            bowler = ticketService.findJackpotCandidate();
            numberOfRetries++;
        }

        if(!bowler.isPresent()) {
            throw new ResourceNotFoundException(NO_CANDIDATE_FOUND);
        }

        return saveJackpot(bowler);
    }

    private void checkJackpotEligibility() {
        Jackpot jackpotInProgress = repository.findByInProgress(true);

        if(jackpotInProgress != null) {
            throw new BadRequestException(JACKPOT_IN_PROGRESS);
        }
    }

    private Jackpot saveJackpot(Optional<Bowler> bowler) {
        Jackpot lastJackpot = repository.findTopByOrderByDrawnDateDesc();

        Jackpot jackpot = new Jackpot();
        jackpot.setBowlerId(bowler.get().getId());
        jackpot.setDrawnDate(new Date());
        jackpot.setInProgress(true);
        if(lastJackpot != null) {
            jackpot.setAmount(ticketService.getTotalAmount().add(lastJackpot.getRolloverAmount()));
        } else {
            jackpot.setAmount(ticketService.getTotalAmount());
        }

        return repository.save(jackpot);
    }

    public void updateJackpot(Jackpot jackpot) {
        if(jackpot.getNumberOfPins() < 0) {
            throw new BadRequestException(INVALID_NUMBER_OF_PINS);
        }

        Jackpot currentJackpot = repository.findOne(jackpot.getId());

        if(currentJackpot == null) {
            throw new BadRequestException(NO_JACKPOT_FOUND);
        }

        currentJackpot.setInProgress(false);
        currentJackpot.setNumberOfPins(jackpot.getNumberOfPins());

        if(jackpot.getNumberOfPins() == 10) {
            currentJackpot.setAmountPaid(currentJackpot.getAmount());
            currentJackpot.setRolloverAmount(BigDecimal.ZERO);
        } else {
            BigDecimal rollOverAmount = currentJackpot.getAmount().multiply(BigDecimal.valueOf(0.9));
            currentJackpot.setRolloverAmount(rollOverAmount);
            currentJackpot.setAmountPaid(currentJackpot.getAmount().subtract(rollOverAmount));
        }

        repository.save(currentJackpot);

        new Thread(
            () -> ticketService.deleteTicketsOlderThan(currentJackpot.getDrawnDate())
        ).start();
    }

    public BigDecimal getCurrentJackpotValue() {
        Jackpot jackpot = repository.findTopByOrderByDrawnDateDesc();
        BigDecimal totalAmount = ticketService.getTotalAmount();

        return (jackpot == null) ? totalAmount : totalAmount.add(jackpot.getRolloverAmount());
    }

    public List<Jackpot> findJackpotsByBowlerId(String bowlerId) {
        return repository.findByBowlerId(bowlerId);
    }

    public List<Jackpot> findJackpotsByBowlerEmailAddress(String email) {
        Optional<Bowler> bowler = bowlerService.getByEmail(email);

        if(!bowler.isPresent()) {
            throw new ResourceNotFoundException(String.format(INVALID_BOWLER, email));
        }

        return repository.findByBowlerId(bowler.get().getId());
    }
}
