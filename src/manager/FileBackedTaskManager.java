package manager;

import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import utility.TaskStringTransform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

public class FileBackedTaskManager extends  InMemoryTaskManager {
    protected final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public void createNewTask(Task task) {
        super.createNewTask(task);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task updateTask) {
        super.updateTask(updateTask);
        save();
    }

    @Override
    public void updateEpic(Epic updateEpic) {
        super.updateEpic(updateEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        super.updateSubtask(updateSubtask);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int subtaskIdForRemove) {
        super.removeSubtaskById(subtaskIdForRemove);
        save();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeAllSubtask() {
        super.removeAllSubtask();
        save();
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epicId\n");

            for (Task task: getListAllTask()) {
                writer.write(TaskStringTransform.toString(task) + "\n");
            }

            for (Epic epic: getListAllEpic()) {
                writer.write(TaskStringTransform.toString(epic) + "\n");
            }

            for (Subtask subtask: getListAllSubtask()) {
                writer.write(TaskStringTransform.toString(subtask) + "\n");
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при сохранении в файл", exception);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try {
            String content = Files.readString(file.toPath());
            String[] lines = content.split("\n");

            if (lines.length == 0) {
                return manager;
            }

            for (int i = 1; i < lines.length; i++) {
                if (lines[i].isEmpty()) {
                    continue;
                }

                Task task = TaskStringTransform.fromString(lines[i]);
                int taskId = task.getId();

                switch (task.getType()) {
                    case TASK -> manager.tasks.put(taskId, task);
                    case EPIC -> manager.epics.put(taskId, (Epic) task);
                    case SUBTASK -> {
                        manager.subtasks.put(taskId, (Subtask) task);
                        Epic subtaskEpic = manager.epics.get(((Subtask) task).getEpicId());
                        if (subtaskEpic != null) {
                            subtaskEpic.addSubtaskId(task.getId());
                        }
                    }
                    default -> throw new IllegalArgumentException("Неизвестный тип задачи");
                }
                if (taskId > manager.id) {
                    manager.id = taskId;
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при загрузке из файла", exception);
        }

        return manager;
    }
}
