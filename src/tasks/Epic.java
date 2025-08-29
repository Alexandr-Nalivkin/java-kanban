package tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIds = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public Epic(String name, String description, int id, ArrayList<Integer> subTaskIds) {
        super(name, description, id);
        this.subTaskIds = subTaskIds;
    }

    public void addSubTaskId(Subtask subtask) {
        subTaskIds.add(subtask.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTaskId(Subtask subtask) {
        subTaskIds.remove((Integer) subtask.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return super.equals(epic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIds);
    }

    @Override
    public String toString() {
        return "Tasks.Epic{" +
                "subTaskIds=" + subTaskIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
