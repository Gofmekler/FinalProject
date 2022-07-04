package by.maiseichyk.finalproject.util.validator;

import java.util.Map;

/**
 * @author Maiseichyk
 * The interface Bet validator.
 * @project Totalizator
 */
public interface BetValidator {
    /**
     * @param winCoefficient the win coefficient
     * @return boolean
     */
    boolean checkWinCoefficient(String winCoefficient);

    /**
     * @param betStatus the bet status
     * @return boolean
     */
    boolean checkBetStatus(String betStatus);

    /**
     * @param betAmount the bet amount
     * @return boolean
     */
    boolean checkBetAmount(String betAmount);

    /**
     * @param betData the bet data
     * @return boolean
     */
    boolean checkBetData(Map<String, String> betData);
}
