package by.maiseichyk.finalproject.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class BankCard extends AbstractEntity {
    private long cardNumber;
    private LocalDate expirationDate;
    private String ownerName;
    private int cvvNumber;
    private BigDecimal balance;

    public BankCard() {
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getCvvNumber() {
        return cvvNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "cardNumber=" + cardNumber +
                ", expirationDate=" + expirationDate +
                ", ownerName='" + ownerName + '\'' +
                ", cvvNumber=" + cvvNumber +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return cardNumber == bankCard.cardNumber && cvvNumber == bankCard.cvvNumber && Objects.equals(expirationDate, bankCard.expirationDate) && Objects.equals(ownerName, bankCard.ownerName) && Objects.equals(balance, bankCard.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, expirationDate, ownerName, cvvNumber, balance);
    }

    public static class BankCardBuilder {
        private final BankCard bankCard;

        public BankCardBuilder() {
            bankCard = new BankCard();
        }

        public BankCardBuilder setCardNumber(long cardNumber) {
            bankCard.cardNumber = cardNumber;
            return this;
        }

        public BankCardBuilder setExpirationDate(LocalDate expirationDate) {
            bankCard.expirationDate = expirationDate;
            return this;
        }

        public BankCardBuilder setOwnerName(String ownerName) {
            bankCard.ownerName = ownerName;
            return this;
        }

        public BankCardBuilder setCvvNumber(int cvvNumber) {
            bankCard.cvvNumber = cvvNumber;
            return this;
        }

        public BankCardBuilder setBalance(BigDecimal balance) {
            bankCard.balance = balance;
            return this;
        }

        public BankCard build() {
            return bankCard;
        }
    }
}
