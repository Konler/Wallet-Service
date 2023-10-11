package ru.ylab.task1.repository;

import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.Player;

/**
 * The interface Player repository.
 */
public interface PlayerRepository {

    /**
     * Register player.
     *
     * @param player the player
     */
    void registerPlayer(Player player);

    /**
     * Change player balance by id.
     *
     * @param id     the id
     * @param amount the amount
     * @throws ImpossibleTransactionException the impossible transaction exception
     */
    void changePlayerBalanceById(Long id, double amount) throws ImpossibleTransactionException;

    /**
     * Login exists boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean loginExists(String login);

    /**
     * Password exists boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean passwordExists(Integer password);

    /**
     * Find player by id player.
     *
     * @param id the id
     * @return the player
     * @throws LoginExistsException the login exists exception
     */
    Player findPlayerById(Long id)throws LoginExistsException;
     Player playerExist(String login, Integer password);
}
