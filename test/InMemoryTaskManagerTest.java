import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void epicNotAddToSubtaskHimself() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("testName", "testDescription");
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("testName", "testDescription", 1);
        taskManager.createNewSubtask(subtask);
        ArrayList<Integer> subtaskIds = epic.getSubTaskIds();
        assertFalse(subtaskIds.contains(epic.getId()));
    }

    @Test
    void subtaskNotAddToEpicHimself() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("testName", "testDescription");
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("testName", "testDescription", 1);
        taskManager.createNewSubtask(subtask);
        assertNotEquals(subtask.getId(), subtask.getEpicId());
    }

    @Test
    void inMemoryTaskManagerAddAllTypeTaskAndFindTheirById() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("testName", "testDescription");
        Epic epic = new Epic("testName", "testDescription");
        Subtask subtask = new Subtask("testName", "testDescription", 2);
        taskManager.createNewTask(task);
        taskManager.createNewEpic(epic);
        taskManager.createNewSubtask(subtask);
        assertEquals(1, taskManager.getTaskById(1).getId());
        assertEquals(2, taskManager.getEpicById(2).getId());
        assertEquals(3, taskManager.getSubtaskById(3).getId());
    }

    @Test
    void taskWithSpecifiedIdAndGenerateIdNotConflict() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task taskGenerateId = new Task("testName", "testDescription");
        taskManager.createNewTask(taskGenerateId);
        Task taskSpecifiedId = new Task("testName", "testDescription", 1);
        taskManager.createNewTask(taskSpecifiedId);
        assertNotEquals(taskGenerateId.getId(), taskSpecifiedId.getId());
    }

    @Test
    void taskFieldShouldNotChangeAfterAddInTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("t", "d", 1, TaskStatus.NEW);
        taskManager.createNewTask(task);
        assertEquals("t", taskManager.getTaskById(1).getName());
        assertEquals("d", taskManager.getTaskById(1).getDescription());
        assertEquals(1, taskManager.getTaskById(1).getId());
        assertEquals(TaskStatus.NEW, taskManager.getTaskById(1).getStatus());
    }
}