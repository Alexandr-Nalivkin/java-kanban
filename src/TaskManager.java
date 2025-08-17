import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int id = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int newId() {
        id += 1;
        return id;
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
        ArrayList<Integer> subtaskIds = epics.get(epicId).getSubTaskIds();
        subtaskIds.add(subtaskId);
        checkEpicStatus(epicId);
    }

    public void removeAllTask() {
        tasks.clear();
    }

    public void removeAllEpic() {
        for (Epic epic : epics.values()) {
            int epicId = epic.getId();
            ArrayList<Integer> subtaskIds = epics.get(epicId).getSubTaskIds();
            for (Integer subtaskId : subtaskIds) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
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

    public ArrayList<Subtask> getSubtaskByOneEpic(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        ArrayList<Subtask> subtasksByOneEpic = new ArrayList<>();
        for (int subtaskId : subtasksId) {
            subtasksByOneEpic.add(subtasks.get(subtaskId));
        }
        return subtasksByOneEpic;
    }

    public void updateTask(Task updateTask) {
        tasks.put(updateTask.getId(), updateTask);
    }

    public void updateEpic(Epic updateEpic) {
        epics.put(updateEpic.getId(), updateEpic);
    }

    public void updateSubtask(Subtask updateSubtask) {
        subtasks.put(updateSubtask.getId(), updateSubtask);
        int epicId = subtasks.get(updateSubtask.getId()).getEpicId();
        checkEpicStatus(epicId);
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
        ArrayList<Integer> subtasksId = epics.get(epicId).getSubTaskIds();
        subtasksId.remove((Integer) subtaskIdForRemove);
        subtasks.remove(subtaskIdForRemove);
        checkEpicStatus(epicId);
    }

    public void checkEpicStatus(int id) {
        int counterNEW = 0;
        int counterDONE = 0;
        ArrayList<Integer> subtasksId = epics.get(id).getSubTaskIds();
        for (Integer subtaskId : subtasksId) {
            if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.NEW)) {
                counterNEW++;
            } else if (subtasks.get(subtaskId).getStatus().equals(TaskStatus.DONE)) {
                counterDONE++;
            }
        }
        if (subtasksId.size() == counterNEW || subtasksId.isEmpty()) {
            epics.get(id).setStatus(TaskStatus.NEW);
        } else if (subtasksId.size() == counterDONE) {
            epics.get(id).setStatus(TaskStatus.DONE);
        } else {
            epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
        }

    }
}
