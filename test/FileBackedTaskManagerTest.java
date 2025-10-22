import manager.FileBackedTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileBackedTaskManagerTest {
    private static FileBackedTaskManager manager;
    private File file;
    Task task = new Task("testTask", "testDescription");
    Epic epic = new Epic("testEpic", "testDescription");
    Subtask subtask = new Subtask("testSubtask", "testDescription", 2);

    @BeforeEach
    public void beforeEach() throws IOException {
        file = Files.createTempFile("tests", ".csv").toFile();
        manager = new FileBackedTaskManager(file);
    }

    @Test
    void testLoadEmptyFile() {
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        assertTrue(loadedManager.getListAllTask().isEmpty());
        assertTrue(loadedManager.getListAllEpic().isEmpty());
        assertTrue(loadedManager.getListAllSubtask().isEmpty());
    }

    @Test
    void testSaveAndLoadTasks() {
        manager.createNewTask(task);
        manager.createNewEpic(epic);
        manager.createNewSubtask(subtask);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(task.getName(), loadedManager.getTaskById(task.getId()).getName());
        assertEquals(epic.getName(), loadedManager.getEpicById(epic.getId()).getName());
        assertEquals(subtask.getName(), loadedManager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    void testSaveAfterUpdateTasks() {
        manager.createNewTask(task);
        manager.createNewEpic(epic);
        manager.createNewSubtask(subtask);

        Task updateTask = new Task("testUpdateTask", task.getDescription(), task.getId());
        Epic updateEpic = new Epic("testUpdateEpic", epic.getDescription(), epic.getId());
        Subtask updateSubtask = new Subtask("testUpdateSubtask", subtask.getDescription(), subtask.getId(), TaskStatus.NEW, subtask.getEpicId());

        manager.updateTask(updateTask);
        manager.updateEpic(updateEpic);
        manager.updateSubtask(updateSubtask);

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);

        assertEquals(updateTask.getName(), loadedManager.getTaskById(task.getId()).getName());
        assertEquals(updateEpic.getName(), loadedManager.getEpicById(epic.getId()).getName());
        assertEquals(updateSubtask.getName(), loadedManager.getSubtaskById(subtask.getId()).getName());
    }

    @Test
    void testSaveAfterDeleteTasks() {
        manager.createNewTask(task);
        manager.createNewEpic(epic);
        manager.createNewSubtask(subtask);
        manager.removeTaskById(task.getId());
        manager.removeSubtaskById(subtask.getId());
        manager.removeEpicById(epic.getId());

        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        assertTrue(loadedManager.getListAllTask().isEmpty());
        assertTrue(loadedManager.getListAllEpic().isEmpty());
        assertTrue(loadedManager.getListAllSubtask().isEmpty());
    }
}
