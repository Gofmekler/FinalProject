package by.maiseichyk.finalproject.dao.mapper.impl;

import by.maiseichyk.finalproject.dao.mapper.Mapper;
import by.maiseichyk.finalproject.entity.BankCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.maiseichyk.finalproject.dao.ColumnName.*;

public class BankCardMapper implements Mapper<BankCard> {
    private static final BankCardMapper instance = new BankCardMapper();

    private BankCardMapper() {
    }

    public static BankCardMapper getInstance() {
        return instance;
    }

    @Override
    public List<BankCard> retrieve(ResultSet resultSet) throws SQLException {
        List<BankCard> cardList = new ArrayList<>();
        while (resultSet.next()) {
            BankCard card = new BankCard.BankCardBuilder()
                    .setCardNumber(resultSet.getLong(CARD_NUMBER))
                    .setBalance(resultSet.getBigDecimal(CARD_BALANCE))
                    .setCvvNumber(resultSet.getInt(CVV_NUMBER))
                    .setExpirationDate(LocalDate.parse(resultSet.getString(EXPIRATION_DATE)))
                    .setOwnerName(resultSet.getString(OWNER_NAME))
                    .build();
            cardList.add(card);
        }
        return cardList;
    }
}
