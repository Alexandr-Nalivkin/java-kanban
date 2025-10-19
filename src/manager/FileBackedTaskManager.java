package manager;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

public class FileBackedTaskManager extends  InMemoryTaskManager implements TaskManager {
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

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task: getListAllTask()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic: getListAllEpic()) {
                writer.write(toString(epic) + "\n");
            }

            for (Subtask subtask: getListAllSubtask()) {
                writer.write(toString(subtask) + "\n");
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при сохранении в файл", exception);
        }
    }

    public static String toString(Task task) {
        TaskType type;
        String epicId = "";

        if (task instanceof Epic) {
            type = TaskType.EPIC;
        } else if (task instanceof Subtask) {
            type = TaskType.SUBTASK;
            epicId = String.valueOf((((Subtask) task).getEpicId()));
        } else {
            type = TaskType.TASK;
        }
        return String.join(",",
                String.valueOf(task.getId()),
                type.name(),
                task.getName(),
                task.getStatus().name(),
                task.getDescription(),
                epicId);
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException();
        }

        int id = Integer.parseInt(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = parts[2];
        TaskStatus status = TaskStatus.valueOf(parts[3]);
        String description = parts[4];

        switch (type) {
            case TASK:
                Task task = new Task(name, description, id, status);
                return task;

            case EPIC:
                Epic epic = new Epic(name, description, id);
                return epic;

            case SUBTASK:
                if (parts.length < 6) {
                    throw new IllegalArgumentException();
                }
                int epicId = Integer.parseInt(parts[5]);
                Subtask subtask = new Subtask(name, description, id, status,epicId);
                return subtask;

            default:
                throw new IllegalArgumentException();
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

                Task task = fromString(lines[i]);

                if (task instanceof Epic) {
                    manager.createNewEpic((Epic) task);
                } else if (task instanceof Subtask) {
                    manager.createNewSubtask((Subtask) task);
                } else {
                    manager.createNewTask(task);
                }
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Ошибка при загрузке из файла", exception);
        }

        return manager;
    }
}
