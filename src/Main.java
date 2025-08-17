public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();
        Task task1 = new Task("PlanA", "123", TaskStatus.NEW);
        Task task2 = new Task("PlanB", "1234", TaskStatus.NEW);

        manager.createNewTask(task1);
        manager.createNewTask(task2);

        Epic epic1 = new Epic("PlanAB", "1234", TaskStatus.NEW);
        Epic epic2 = new Epic("PlanAC", "1289", TaskStatus.NEW);

        manager.createNewEpic(epic1);
        manager.createNewEpic(epic2);

        Subtask subtask1 = new Subtask("subPlanA", "123", TaskStatus.NEW, 3);
        Subtask subtask2 = new Subtask("subPlanB", "456", TaskStatus.DONE, 3);
        Subtask subtask3 = new Subtask("subPlanC", "786", TaskStatus.DONE, 3);
        Subtask subtask4 = new Subtask("subPlanD", "496", TaskStatus.DONE, 4);

        manager.createNewSubtask(subtask1);
        manager.createNewSubtask(subtask3);
        manager.createNewSubtask(subtask4);
        manager.createNewSubtask(subtask2);

        System.out.println(manager.getListAllTask());
        System.out.println(manager.getListAllEpic());
        System.out.println(manager.getListAllSubtask());

        manager.removeSubtaskById(5);
        System.out.println(manager.getListAllEpic());
        System.out.println(manager.getListAllSubtask());

        manager.removeAllSubtask();
        System.out.println(manager.getListAllEpic());
        System.out.println(manager.getListAllSubtask());

        manager.removeAllTask();
        System.out.println(manager.getListAllTask());
    }
}
