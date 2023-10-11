package ru.ylab.task1;

import ru.ylab.task1.exception.LoginExistsException;
import ru.ylab.task1.in.InputHandler;
import ru.ylab.task1.in.impl.ScannerInputHandler;
import ru.ylab.task1.model.Player;
import ru.ylab.task1.model.transaction.TransactionType;
import ru.ylab.task1.out.OutputHandler;
import ru.ylab.task1.out.impl.ConsoleOutputHandler;
import ru.ylab.task1.repository.HistoryRepository;
import ru.ylab.task1.repository.PlayerRepository;
import ru.ylab.task1.repository.TransactionRepository;
import ru.ylab.task1.repository.impl.HistoryRepositoryImpl;
import ru.ylab.task1.repository.impl.PlayerRepositoryImpl;
import ru.ylab.task1.repository.impl.TransactionRepositoryImpl;
import ru.ylab.task1.service.PlayerService;
import ru.ylab.task1.service.impl.PlayerServiceImpl;
import ru.ylab.task1.service.impl.WalletServiceImpl;
import ru.ylab.task1.util.CommandProcessor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type App.
 * Первый цикл просит пользователя зарегестрироваться, и пока он не будет зарегестрирован,то программа не пропустит к транзакциям.
 * Второй цикл бесконечо запрашивает необходимые операции для выполнения у пользователя с консоли.
 * Если такая команда есть, то записывает действие в историю.
 */
public class App {

    public static void main(String[] args) throws LoginExistsException {
        Map<String, String> actions = new HashMap<>();
        actions.put("1", "Кредит (пополнение)");
        actions.put("2", "Дебит (списание)");
        actions.put("3", "Просмотр истории транзакций");
        actions.put("4", "Просмотр аудита");
        PlayerRepository playerRepository = new PlayerRepositoryImpl(new HashMap<>());
        TransactionRepository transactionRepository = new TransactionRepositoryImpl(new LinkedHashMap<>());
        HistoryRepository historyRepository = new HistoryRepositoryImpl(new LinkedHashMap<>());
        WalletServiceImpl wallerService = new WalletServiceImpl(transactionRepository, playerRepository);
        PlayerService playerService = new PlayerServiceImpl(playerRepository, historyRepository);
        InputHandler inputHandler = new ScannerInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();
        CommandProcessor commandProcessor = new CommandProcessor(inputHandler, outputHandler, wallerService, playerService);
        while (true) {
            outputHandler.displayMessage("1-Регистрация;2-авторизация");
            String commandRegisterOrAuthorization = inputHandler.getUserInput();
            Long playerId = -1L;
            if (commandRegisterOrAuthorization.equals("1")) {

                while (playerId == -1) {
                    playerId = commandProcessor.registerNewPlayer();
                    executeCommands(commandProcessor, actions, playerService, playerId, outputHandler);
                }
            } else {
                Player p = commandProcessor.authorizationPlayer();
                if (p != null) {
                    executeCommands(commandProcessor, actions, playerService, playerId, outputHandler);
                }
            }
        }


    }

    static void executeCommands(CommandProcessor commandProcessor, Map<String, String> actions, PlayerService playerService, Long playerId, OutputHandler outputHandler) {
        while (true) {
            String command = commandProcessor.readCommand();
            if (actions.containsKey(command)) {
                playerService.addActionToHistory(playerId, actions.get(command));
            }
            switch (command) {
                case "1" -> commandProcessor.processChangeBalanceCommand(TransactionType.CREDIT, playerId);
                case "2" -> commandProcessor.processChangeBalanceCommand(TransactionType.DEBIT, playerId);
                case "3" -> commandProcessor.displayAllTransactions(playerId);
                case "4" -> commandProcessor.displayAllHistory(playerId);
                case "0" -> {
                    outputHandler.displayMessage("Выход из системы");
                    return;
                }
                default -> outputHandler.displayMessage("Неверная команда");
            }
        }
    }
}


