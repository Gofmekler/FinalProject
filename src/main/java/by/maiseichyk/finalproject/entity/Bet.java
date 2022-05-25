package by.maiseichyk.finalproject.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Bet extends AbstractEntity {
    private String id;
    private String userLogin;
    private String sportEventId;
    private String betDate; //migrate to dateTime
    private BetStatus betStatus;
    private BigDecimal betAmount; //migrate to big decimal
    private String chosenTeam;

    public Bet() {
    }

    public Bet(String userLogin, String sportEventId) {
        this.userLogin = userLogin;
        this.sportEventId = sportEventId;
    }

    public String getId() {
        return id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getSportEventId() {
        return sportEventId;
    }

    public void setSportEventId(String sportEventId) {
        this.sportEventId = sportEventId;
    }

    public String getBetDate() {
        return betDate;
    }

    public void setBetDate(String betDate) {
        this.betDate = betDate;
    }

    public BetStatus getBetStatus() {
        return betStatus;
    }

    public String getChosenTeam() {
        return chosenTeam;
    }

    public void setChosenTeam(String chosenTeam) {
        this.chosenTeam = chosenTeam;
    }

    public void setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(BigDecimal betAmount) {
        this.betAmount = betAmount;
    }

    public static class BetBuilder {
        private final Bet bet;

        public BetBuilder() {
            bet = new Bet();
        }

        public BetBuilder setId(String id) {
            bet.id = id;
            return this;
        }

        public BetBuilder setUserLogin(String userLogin) {
            bet.userLogin = userLogin;
            return this;
        }

        public BetBuilder setSportEventId(String sportEventId) {
            bet.sportEventId = sportEventId;
            return this;
        }

        public BetBuilder setBetDate(String betDate) {
            bet.betDate = betDate;
            return this;
        }

        public BetBuilder setChosenTeam(String chosenTeam){
            bet.chosenTeam = chosenTeam;
            return this;
        }

        public BetBuilder setBetStatus(BetStatus betStatus) {
            bet.betStatus = betStatus;
            return this;
        }

        public BetBuilder setBetAmount(BigDecimal betAmount) {
            bet.betAmount = betAmount;
            return this;
        }

        public Bet build() {
            return bet;
        }
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id='" + id + '\'' +
                ", user=" + userLogin +
                ", sportEvent=" + sportEventId +
                ", betDate='" + betDate + '\'' +
                ", betStatus=" + betStatus +
                ", betAmount='" + betAmount + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id) && Objects.equals(userLogin, bet.userLogin) && Objects.equals(sportEventId, bet.sportEventId) && Objects.equals(betDate, bet.betDate) && betStatus == bet.betStatus && Objects.equals(betAmount, bet.betAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin, sportEventId, betDate, betStatus, betAmount);
    }
}
