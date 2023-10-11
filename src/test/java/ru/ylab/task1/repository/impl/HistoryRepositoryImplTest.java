package ru.ylab.task1.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ylab.task1.model.HistoryItem;
import ru.ylab.task1.repository.HistoryRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryRepositoryImplTest {

    private HistoryRepository historyRepository;
    private Map<Long, HistoryItem> historyMap;

    @BeforeEach
    public void setUp() {
        historyMap = new HashMap<>();
        historyRepository = new HistoryRepositoryImpl(historyMap);
    }

    @Test
    public void testGetPlayerHistoryWhenPlayerIdExistsThenReturnHistoryItems() {

        Long playerId = 1L;
        HistoryItem historyItem1 = new HistoryItem(1L, playerId, "action1", LocalDateTime.now());
        HistoryItem historyItem2 = new HistoryItem(2L, playerId, "action2", LocalDateTime.now());
        historyMap.put(historyItem1.getId(), historyItem1);
        historyMap.put(historyItem2.getId(), historyItem2);


        List<HistoryItem> result = historyRepository.getPlayerHistory(playerId);


        assertEquals(2, result.size());
        assertTrue(result.contains(historyItem1));
        assertTrue(result.contains(historyItem2));
    }

    @Test
    public void testAddHistoryItemWhenCalledThenAddToHistoryMap() {

        HistoryItem historyItem = new HistoryItem(1L, 1L, "action", LocalDateTime.now());


        historyRepository.addHistoryItem(historyItem);


        assertTrue(historyMap.containsKey(historyItem.getId()));
        assertEquals(historyItem, historyMap.get(historyItem.getId()));
    }
}
