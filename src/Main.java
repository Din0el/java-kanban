import manager.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task task = new Task("Описание", "Дубляж");
        Task newTask = taskManager.addTask(task);
        System.out.println(newTask);

        Task createTask = new Task("Описание", "Дубляж");
        Task newCreateTask = taskManager.updateTask(createTask);
        System.out.println(newCreateTask);


        Epic createEpic = new Epic("Описание", "Дубляж");
        taskManager.addEpic(createEpic);
        System.out.println(createEpic);
        Subtask createSubtask1 = new Subtask("Описание", "Дубляж",
                createEpic.getId());
        Subtask createSubtask2 = new Subtask("Описание", "Дубляж",
                createEpic.getId());
        taskManager.addSubtask(createSubtask1);
        taskManager.addSubtask(createSubtask2);
        System.out.println(createEpic);
        createSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(createSubtask2);
        System.out.println(createEpic);
    }
}