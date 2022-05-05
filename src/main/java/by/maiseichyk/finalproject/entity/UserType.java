package by.maiseichyk.finalproject.entity;

public enum UserType {
    ADMIN,
    GUEST,
    CLIENT,
    BOOKMAKER;

    public String getValue() {
        return this.toString().toLowerCase();
    }
}
