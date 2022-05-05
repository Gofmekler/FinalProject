package by.maiseichyk.finalproject.entity;

public enum SportEventType {
    CYBER,
    FOOTBALL,
    VOLLEYBALL,
    BASKETBALL;

    public String getValue() {
        return this.toString().toLowerCase();
    }
}
