import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryHistoryManagerTest {
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void historyManagerAddTasksAndGetHistory() {
        for (int i = 1; i < 11; i++) {
            Task task = new Task("name" + i, "description" + i);
            historyManager.add(task);
        }
        assertNotNull(historyManager.getHistory());
        assertEquals(1, historyManager.getHistory().get(0).getId());
        assertEquals("name1", historyManager.getHistory().get(0).getName());
        assertEquals(10, historyManager.getHistory().get(9).getId());
        assertEquals("name10", historyManager.getHistory().get(9).getName());
        assertEquals(5, historyManager.getHistory().get(4).getId());
        assertEquals("name5", historyManager.getHistory().get(4).getName());
    }

    @Test
    void historyManagerStoresTheHistoryOfChanges() {
        Task task1 = new Task("name1", "description", 1);
        historyManager.add(task1);
        Task task2 = new Task("name2", "description", 1);
        historyManager.add(task2);
        assertEquals(2, historyManager.getHistory().size());
        assertEquals(1, historyManager.getHistory().get(0).getId());
        assertEquals("name1", historyManager.getHistory().get(0).getName());
        assertEquals(1, historyManager.getHistory().get(1).getId());
        assertEquals("name2", historyManager.getHistory().get(1).getName());
    }
}
