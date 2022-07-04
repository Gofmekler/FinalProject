package by.maiseichyk.finalproject.command;

import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
/**
 * @project Totalizator
 * @author Maiseihcyk
 * The interface Command.
 */
@FunctionalInterface
public interface Command {/**
 * Execute router.
 *
 * @param request the request
 * @return the router
 */
    Router execute(HttpServletRequest request) throws CommandException;
}
