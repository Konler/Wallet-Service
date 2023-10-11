package ru.ylab.task1.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylab.task1.exception.ImpossibleTransactionException;
import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.model.Player;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerRepositoryImplTest {

    private PlayerRepositoryImpl playerRepository;
    private Map<Long, Player> playerMap;

    @BeforeEach
    public void setUp() {
        playerMap = new HashMap<>();
        playerRepository = new PlayerRepositoryImpl(playerMap);
    }

    @Test
    public void testFindPlayerByIdWhenPlayerExistsThenReturnPlayer() throws LoginExistsException {
        Long playerId = 1L;
        Player player = new Player(playerId, "test", 1234);
        playerMap.put(playerId, player);

        Player result = playerRepository.findPlayerById(playerId);

        assertEquals(player, result, "The returned player should be the same as the one in the player map");
    }

    @Test
    public void testFindPlayerByIdWhenPlayerDoesNotExistThenThrowException() {
        Long playerId = 1L;

        assertThrows(LoginExistsException.class, () -> playerRepository.findPlayerById(playerId),
                "LoginExistsException should be thrown when the player with the given id does not exist in the player map");
    }

    @Test
    public void testRegisterPlayer() {
        Long playerId = 1L;
        Player player = new Player(playerId, "test", 1234);

        playerRepository.registerPlayer(player);

        assertTrue(playerMap.containsKey(playerId), "The player map should contain the registered player");
    }

    @Test
    public void testLoginExistsWhenLoginExistsThenReturnTrue() {
        Long playerId = 1L;
        String login = "test";
        Player player = new Player(playerId, login, 1234);
        playerMap.put(playerId, player);

        boolean result = playerRepository.loginExists(login);

        assertTrue(result, "The method should return true when the login exists in the player map");
    }

    @Test
    public void testLoginExistsWhenLoginDoesNotExistThenReturnFalse() {
        String login = "test";

        boolean result = playerRepository.loginExists(login);

        assertFalse(result, "The method should return false when the login does not exist in the player map");
    }

    @Test
    public void testChangePlayerBalanceByIdWhenTransactionIsPossibleThenChangeBalance() throws ImpossibleTransactionException {
        Long playerId = 1L;
        double initialBalance = 100;
        double amount = 50;
        Player player = new Player(playerId, "test", 1234);
        player.setBalance(initialBalance);
        playerMap.put(playerId, player);

        playerRepository.changePlayerBalanceById(playerId, amount);

        assertEquals(initialBalance + amount, player.getBalance(), "The player's balance should be changed by the given amount");
    }

    @Test
    public void testChangePlayerBalanceByIdWhenTransactionIsImpossibleThenThrowException() {
        Long playerId = 1L;
        double initialBalance = 100;
        double amount = -200;
        Player player = new Player(playerId, "test", 1234);
        player.setBalance(initialBalance);
        playerMap.put(playerId, player);

        assertThrows(ImpossibleTransactionException.class, () -> playerRepository.changePlayerBalanceById(playerId, amount),
                "ImpossibleTransactionException should be thrown when the transaction is impossible");
    }
}