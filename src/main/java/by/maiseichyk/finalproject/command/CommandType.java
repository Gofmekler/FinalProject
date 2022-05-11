package by.maiseichyk.finalproject.command;

import by.maiseichyk.finalproject.command.goTo.*;
import by.maiseichyk.finalproject.command.impl.DefaultCommand;
import by.maiseichyk.finalproject.command.impl.LoginCommand;
import by.maiseichyk.finalproject.command.impl.LogoutCommand;
import by.maiseichyk.finalproject.command.impl.RegisterCommand;
import by.maiseichyk.finalproject.command.adminCommand.AddSportEventCommand;
import by.maiseichyk.finalproject.command.adminCommand.DeleteSportEventCommand;
import by.maiseichyk.finalproject.command.adminCommand.UpdateSportEventCommand;
import by.maiseichyk.finalproject.command.adminCommand.AddUserCommand;
import by.maiseichyk.finalproject.command.adminCommand.UpdateUserInfoCommand;
import by.maiseichyk.finalproject.command.adminCommand.DeleteUserCommand;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ADD_USER_COMMAND(new AddUserCommand()),
    UPDATE_USER_INFO_COMMAND(new UpdateUserInfoCommand()),
    DELETE_USER_COMMAND(new DeleteUserCommand()),
    ADD_SPORT_EVENT_COMMAND(new AddSportEventCommand()),
    DELETE_SPORT_EVENT_COMMAND(new DeleteSportEventCommand()),
    UPDATE_SPORT_EVENT_COMMAND(new UpdateSportEventCommand()),
    GO_TO_ACCOUNT_INFO(new GoToAccountInfo()),
    GO_TO_EVENT(new GoToEvent()),
    GO_TO_SIGN_IN(new GoToSignIn()),
    GO_TO_SIGN_UP(new GoToSignUp()),
    GO_TO_USER_LIST(new GoToUserList()),
    GO_TO_HOME_PAGE(new GoToHomePage()),
    DEFAULT(new DefaultCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        CommandType current = CommandType.valueOf(commandStr.toUpperCase());
        return current.command;
    }
}
