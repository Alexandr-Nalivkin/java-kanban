import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTaskManagerTest {
    private static TaskManager taskManager = Managers.getDefault();

    @BeforeEach
    public void beforeEach() {
        taskManager.setId(0);
        Task task = new Task("testTask", "testDescription");
        taskManager.createNewTask(task);
        Epic epic = new Epic("testEpic", "testDescription");
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("testSubtask", "testDescription", 2);
        taskManager.createNewSubtask(subtask);
    }

    @Test
    void testCreateNewTask() {
        ArrayList<Task> tasks = taskManager.getListAllTask();
        assertEquals(1, tasks.get(0).getId());
    }

    @Test
    void testCreateNewEpic() {
        ArrayList<Epic> epics = taskManager.getListAllEpic();
        assertEquals(2, epics.get(0).getId());
    }

    @Test
    void testCreateNewSubtask() {
        ArrayList<Subtask> subtasks = taskManager.getListAllSubtask();
        assertEquals(3, subtasks.get(0).getId());
    }

    @Test
    void testRemoveAllTask() {
        taskManager.removeAllTask();
        ArrayList<Task> tasks = taskManager.getListAllTask();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testRemoveAllEpic() {
        taskManager.removeAllEpic();
        ArrayList<Epic> epics = taskManager.getListAllEpic();
        assertTrue(epics.isEmpty());
    }

    @Test
    void testRemoveAllSubtask() {
        taskManager.removeAllSubtask();
        ArrayList<Subtask> subtasks = taskManager.getListAllSubtask();
        assertTrue(subtasks.isEmpty());
    }

    @Test
    void testGetTaskById() {
        Task task = taskManager.getTaskById(1);
        assertEquals(1, task.getId());
    }

    @Test
    void testGetEpicById() {
        Epic epic = taskManager.getEpicById(2);
        assertEquals(2, epic.getId());
    }

    @Test
    void testGetSubtaskById() {
        Subtask subtask = taskManager.getSubtaskById(3);
        assertEquals(3, subtask.getId());
    }

    @Test
    void testUpdateTask() {
        Task updateTask1 = new Task("testName0", "testDescription", 1);
        taskManager.updateTask(updateTask1);
        Task task = taskManager.getTaskById(1);
        assertEquals(1, task.getId());
        assertEquals("testName0", task.getName());
    }

    @Test
    void testUpdateEpic() {
        Epic updateEpic = new Epic("testName0", "testDescription", 2);
        taskManager.updateEpic(updateEpic);
        Epic epic = taskManager.getEpicById(2);
        assertEquals(2, epic.getId());
        assertEquals("testName0", epic.getName());
    }

    @Test
    void testUpdateSubtask() {
        Subtask updateSubtask = new Subtask("testName0", "testDescription", 3, TaskStatus.DONE, 2);
        taskManager.updateSubtask(updateSubtask);
        Subtask subtask = taskManager.getSubtaskById(3);
        assertEquals(3, subtask.getId());
        assertEquals("testName0", subtask.getName());
    }

    @Test
    void testRemoveTaskById() {
        taskManager.removeTaskById(1);
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    void testRemoveEpicById() {
        taskManager.removeEpicById(2);
        assertNull(taskManager.getEpicById(2));
        assertNull(taskManager.getSubtaskById(3));
    }

    @Test
    void testRemoveSubtaskById() {
        taskManager.removeSubtaskById(3);
        ArrayList<Integer> subtaskIds = taskManager.getEpicById(2).getSubTaskIds();
        assertNull(taskManager.getSubtaskById(3));
        assertFalse(subtaskIds.contains(3));
    }


    @Test
    void testEpicNotAddToSubtaskHimself() {
        Epic epic = taskManager.getEpicById(2);
        ArrayList<Integer> subtaskIds = epic.getSubTaskIds();
        assertFalse(subtaskIds.contains(epic.getId()));
    }

    @Test
    void testSubtaskNotAddToEpicHimself() {
        Subtask subtask = taskManager.getSubtaskById(3);
        assertNotEquals(subtask.getId(), subtask.getEpicId());
    }

    @Test
    void taskWithSpecifiedIdAndGenerateIdNotConflict() {
        Task taskGenerateId = new Task("testName", "testDescription");
        taskManager.createNewTask(taskGenerateId);
        Task taskSpecifiedId = new Task("testName", "testDescription", 1);
        taskManager.createNewTask(taskSpecifiedId);
        assertNotEquals(taskGenerateId.getId(), taskSpecifiedId.getId());
    }

    @Test
    void taskFieldShouldNotChangeAfterAddInTaskManager() {
        Task task = taskManager.getTaskById(1);
        assertEquals("testTask", task.getName());
        assertEquals("testDescription", task.getDescription());
        assertEquals(1, task.getId());
        assertEquals(TaskStatus.NEW, task.getStatus());
    }
}
