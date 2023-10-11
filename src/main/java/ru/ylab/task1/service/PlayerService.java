package ru.ylab.task1.service;

import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.Player;

import java.util.List;

/**
 * The interface Player service.
 */
public interface PlayerService {

    /**
     * Register player long.
     *
     * @param login    the login
     * @param password the password
     * @return the long
     * @throws LoginExistsException the login exists exception
     */
    Long registerPlayer(String login, String password) throws LoginExistsException;

    /**
     * Gets all history.
     *метод возвращает все действия пользователя
     * @param playerId the player id
     * @return the all history
     */
    List<String> getAllHistory(Long playerId);

    /**
     * Add action to history.
     *
     * @param playerId the player id
     * @param action   the action
     */
    void addActionToHistory(Long playerId, String action);

   Player authorizationPlayer(String login, String password) throws LoginExistsException;

}