import manager.Managers;
import manager.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public static void main(String[] args) {
    TaskManager taskManager = Managers.getDefault();

    Task newTask  = new Task("Описание", "Дубляж");
    taskManager.addTask(newTask );

    Task createTask = new Task( "Описание",
            "Дубляж", Status.IN_PROGRESS,newTask.getId());
    taskManager.updateTask(createTask);
    taskManager.addTask(new Task("Описание", "Дубляж"));


    Epic createEpic = new Epic("Описание", "Дубляж");
    taskManager.addEpic(createEpic);
    Subtask createSubtask1 = new Subtask("Описание", "Дубляж",
            createEpic.getId());
    Subtask createSubtask2 = new Subtask("Описание", "Дубляж",
            createEpic.getId());
    Subtask createSubtask3 = new Subtask("Описание", "Дубляж",
            createEpic.getId());
    taskManager.addSubtask(createSubtask1);
    taskManager.addSubtask(createSubtask2);
    taskManager.addSubtask(createSubtask3);
    createSubtask2.setStatus(Status.DONE);
    taskManager.updateSubtask(createSubtask2);


    System.out.println("Таски:");
    for (Task task : taskManager.getTasks()) {
        System.out.println(task);
    }
    System.out.println("Эпики:");
    for (Epic epic : taskManager.getEpics()) {
        System.out.println(epic);

        for (Task task : taskManager.getEpicSubtasks(epic)) {
            System.out.println(task);
        }
    }

    System.out.println("Субтаски:");
    for (Task subtask : taskManager.getSubtasks()) {
        System.out.println(subtask);
    }


    taskManager.getTaskByID(1);
    taskManager.getTaskByID(2);
    taskManager.getEpicByID(3);
    taskManager.getTaskByID(1);
    taskManager.getSubtaskByID(4);
    taskManager.getSubtaskByID(5);
    taskManager.getSubtaskByID(6);
    taskManager.getEpicByID(3);
    taskManager.getSubtaskByID(4);
    taskManager.getTaskByID(2);
    taskManager.getSubtaskByID(6);

    System.out.println();
    System.out.println("История просмотров:");
    for (Task task : taskManager.getHistory()) {
        System.out.println(task);

        /*System.out.println("Удаление");
        taskManager.deleteTaskByID(1);
        System.out.println(task);
        taskManager.deleteTasks();
        taskManager.printTasks();
        taskManager.printSubtasks();
        taskManager.deleteSubtasks();
        taskManager.printSubtasks();
        taskManager.deleteEpicByID(4);
        taskManager.printEpics();
        taskManager.deleteEpics();
        taskManager.printEpics();*/
    }
}