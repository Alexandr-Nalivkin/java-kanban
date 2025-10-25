package utility;

import tasks.*;

public class TaskStringTransform {
    public static String toString(Task task) {
        TaskType type = task.getType();
        String epicId = "";

        if (type == TaskType.SUBTASK) {
            epicId = String.valueOf((((Subtask) task).getEpicId()));
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
            throw new IllegalArgumentException("Неверный формат строки");
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
}
