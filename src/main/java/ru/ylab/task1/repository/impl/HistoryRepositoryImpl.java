package ru.ylab.task1.repository.impl;

import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.repository.HistoryRepository;

import java.util.List;
import java.util.Map;

/**
 * The type History repository.
 */
public class HistoryRepositoryImpl implements HistoryRepository {

    private final Map<Long, HistoryItem> historyMap;

    /**
     * Instantiates a new History repository.
     *
     * @param historyMap the history map
     */
    public HistoryRepositoryImpl(Map<Long, HistoryItem> historyMap) {
        this.historyMap = historyMap;
    }

    @Override
    public List<HistoryItem> getPlayerHistory(Long playerId) {
        return historyMap.values().stream().toList();
    }

    /**
     * Добавление истории в хранилище с ключем id и значение HistoryItem
     *
     * @param historyItem the history item
     */
    @Override
    public void addHistoryItem(HistoryItem historyItem) {
        historyMap.put(historyItem.getId(), historyItem);
    }
}
