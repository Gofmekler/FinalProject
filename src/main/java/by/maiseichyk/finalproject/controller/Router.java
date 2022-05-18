package by.maiseichyk.finalproject.controller;

import static by.maiseichyk.finalproject.command.PagePath.WELCOME;

public class Router {
    private String page = WELCOME;
    private Type type = Type.FORWARD;

    public enum Type {
        FORWARD, REDIRECT
    }

    public Router() {
    }

    public Router(String page, Type type) {
        if (page != null || type != null) {
            this.page = page;
            this.type = type;
        }
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setType() {
        this.type = Type.REDIRECT;
    }

    public Type getType() {
        return type;
    }
}
