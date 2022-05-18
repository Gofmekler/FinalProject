package by.maiseichyk.finalproject.command.impl.go;

import by.maiseichyk.finalproject.command.Command;
import by.maiseichyk.finalproject.controller.Router;
import by.maiseichyk.finalproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.maiseichyk.finalproject.command.PagePath.ACCOUNT_INFO;
import static by.maiseichyk.finalproject.controller.Router.Type.FORWARD;

public class GoToAccountInfo implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        LOGGER.info("Go to account info command was called. ");
        return new Router(ACCOUNT_INFO, FORWARD);
    }
}
