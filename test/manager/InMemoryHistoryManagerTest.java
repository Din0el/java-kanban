package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 20; i++) {
            taskManager.addTask(new Task("Описание", "Дубляж"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task newTask = new Task("Описание1", "Дубляж1");
        taskManager.addTask(newTask);
        taskManager.getTaskByID(newTask.getId());
        taskManager.updateTask(new Task("Описание2",
                "Дубляж2", Status.IN_PROGRESS,newTask.getId()));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(newTask.getName(), oldTask.getName(), "Не сохранился таск");
        assertEquals(newTask.getDescription(), oldTask.getDescription(),
                "Не сохранился таск");

    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic taskDescription = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(taskDescription);
        taskManager.getEpicByID(taskDescription.getId());
        taskManager.updateEpic(new Epic("Описание2", "Дубляж2",
                Status.IN_PROGRESS,taskDescription.getId()));
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(taskDescription.getName(), oldEpic.getName(),
                "Не сохранился эпик");
        assertEquals(taskDescription.getDescription(), oldEpic.getDescription(),
                "Не сохранился эпик");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic taskDescription = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(taskDescription);
        Subtask taskDescription3 = new Subtask("Описание2", "Дубляж2",
                taskDescription.getId());
        taskManager.addSubtask(taskDescription3);
        taskManager.getSubtaskByID(taskDescription3.getId());
        taskManager.updateSubtask(new Subtask("Описание3",
                "Дубляж3", Status.IN_PROGRESS, taskDescription.getId(),taskDescription3.getId()));
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask) subtasks.getFirst();
        assertEquals(taskDescription3.getName(), oldSubtask.getName(),
                "Не сохранился субтаск");
        assertEquals(taskDescription3.getDescription(), oldSubtask.getDescription(),
                "Не сохранился субтаск");
    }
}
