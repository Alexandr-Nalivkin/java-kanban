import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryHistoryManagerTest {
    HistoryManager historyManager = Managers.getDefaultHistory();

    Task task = new Task("name", "description", 1, TaskStatus.NEW);
    Epic epic = new Epic("name", "description", 2);
    Subtask subtask = new Subtask("name", "description", 3, TaskStatus.NEW, 2);

    @Test
    void testHistoryManagerAddTasksAndGetHistory() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        assertEquals(3, historyManager.getHistory().size());
        assertEquals(1, historyManager.getHistory().get(0).getId());
        assertEquals(2, historyManager.getHistory().get(1).getId());
        assertEquals(3, historyManager.getHistory().get(2).getId());
    }

    @Test
    void testHistoryManagerRemoveDuplicates() {
        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void testHistoryManagerRemoveById() {
        historyManager.add(task);
        historyManager.remove(1);
        historyManager.add(epic);
        historyManager.remove(2);
        assertTrue(historyManager.getHistory().isEmpty());
    }
}
