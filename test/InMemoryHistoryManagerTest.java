import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

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
        assertEquals(List.of(task, epic, subtask), historyManager.getHistory());
    }

    @Test
    void testHistoryManagerRemoveDuplicates() {
        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void testHistoryManagerRemoveFromBeginning() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());
        assertEquals(List.of(epic, subtask), historyManager.getHistory());

    }

    @Test
    void testHistoryManagerRemoveFromMiddle() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(2);
        assertEquals(2, historyManager.getHistory().size());
        assertEquals(List.of(task, subtask), historyManager.getHistory());
    }

    @Test
    void testHistoryManagerRemoveFromEnd() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.remove(3);
        assertEquals(2, historyManager.getHistory().size());
        assertEquals(List.of(task, epic), historyManager.getHistory());
    }
}
