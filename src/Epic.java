import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    ArrayList<Integer> subTaskIds = new ArrayList<>();
    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public void addSubTaskId(Subtask subtask) {
        subTaskIds.add(subtask.getId());
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTaskId(int subTaskId) {
        subTaskIds.remove(subTaskId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Epic epic = (Epic) o;
        return Objects.equals(subTaskIds, epic.subTaskIds);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(subTaskIds);
        return result;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIds=" + subTaskIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
