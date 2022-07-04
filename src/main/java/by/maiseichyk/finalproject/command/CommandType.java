package by.maiseichyk.finalproject.command;

import by.maiseichyk.finalproject.command.impl.*;
import by.maiseichyk.finalproject.command.impl.bet.DeleteBetCommand;
import by.maiseichyk.finalproject.command.impl.bet.UpdateBetCommand;
import by.maiseichyk.finalproject.command.impl.card.*;
import by.maiseichyk.finalproject.command.impl.user.*;
import by.maiseichyk.finalproject.command.impl.bet.BetCommand;
import by.maiseichyk.finalproject.command.impl.event.AddSportEventCommand;
import by.maiseichyk.finalproject.command.impl.event.DeleteSportEventCommand;
import by.maiseichyk.finalproject.command.impl.event.SportEventReleaseCommand;
import by.maiseichyk.finalproject.command.impl.event.UpdateSportEventCommand;
import by.maiseichyk.finalproject.command.impl.go.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @project Totalizator
 * @author Maiseichyk
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The add bank card
     */
    ADD_BANK_CARD(new AddBankCardCommand()),
    /**
     * The add sport event
     */
    ADD_SPORT_EVENT(new AddSportEventCommand()),
    /**
     * The add user
     */
    ADD_USER(new AddUserCommand()),
    /**
     * The bet
     */
    BET(new BetCommand()),
    /**
     * The change locale
     */
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    /**
     * The default
     */
    DEFAULT(new DefaultCommand()),
    /**
     * The delete card
     */
    DELETE_BANK_CARD(new DeleteBankCardCommand()),
    /**
     * The delete bet
     */
    DELETE_BET(new DeleteBetCommand()),
    /**
     * The delete sport event
     */
    DELETE_SPORT_EVENT(new DeleteSportEventCommand()),
    /**
     * The delete user
     */
    DELETE_USER(new DeleteUserCommand()),
    /**
     * The deposit
     */
    DEPOSIT_COMMAND(new DepositCommand()),
    /**
     * The event release
     */
    EVENT_RELEASE(new SportEventReleaseCommand()),
    /**
     * The go to account info
     */
    GO_TO_ACCOUNT_INFO(new GoToAccountInfoCommand()),
    /**
     * The go to admin list
     */
    GO_TO_ADMIN(new GoToAdminCommand()),
    /**
     * The go to bet list
     */
    GO_TO_BET_LIST(new GoToBetListCommand()),
    /**
     * The go to bookmaker list
     */
    GO_TO_BOOKMAKER(new GoToBookmakerCommand()),
    /**
     * The go to card operations
     */
    GO_TO_CARD_OPERATIONS(new GoToCardOperationCommand()),
    /**
     * The go to contacts
     */
    GO_TO_CONTACTS(new GoToContactsCommand()),
    /**
     * The go to demo
     */
    GO_TO_DEMO(new GoToDemoCommand()),
    /**
     * The go to event
     */
    GO_TO_EVENT(new GoToEventCommand()),
    /**
     * The go to event info
     */
    GO_TO_EVENT_INFO(new GoToEventInformationCommand()),
    /**
     * The go to home
     */
    GO_TO_HOME_PAGE(new GoToHomePageCommand()),
    /**
     * The go to learn more
     */
    GO_TO_LEARN_MORE(new GoToLearnMoreCommand()),
    /**
     * The go to past event
     */
    GO_TO_PAST_EVENT(new GoToPastEventCommand()),
    /**
     * The go to sign in
     */
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    /**
     * The go to sign up
     */
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    /**
     * The go to user info
     */
    GO_TO_USER_INFO(new GoToUserInformationCommand()),
    /**
     * The go to user list
     */
    GO_TO_USER_LIST(new GoToUserListCommand()),
    /**
     * The go to user operations
     */
    GO_TO_USER_OPERATIONS(new GoToUserOperationCommand()),
    /**
     * The go to welcome
     */
    GO_TO_WELCOME_PAGE(new GoToWelcomePageCommand()),
    /**
     * The login
     */
    LOGIN(new LoginCommand()),
    /**
     * The logout
     */
    LOGOUT(new LogoutCommand()),
    /**
     * The register
     */
    REGISTER(new RegisterCommand()),
    /**
     * The search bet
     */
    SEARCH_BET(new SearchBetCommand()),
    /**
     * The search event
     */
    SEARCH_EVENT(new SearchSportEventCommand()),
    /**
     * The search user
     */
    SEARCH_USER(new SearchUserCommand()),
    /**
     * The update bank card info
     */
    UPDATE_BANK_CARD_INFO(new UpdateBankCardCommand()),
    /**
     * The update bet
     */
    UPDATE_BET(new UpdateBetCommand()),
    /**
     *
     */
    UPDATE_SPORT_EVENT(new UpdateSportEventCommand()),
    /**
     * The update user info
     */
    UPDATE_USER_INFO(new UpdateUserInfoCommand()),
    /**
     * The withdrawal
     */
    WITHDRAW_COMMAND(new WithdrawCommand());
    private final Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command define(String commandType) {
        if (commandType == null || commandType.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandType.toUpperCase()).getCommand();
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Error has occurred while defining command: " + exception);
            return CommandType.DEFAULT.getCommand();
        }
    }
}
