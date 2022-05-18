package by.maiseichyk.finalproject.entity;

import java.util.Objects;

public class SportEvent extends AbstractEntity {
    private String firstTeam;
    private String secondTeam;
    private SportEventType eventType;
    private String firstTeamRatio;//migrate to double
    private String secondTeamRatio;
    private String eventDate;
    private String uniqueEventId;

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

    public String getFirstTeamRatio() {
        return firstTeamRatio;
    }

    public void setFirstTeamRatio(String firstTeamRatio) {
        this.firstTeamRatio = firstTeamRatio;
    }

    public String getSecondTeamRatio() {
        return secondTeamRatio;
    }

    public void setSecondTeamRatio(String secondTeamRatio) {
        this.secondTeamRatio = secondTeamRatio;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getUniqueEventId() {
        return uniqueEventId;
    }

    public void setUniqueEventId(String uniqueEventId) {
        this.uniqueEventId = uniqueEventId;
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


        public SportEventBuilder setFirstTeamRatio(String firstTeamRatio) {
            sportEvent.firstTeamRatio = firstTeamRatio;
            return this;
        }


        public SportEventBuilder setSecondTeamRatio(String secondTeamRatio) {
            sportEvent.secondTeamRatio = secondTeamRatio;
            return this;
        }

        public SportEventBuilder setEventDate(String eventDate) {
            sportEvent.eventDate = eventDate;
            return this;
        }


        public SportEventBuilder setUniqueEventId(String uniqueEventId) {
            sportEvent.uniqueEventId = uniqueEventId;
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
