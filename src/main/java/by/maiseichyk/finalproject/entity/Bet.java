package by.maiseichyk.finalproject.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Bet extends AbstractEntity {
    private String userLogin;
    private String sportEventId;
    private BetStatus betStatus;
    private BigDecimal betAmount;
    private String chosenTeam;
    private BigDecimal winCoefficient;

    public Bet() {
    }

    public Bet(String userLogin, String sportEventId) {
        this.userLogin = userLogin;
        this.sportEventId = sportEventId;
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

    public BetStatus getBetStatus() {
        return betStatus;
    }

    public String getChosenTeam() {
        return chosenTeam;
    }

    public void setChosenTeam(String chosenTeam) {
        this.chosenTeam = chosenTeam;
    }

    public BigDecimal getWinCoefficient() {
        return winCoefficient;
    }

    public void setWinCoefficient(BigDecimal winCoefficient) {
        this.winCoefficient = winCoefficient;
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

        public BetBuilder setId(int id) {
            bet.setId(id);
            return this;
        }

        public BetBuilder setUserLogin(String userLogin) {
            bet.userLogin = userLogin;
            return this;
        }

        public BetBuilder setWinCoefficient(BigDecimal winCoefficient) {
            bet.winCoefficient = winCoefficient;
            return this;
        }

        public BetBuilder setSportEventId(String sportEventId) {
            bet.sportEventId = sportEventId;
            return this;
        }

        public BetBuilder setChosenTeam(String chosenTeam) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bet bet = (Bet) o;
        return Objects.equals(userLogin, bet.userLogin) && Objects.equals(sportEventId, bet.sportEventId) && betStatus == bet.betStatus && Objects.equals(betAmount, bet.betAmount) && Objects.equals(chosenTeam, bet.chosenTeam) && Objects.equals(winCoefficient, bet.winCoefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userLogin, sportEventId, betStatus, betAmount, chosenTeam, winCoefficient);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "userLogin='" + userLogin + '\'' +
                ", sportEventId='" + sportEventId + '\'' +
                ", betStatus=" + betStatus +
                ", betAmount=" + betAmount +
                ", chosenTeam='" + chosenTeam + '\'' +
                ", winCoefficient=" + winCoefficient +
                '}';
    }
}
