public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task taskNew = new Task("Собрать коробки", "Положить в багажник");
        Task taskUpdate = taskManager.addTask(taskNew);
        System.out.println(taskUpdate);

        Task taskCreate = new Task(taskNew.getId(), "Пойти поработать", "Взять еду",
                Status.IN_PROGRESS);
        Task taskNewCreate = taskManager.updateTask(taskCreate);
        System.out.println(taskNewCreate);


        Epic flagUpdate = new Epic("Поехать на отдых", "Не забыть жену");
        taskManager.addEpic(flagUpdate);
        System.out.println(flagUpdate);
        Subtask flagUpdateSubtask1 = new Subtask("Купить продукты", "Взять список",
                flagUpdate.getId());
        Subtask flagUpdateSubtask2 = new Subtask("Провести уборку", "Использовать средства",
                flagUpdate.getId());
        taskManager.addSubtask(flagUpdateSubtask1);
        taskManager.addSubtask(flagUpdateSubtask2);
        System.out.println(flagUpdate);
        flagUpdateSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(flagUpdateSubtask2);
        System.out.println(flagUpdate);
    }
}