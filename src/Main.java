import manager.InMemoryTaskManager;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        /*TaskManager manager = new InMemoryTaskManager();
        Task task1 = new Task("PlanA", "123");
        Task task2 = new Task("PlanB", "1234");

        manager.createNewTask(task1);
        manager.createNewTask(task2);

        Epic epic1 = new Epic("PlanAB", "1234");
        Epic epic2 = new Epic("PlanAC", "1289");

        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);

        Subtask subtask1 = new Subtask("subPlanA", "123", 3);
        Subtask subtask2 = new Subtask("subPlanB", "456", 3);
        Subtask subtask3 = new Subtask("subPlanC", "786", 4);
        Subtask subtask4 = new Subtask("subPlanD", "496", 4);

        manager.createNewSubtask(subtask1);
        manager.createNewSubtask(subtask2);
        manager.createNewSubtask(subtask3);
        manager.createNewSubtask(subtask4);

        System.out.println(manager.getListAllTask());
        System.out.println(manager.getListAllEpic());
        System.out.println(manager.getListAllSubtask());

        Task updateTask1 = new Task("PlanAN", task1.getId(), "156");
        manager.updateTask(updateTask1);
        System.out.println(manager.getListAllTask());

        Epic updateEpic2 = new Epic("PlanACN", epic2.getId(), "8989", epic2.getSubTaskIds());
        manager.updateEpic(updateEpic2);
        System.out.println(manager.getListAllEpic());

        Subtask updateSubtask1 = new Subtask(subtask1.getName(), subtask1.getDescription(), subtask1.getId(), TaskStatus.DONE, subtask1.getEpicId());
        Subtask updateSubtask3 = new Subtask("subPlanCN", "1212", subtask3.getId(), TaskStatus.IN_PROGRESS, subtask3.getEpicId());
        manager.updateSubtask(updateSubtask1);
        manager.updateSubtask(updateSubtask3);
        System.out.println(manager.getListAllSubtask());
        System.out.println(manager.getListAllEpic());

        System.out.println(manager.getSubtaskByEpicId(3));

        manager.removeTaskById(2);
        System.out.println(manager.getListAllTask());

        manager.removeEpicById(3);
        System.out.println(manager.getListAllEpic());
        System.out.println(manager.getListAllSubtask());

        manager.removeSubtaskById(8);
        System.out.println(manager.getListAllSubtask());
        System.out.println(manager.getListAllEpic());*/

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        for (int i = 1; i < 11; i++) {
            Task task = new Task("name" + i, "discription" + i);
            inMemoryTaskManager.createNewTask(task);
            inMemoryTaskManager.getTaskById(i);
        }
        ArrayList<Task> history = inMemoryTaskManager.getHistory();
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
    }
}
