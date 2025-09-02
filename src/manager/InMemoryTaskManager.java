package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createNewTask(Task task) {
        int taskId = newId();
        task.setId(taskId);
        tasks.put(taskId, task);
    }

    @Override
    public void createNewEpic(Epic epic) {
        int epicId = newId();
        epic.setId(id);
        epics.put(epicId, epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Эпик с id " + epicId + " не существует.");
        } else {
            int subtaskId = newId();
            subtask.setId(subtaskId);
            subtasks.put(subtaskId, subtask);
            ArrayList<Integer> subtaskIds = epics.get(epicId).getSubTaskIds();
            subtaskIds.add(subtaskId);
            checkEpicStatus(epicId);
        }
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public void removeAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtask() {
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
            checkEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public ArrayList<Task> getListAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getListAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getListAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public ArrayList<Subtask> getSubtaskByEpicId(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        ArrayList<Subtask> subtasksByEpicId = new ArrayList<>();
        for (int subtaskId : subtasksId) {
            subtasksByEpicId.add(subtasks.get(subtaskId));
        }
        return subtasksByEpicId;
    }

    @Override
    public void updateTask(Task updateTask) {
        if (tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        } else {
            System.out.println("Задачи с id " + updateTask.getId() + " нет в перечне.");
        }
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        if (epics.containsKey(updateEpic.getId())) {
            epics.put(updateEpic.getId(), updateEpic);
            checkEpicStatus(updateEpic.getId());
        } else {
            System.out.println("Эпика с id " + updateEpic.getId() + " нет в перечне.");
        }
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        if (subtasks.containsKey(updateSubtask.getId())) {
            subtasks.put(updateSubtask.getId(), updateSubtask);
            int epicId = subtasks.get(updateSubtask.getId()).getEpicId();
            checkEpicStatus(epicId);
        } else {
            System.out.println("Подзадачи с id " + updateSubtask.getId() + " нет в перечне.");
        }
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        for (Integer subtaskId : subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        if (!subtasks.containsKey(subtaskIdForRemove)) {
            System.out.println("Подзадачи с id " + subtaskIdForRemove + " нет в перечне.");
        } else {
            int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
            getEpicById(epicId).removeSubTaskId(getSubtaskById(subtaskIdForRemove));
            subtasks.remove(subtaskIdForRemove);
            checkEpicStatus(epicId);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    private int newId() {
        return ++id;
    }

    private void checkEpicStatus(int id) {
        int counterNew = 0;
        int counterDone = 0;
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        for (Integer subtaskId : subtasksId) {
            if (subtasks.containsKey(subtaskId)) {
                if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.NEW)) {
                    counterNew++;
                } else if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.DONE)) {
                    counterDone++;
                }
            }
        }
        if (subtasksId.size() == counterNew || subtasksId.isEmpty()) {
            epics.get(id).setStatus(TaskStatus.NEW);
        } else if (subtasksId.size() == counterDone) {
            epics.get(id).setStatus(TaskStatus.DONE);
        } else {
            epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
        }

    }
}
