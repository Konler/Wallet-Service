package ru.ylab.task1.repository.impl;

import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.repository.PlayerRepository;

import java.util.Map;

/**
 * The type Player repository.
 */
public class PlayerRepositoryImpl implements PlayerRepository {

    private Map<Long, Player> playerMap;

    /**
     * Instantiates a new Player repository.
     *
     * @param playerMap the player map
     */
    public PlayerRepositoryImpl(Map<Long, Player> playerMap) {

        this.playerMap = playerMap;
    }

    public Player findPlayerById(Long id) throws LoginExistsException {
        if (playerMap.containsKey(id)) {
            return playerMap.get(id);
        }
        throw new LoginExistsException();
    }


    public void registerPlayer(Player player) {
        playerMap.put(player.getId(), player);
    }

    /**
     * Player exist.
     * проверка логина и пароля, если все ок то возврат плеера
     *
     * @param login    the login
     * @param password the password
     */
    public Player playerExist(String login, Integer password) {
        return playerMap.values().stream().filter(x -> x.getLogin().equals(login)).findFirst().orElseThrow(RuntimeException::new);
    }

    public boolean loginExists(String login) {
        return playerMap.values().stream().map(x -> login.equals(x.getLogin())).findFirst().isPresent();
    }

    public boolean passwordExists(Integer password) {
        return playerMap.values().stream().map(x -> password.equals(x.getPassword())).findFirst().isPresent();
    }

    public void changePlayerBalanceById(Long id, double amount) throws ImpossibleTransactionException {
        Player player = playerMap.get(id);
        if (player.getBalance() + amount > 0) {
            player.setBalance(player.getBalance() + amount);
        } else {
            throw new ImpossibleTransactionException("Транзакция невозможна");
        }
    }


}
