package by.maiseichyk.finalproject.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class SportEvent extends AbstractEntity {
    private String firstTeam;
    private String secondTeam;
    private SportEventType eventType;
    private BigDecimal firstTeamRatio;
    private BigDecimal secondTeamRatio;
    private LocalDate eventDate;
    private String uniqueEventId;
    private String eventResult;

    public SportEvent() {
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public SportEventType getEventType() {
        return eventType;
    }

    public void setEventType(SportEventType eventType) {
        this.eventType = eventType;
    }

    public BigDecimal getFirstTeamRatio() {
        return firstTeamRatio;
    }

    public void setFirstTeamRatio(BigDecimal firstTeamRatio) {
        this.firstTeamRatio = firstTeamRatio;
    }

    public BigDecimal getSecondTeamRatio() {
        return secondTeamRatio;
    }

    public void setSecondTeamRatio(BigDecimal secondTeamRatio) {
        this.secondTeamRatio = secondTeamRatio;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getUniqueEventId() {
        return uniqueEventId;
    }

    public void setUniqueEventId(String uniqueEventId) {
        this.uniqueEventId = uniqueEventId;
    }

    public String getEventResult() {
        return eventResult;
    }

    public void setEventResult(String eventResult) {
        this.eventResult = eventResult;
    }

    public static class SportEventBuilder {
        private final SportEvent sportEvent;

        public SportEventBuilder() {
            sportEvent = new SportEvent();
        }

        public SportEventBuilder setFirstTeam(String firstTeam) {
            sportEvent.firstTeam = firstTeam;
            return this;
        }


        public SportEventBuilder setSecondTeam(String secondTeam) {
            sportEvent.secondTeam = secondTeam;
            return this;
        }


        public SportEventBuilder setEventType(SportEventType eventType) {
            sportEvent.eventType = eventType;
            return this;
        }


        public SportEventBuilder setFirstTeamRatio(BigDecimal firstTeamRatio) {
            sportEvent.firstTeamRatio = firstTeamRatio;
            return this;
        }


        public SportEventBuilder setSecondTeamRatio(BigDecimal secondTeamRatio) {
            sportEvent.secondTeamRatio = secondTeamRatio;
            return this;
        }

        public SportEventBuilder setEventDate(LocalDate eventDate) {
            sportEvent.eventDate = eventDate;
            return this;
        }


        public SportEventBuilder setUniqueEventId(String uniqueEventId) {
            sportEvent.uniqueEventId = uniqueEventId;
            return this;

        }

        public SportEventBuilder setEventResult(String eventResult){
            sportEvent.eventResult = eventResult;
            return this;
        }

        public SportEvent build() {
            return sportEvent;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportEvent that = (SportEvent) o;
        return Objects.equals(firstTeam, that.firstTeam) && Objects.equals(secondTeam, that.secondTeam) && eventType == that.eventType && Objects.equals(firstTeamRatio, that.firstTeamRatio) && Objects.equals(secondTeamRatio, that.secondTeamRatio) && Objects.equals(eventDate, that.eventDate) && Objects.equals(uniqueEventId, that.uniqueEventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTeam, secondTeam, eventType, firstTeamRatio, secondTeamRatio, eventDate, uniqueEventId);
    }

    @Override
    public String toString() {
        return "SportEvent{" +
                "firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", eventType=" + eventType +
                ", firstTeamRatio='" + firstTeamRatio + '\'' +
                ", secondTeamRatio='" + secondTeamRatio + '\'' +
                ", event_date='" + eventDate + '\'' +
                ", unique_event_id='" + uniqueEventId + '\'' +
                '}' + '\n';
    }
}
