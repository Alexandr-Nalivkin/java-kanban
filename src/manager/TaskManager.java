package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    void createNewTask(Task task);

    void createNewEpic(Epic epic);

    void createNewSubtask(Subtask subtask);

    void removeAllTask();

    void removeAllEpic();

    void removeAllSubtask();

    ArrayList<Task> getListAllTask();

    ArrayList<Epic> getListAllEpic();

    ArrayList<Subtask> getListAllSubtask();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    ArrayList<Subtask> getSubtaskByEpicId(int id);

    void updateTask(Task updateTask);

    void updateEpic(Epic updateEpic);

    void updateSubtask(Subtask updateSubtask);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int subtaskIdForRemove);

    ArrayList<Task> getHistory();

    void setId(int i);
}
