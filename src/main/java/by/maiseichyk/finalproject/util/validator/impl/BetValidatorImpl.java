package by.maiseichyk.finalproject.util.validator.impl;

import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.util.validator.BetValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static by.maiseichyk.finalproject.command.RequestParameter.*;

public class BetValidatorImpl implements BetValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BetValidatorImpl instance = new BetValidatorImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String COEFFICIENT_REGEX = "\\d+.*\\d*";
    private static final String AMOUNT_REGEX = "\\d+";

    private BetValidatorImpl() {
    }

    public static BetValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean checkWinCoefficient(String winCoefficient) {
        return winCoefficient != null && !winCoefficient.isBlank() && winCoefficient.matches(COEFFICIENT_REGEX);
    }

    @Override
    public boolean checkBetStatus(String betStatus) {
        if (betStatus != null && !betStatus.isBlank()) {
            List<BetStatus> betStatusList = Arrays.stream(BetStatus.values()).toList();
            for (BetStatus status : betStatusList) {
                if (status.toString().equals(betStatus)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkBetAmount(String betAmount) {
        return betAmount != null && !betAmount.isBlank() && betAmount.matches(AMOUNT_REGEX);
    }

    @Override
    public boolean checkBetData(Map<String, String> betData) {
        UserValidatorImpl userValidator = UserValidatorImpl.getInstance();
        boolean match = true;
        if (!checkWinCoefficient(betData.get(WIN_COEFFICIENT))) {
            betData.put(WIN_COEFFICIENT, betData.get(WIN_COEFFICIENT) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkBetAmount(betData.get(BET_AMOUNT))) {
            betData.put(BET_AMOUNT, betData.get(BET_AMOUNT) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!checkBetStatus(betData.get(BET_STATUS))) {
            betData.put(BET_STATUS, betData.get(BET_STATUS) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        if (!userValidator.checkLogin(betData.get(LOGIN))) {
            betData.put(LOGIN, betData.get(LOGIN) + INCORRECT_VALUE_PARAMETER);
            match = false;
        }
        LOGGER.info(betData.keySet());
        LOGGER.info(betData.values());
        return match;
    }
}
