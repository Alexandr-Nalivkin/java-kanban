package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import Tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int newId() {
        return ++id;
    }

    public void createNewTask(Task task) {
        int taskId = newId();
        task.setId(taskId);
        tasks.put(taskId, task);
    }

    public void createNewEpic(Epic epic) {
        int epicId = newId();
        epic.setId(id);
        epics.put(epicId, epic);
    }

    public void createNewSubtask(Subtask subtask) {
        int subtaskId = newId();
        subtask.setId(subtaskId);
        subtasks.put(subtaskId, subtask);
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Эпик с id " + epicId + " не существует.");
        } else {
            ArrayList<Integer> subtaskIds = epics.get(epicId).getSubTaskIds();
            subtaskIds.add(subtaskId);
            checkEpicStatus(epicId);
        }
    }

    public void removeAllTask() {
        tasks.clear();
    }

    public void removeAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtask() {
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
            checkEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    public ArrayList<Task> getListAllTask() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getListAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getListAllSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Subtask> getSubtaskByEpicId(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        ArrayList<Subtask> subtasksByEpicId = new ArrayList<>();
        for (int subtaskId : subtasksId) {
            subtasksByEpicId.add(subtasks.get(subtaskId));
        }
        return subtasksByEpicId;
    }

    public void updateTask(Task updateTask) {
        if (tasks.containsKey(updateTask.getId())) {
            tasks.put(updateTask.getId(), updateTask);
        } else {
            System.out.println("Задачи с id " + updateTask.getId() + " нет в перечне.");
        }
    }

    public void updateEpic(Epic updateEpic) {
        if (epics.containsKey(updateEpic.getId())) {
            epics.put(updateEpic.getId(), updateEpic);
            checkEpicStatus(updateEpic.getId());
        } else {
            System.out.println("Эпика с id " + updateEpic.getId() + " нет в перечне.");
        }
    }

    public void updateSubtask(Subtask updateSubtask) {
        if (subtasks.containsKey(updateSubtask.getId())) {
            subtasks.put(updateSubtask.getId(), updateSubtask);
            int epicId = subtasks.get(updateSubtask.getId()).getEpicId();
            checkEpicStatus(epicId);
        } else {
            System.out.println("Подзадачи с id " + updateSubtask.getId() + " нет в перечне.");
        }
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicById(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        for (Integer subtaskId : subtasksId) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void removeSubtaskById(int subtaskIdForRemove) {
        int epicId = subtasks.get(subtaskIdForRemove).getEpicId();
        getEpicById(epicId).removeSubTaskId(getSubtaskById(subtaskIdForRemove));
        subtasks.remove(subtaskIdForRemove);
        checkEpicStatus(epicId);
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
