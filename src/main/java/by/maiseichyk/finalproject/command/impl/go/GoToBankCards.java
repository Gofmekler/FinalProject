package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToBankCards implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
