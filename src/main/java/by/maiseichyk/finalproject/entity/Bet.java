package by.maiseichyk.finalproject.entity;

import java.util.Objects;

public class Bet extends AbstractEntity {
    private String id;
    private User user;
    private SportEvent sportEvent;
    private String betDate; //migrate to dateTime
    private BetStatus betStatus;
    private String betAmount; //migrate to double

    public Bet() {
    }

    public Bet(User user, SportEvent sportEvent) {
        this.user = user;
        this.sportEvent = sportEvent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SportEvent getSportEvent() {
        return sportEvent;
    }

    public void setSportEvent(SportEvent sportEvent) {
        this.sportEvent = sportEvent;
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

    public void setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
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

        public BetBuilder setUser(User user) {
            bet.user = user;
            return this;
        }

        public BetBuilder setSportEvent(SportEvent sportEvent) {
            bet.sportEvent = sportEvent;
            return this;
        }

        public BetBuilder setBetDate(String betDate) {
            bet.betDate = betDate;
            return this;
        }

        public BetBuilder setBetStatus(BetStatus betStatus) {
            bet.betStatus = betStatus;
            return this;
        }

        public BetBuilder setBetAmount(String betAmount) {
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
                ", user=" + user +
                ", sportEvent=" + sportEvent +
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
        return Objects.equals(id, bet.id) && Objects.equals(user, bet.user) && Objects.equals(sportEvent, bet.sportEvent) && Objects.equals(betDate, bet.betDate) && betStatus == bet.betStatus && Objects.equals(betAmount, bet.betAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, sportEvent, betDate, betStatus, betAmount);
    }
}
