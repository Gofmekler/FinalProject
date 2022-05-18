package by.maiseichyk.finalproject.mapper.impl;

import by.maiseichyk.finalproject.entity.Bet;
import by.maiseichyk.finalproject.entity.bet;
import by.maiseichyk.finalproject.entity.betType;
import by.maiseichyk.finalproject.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class BetMapper implements Mapper {
    @Override
    public List retrieve(ResultSet resultSet) throws SQLException {
        List<Bet> betList = new ArrayList<>();
        while(resultSet.next()){
            Bet bet = new Bet.BetBuilder()
                    .setId(resultSet.getString(BET_ID))
                    .setUser(resultSet.getString())// TODO: 18.05.2022
                    .setName(resultSet.getString(bet_NAME))
                    .setLastName(resultSet.getString(bet_LASTNAME))
                    .setEmail(resultSet.getString(bet_EMAIL))
                    .setbetRole(betType.valueOf(resultSet.getString(bet_ROLE)))
                    .build();
            betList.add(bet);
        }
        return betList;
    }
}
