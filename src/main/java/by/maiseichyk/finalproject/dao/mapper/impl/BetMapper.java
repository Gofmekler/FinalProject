package by.maiseichyk.finalproject.dao.mapper.impl;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.BetStatus;
import by.maiseichyk.finalproject.dao.mapper.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class BetMapper implements Mapper {
    private static final BetMapper instance = new BetMapper();

    private BetMapper() {
    }

    public static BetMapper getInstance() {
        return instance;
    }

    @Override
    public List<Bet> retrieve(ResultSet resultSet) throws SQLException {
        List<Bet> betList = new ArrayList<>();
        while (resultSet.next()) {
            Bet bet = new Bet.BetBuilder()
                    .setId(resultSet.getString(BET_ID))
                    .setUserLogin(resultSet.getString(BET_USER_LOGIN))
                    .setSportEventId(resultSet.getString(BET_EVENT_ID))
                    .setBetDate(resultSet.getString(BET_DATE))
                    .setBetAmount(resultSet.getBigDecimal(BET_AMOUNT))
                    .setBetStatus(BetStatus.valueOf(resultSet.getString(BET_STATUS)))
                    .setChosenTeam(resultSet.getString(CHOSEN_TEAM))
                    .setWinCoefficient(resultSet.getBigDecimal(WIN_COEFFICIENT))
                    .build();
            betList.add(bet);
        }
        return betList;
    }
}
