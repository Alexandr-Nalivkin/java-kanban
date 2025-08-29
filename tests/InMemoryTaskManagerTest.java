import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    Task task1 = new Task("testName1", "testDescription1", 1);
    Task task2 = new Task("testName2", "testDescription2", 1);
    Epic epic1 = new Epic("testName1", "testDescription1", 1);
    Epic epic2 = new Epic("testName2", "testDescription2", 1);
    Subtask subtask1 = new Subtask("testName1", "testDescription1", 1, TaskStatus.NEW, 1);
    Subtask subtask2 = new Subtask("testName1", "testDescription1", 1, TaskStatus.NEW, 1);

    @Test
    void TasksWithSameIdAreEqual() {
        assertEquals(task1, task2);
    }

    @Test
    void EpicsWithSameIdAreEqual() {
        assertEquals(epic1, epic2);
    }

    @Test
    void SubtasksWithSameIdAreEqual() {
        assertEquals(subtask1, subtask2);
    }

    @Test
    void EpicNotAddToSubtaskHimself() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("testName", "testDescription");
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("testName", "testDescription", 1);
        taskManager.createNewSubtask(subtask);
        ArrayList<Integer> subtaskIds = epic.getSubTaskIds();
        assertFalse(subtaskIds.contains(epic.getId()));
    }

    @Test
    void SubtaskNotAddToEpicHimself() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Epic epic = new Epic("testName", "testDescription");
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("testName", "testDescription", 1);
        taskManager.createNewSubtask(subtask);
        assertNotEquals(subtask.getId(), subtask.getEpicId());
    }

    @Test
    void ManagersReturnInitialisedManager() {
        assertNotNull(Managers.getDefault());
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void InMemoryTaskManagerAddAllTypeTaskAndFindTheirById() {
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
    void TaskWithSpecifiedIdAndGenerateIdNotConflict() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task taskGenerateId = new Task ("testName", "testDescription");
        taskManager.createNewTask(taskGenerateId);
        Task taskSpecifiedId = new Task("testName", "testDescription", 1);
        taskManager.createNewTask(taskSpecifiedId);
        assertNotEquals(taskGenerateId.getId(), taskSpecifiedId.getId());
    }

    @Test
    void TaskFieldShouldNotChangeAfterAddInTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("t", "d", 1, TaskStatus.NEW);
        taskManager.createNewTask(task);
        assertEquals("t", taskManager.getTaskById(1).getName());
        assertEquals("d", taskManager.getTaskById(1).getDescription());
        assertEquals(1, taskManager.getTaskById(1).getId());
        assertEquals(TaskStatus.NEW, taskManager.getTaskById(1).getStatus());
    }

    @Test
    void HistoryManagerAddTasksAndGetHistory() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        for (int i = 1; i < 11; i++) {
            Task task = new Task("name" + i, "description" + i);
            taskManager.createNewTask(task);
            taskManager.getTaskById(i);
        }
        assertNotNull(taskManager.getHistory());
        assertEquals(1, taskManager.getHistory().get(0).getId());
        assertEquals("name1", taskManager.getHistory().get(0).getName());
        assertEquals(10, taskManager.getHistory().get(9).getId());
        assertEquals("name10", taskManager.getHistory().get(9).getName());
        assertEquals(5, taskManager.getHistory().get(4).getId());
        assertEquals("name5", taskManager.getHistory().get(4).getName());
    }

    @Test
    void HistoryManagerStoresTheHistoryOfChanges() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("name", "description");
        taskManager.createNewTask(task);
        taskManager.getTaskById(1);
        Task updateTask = new Task("notName", "description", task.getId());
        taskManager.updateTask(updateTask);
        taskManager.getTaskById(1);
        assertEquals(1, taskManager.getHistory().get(0).getId());
        assertEquals("name", taskManager.getHistory().get(0).getName());
        assertEquals(1, taskManager.getHistory().get(1).getId());
        assertEquals("notName", taskManager.getHistory().get(1).getName());
    }
}