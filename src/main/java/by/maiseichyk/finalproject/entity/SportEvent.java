package by.maiseichyk.finalproject.entity;

import java.util.Objects;

public class SportEvent extends AbstractEntity {
    private String firstTeam;
    private String secondTeam;
    private SportEventType eventType;
    private String firstTeamRatio;//migrate to double
    private String secondTeamRatio;
    private String event_date;
    private String unique_event_id;

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

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getUnique_event_id() {
        return unique_event_id;
    }

    public void setUnique_event_id(String unique_event_id) {
        this.unique_event_id = unique_event_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportEvent event = (SportEvent) o;
        return firstTeam.equals(event.firstTeam) && secondTeam.equals(event.secondTeam) && eventType == event.eventType && firstTeamRatio.equals(event.firstTeamRatio) && secondTeamRatio.equals(event.secondTeamRatio) && event_date.equals(event.event_date) && unique_event_id.equals(event.unique_event_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTeam, secondTeam, eventType, firstTeamRatio, secondTeamRatio, event_date, unique_event_id);
    }

    @Override
    public String toString() {
        return "SportEvent{" +
                "firstTeam='" + firstTeam + '\'' +
                ", secondTeam='" + secondTeam + '\'' +
                ", eventType=" + eventType +
                ", firstTeamRatio='" + firstTeamRatio + '\'' +
                ", secondTeamRatio='" + secondTeamRatio + '\'' +
                ", event_date='" + event_date + '\'' +
                ", unique_event_id='" + unique_event_id + '\'' +
                '}' + '\n';
    }
}
