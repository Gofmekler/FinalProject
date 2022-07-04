package by.maiseichyk.finalproject.controller.filter;

import by.maiseichyk.finalproject.command.CommandType;
import by.maiseichyk.finalproject.command.PagePath;
import by.maiseichyk.finalproject.command.RequestParameter;
import by.maiseichyk.finalproject.command.SessionAttribute;
import by.maiseichyk.finalproject.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static by.maiseichyk.finalproject.command.CommandType.*;
import static by.maiseichyk.finalproject.entity.UserRole.*;

@WebFilter(urlPatterns = "/controller")
public class SecurityRoleAccessFilter implements Filter {
    private static final String DEFAULT_COMMAND = "GO_TO_WELCOME_PAGE";
    private EnumMap<UserRole, List<CommandType>> accessibleCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        accessibleCommands = new EnumMap<>(UserRole.class);
        accessibleCommands.put(ADMIN, List.of(REGISTER, LOGIN, LOGOUT, ADD_USER, UPDATE_USER_INFO, DELETE_USER, ADD_SPORT_EVENT,
                DELETE_SPORT_EVENT, UPDATE_SPORT_EVENT, EVENT_RELEASE, BET, UPDATE_BET, DELETE_BET, ADD_BANK_CARD, UPDATE_BANK_CARD_INFO,
                DELETE_BANK_CARD, WITHDRAW_COMMAND, DEPOSIT_COMMAND, SEARCH_BET, SEARCH_USER, SEARCH_EVENT, GO_TO_ACCOUNT_INFO, GO_TO_ADMIN, GO_TO_BET_LIST,
                GO_TO_BOOKMAKER, GO_TO_CARD_OPERATIONS, GO_TO_DEMO, GO_TO_EVENT_INFO, GO_TO_PAST_EVENT, GO_TO_USER_INFO, GO_TO_USER_OPERATIONS, GO_TO_EVENT,
                GO_TO_SIGN_IN, GO_TO_SIGN_UP, GO_TO_USER_LIST, GO_TO_CONTACTS, GO_TO_LEARN_MORE, GO_TO_HOME_PAGE, GO_TO_WELCOME_PAGE, CHANGE_LOCALE, DEFAULT));
        accessibleCommands.put(BOOKMAKER, List.of(REGISTER, LOGIN, LOGOUT, UPDATE_USER_INFO, UPDATE_SPORT_EVENT, BET, UPDATE_BET, DELETE_BET, ADD_BANK_CARD, UPDATE_BANK_CARD_INFO,
                DELETE_BANK_CARD, WITHDRAW_COMMAND, DEPOSIT_COMMAND, SEARCH_BET, SEARCH_USER, SEARCH_EVENT, GO_TO_ACCOUNT_INFO, GO_TO_ADMIN, GO_TO_BET_LIST,
                GO_TO_BOOKMAKER, GO_TO_DEMO, GO_TO_EVENT_INFO, GO_TO_CONTACTS, GO_TO_PAST_EVENT, GO_TO_USER_INFO, GO_TO_USER_OPERATIONS, GO_TO_EVENT,
                GO_TO_SIGN_IN, GO_TO_SIGN_UP, GO_TO_USER_LIST, GO_TO_LEARN_MORE, GO_TO_HOME_PAGE, GO_TO_WELCOME_PAGE, CHANGE_LOCALE, DEFAULT));
        accessibleCommands.put(CLIENT, List.of(REGISTER, LOGIN, LOGOUT, UPDATE_USER_INFO, BET, UPDATE_BET, DELETE_BET, ADD_BANK_CARD, UPDATE_BANK_CARD_INFO,
                DELETE_BANK_CARD, WITHDRAW_COMMAND, DEPOSIT_COMMAND, SEARCH_BET, SEARCH_USER, SEARCH_EVENT, GO_TO_ACCOUNT_INFO, GO_TO_ADMIN,
                GO_TO_BOOKMAKER, GO_TO_DEMO, GO_TO_EVENT_INFO, GO_TO_CONTACTS, GO_TO_LEARN_MORE, GO_TO_PAST_EVENT, GO_TO_EVENT,
                GO_TO_SIGN_IN, GO_TO_SIGN_UP, GO_TO_HOME_PAGE, GO_TO_WELCOME_PAGE, CHANGE_LOCALE, DEFAULT));
        accessibleCommands.put(GUEST, List.of(REGISTER, LOGIN, LOGOUT, GO_TO_ADMIN,
                GO_TO_BOOKMAKER, GO_TO_DEMO, GO_TO_SIGN_IN, GO_TO_HOME_PAGE, GO_TO_SIGN_UP, GO_TO_CONTACTS, GO_TO_LEARN_MORE, GO_TO_WELCOME_PAGE, CHANGE_LOCALE, DEFAULT));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        UserRole role = UserRole.valueOf(String.valueOf(session.getAttribute(SessionAttribute.ROLE)).toUpperCase());
        String command = request.getParameter(RequestParameter.COMMAND);
        if (command == null) {
            command = DEFAULT_COMMAND;
        }
        CommandType commandType = CommandType.valueOf(command.toUpperCase());
        Optional<CommandType> foundCommandType = accessibleCommands.get(role)
                .stream()
                .filter(c -> c == commandType)
                .findFirst();
        if (foundCommandType.isEmpty()) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_403);
            return;
        }
        chain.doFilter(request, response);
    }
}
