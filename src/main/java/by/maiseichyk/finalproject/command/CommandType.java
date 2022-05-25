package by.maiseichyk.finalproject.command;

import by.maiseichyk.finalproject.command.impl.bet.BetCommand;
import by.maiseichyk.finalproject.command.impl.go.*;
import by.maiseichyk.finalproject.command.impl.DefaultCommand;
import by.maiseichyk.finalproject.command.impl.LoginCommand;
import by.maiseichyk.finalproject.command.impl.LogoutCommand;
import by.maiseichyk.finalproject.command.impl.RegisterCommand;
import by.maiseichyk.finalproject.command.impl.admin.AddSportEventCommand;
import by.maiseichyk.finalproject.command.impl.admin.DeleteSportEventCommand;
import by.maiseichyk.finalproject.command.impl.admin.UpdateSportEventCommand;
import by.maiseichyk.finalproject.command.impl.admin.AddUserCommand;
import by.maiseichyk.finalproject.command.impl.admin.UpdateUserInfoCommand;
import by.maiseichyk.finalproject.command.impl.admin.DeleteUserCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ADD_USER(new AddUserCommand()),
    UPDATE_USER_INFO(new UpdateUserInfoCommand()),
    DELETE_USER(new DeleteUserCommand()),
    ADD_SPORT_EVENT(new AddSportEventCommand()),
    DELETE_SPORT_EVENT(new DeleteSportEventCommand()),
    UPDATE_SPORT_EVENT(new UpdateSportEventCommand()),
    BET(new BetCommand()),
    GO_TO_ACCOUNT_INFO(new GoToAccountInfoCommand()),
    GO_TO_EVENT(new GoToEventCommand()),
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    GO_TO_USER_LIST(new GoToUserListCommand()),
    GO_TO_HOME_PAGE(new GoToHomePageCommand()),
    GO_TO_WELCOME_PAGE(new GoToWelcomePageCommand()),
    DEFAULT(new DefaultCommand());
    Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandType) {
        CommandType current = DEFAULT;
        if (commandType != null) {
            try {
                current = CommandType.valueOf(commandType.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.error("Error has occurred while defining command: ", e);
                return DEFAULT.command;
            }
        }
        return current.command;
    }
}
