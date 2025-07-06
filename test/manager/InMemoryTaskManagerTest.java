package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Task;
import tasks.Subtask;
import tasks.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    void addNewTask() {
        final Task task = taskManager.addTask(new Task("Тест Описание", "Тест Дубляж"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {
        final Epic taskDescription = taskManager.addEpic(new Epic("Описание1",
                "Дубляж1"));
        final Subtask taskDescription1 = taskManager.addSubtask(new Subtask("Описание2",
                "Дубляж2", taskDescription.getId()));
        final Subtask subtaskDescription2 = taskManager.addSubtask(new Subtask("Описание3",
                "Дубляж3", taskDescription.getId()));
        final Subtask taskDescription2 = taskManager.addSubtask(new Subtask("Описание4", "Дубляж4",
                taskDescription.getId()));
        final Epic savedEpic = taskManager.getEpicByID(taskDescription.getId());
        final Subtask savedSubtask1 = taskManager.getSubtaskByID(taskDescription1.getId());
        final Subtask savedSubtask2 = taskManager.getSubtaskByID(subtaskDescription2.getId());
        final Subtask savedSubtask3 = taskManager.getSubtaskByID(taskDescription2.getId());
        assertNotNull(savedEpic, "Эпик не найден");
        assertNotNull(savedSubtask2, "Субтаск не найден");
        assertEquals(taskDescription, savedEpic, "Эпики не совпадают");
        assertEquals(taskDescription1, savedSubtask1, "Субтаск не совпадают");
        assertEquals(taskDescription2, savedSubtask3, "Субтаск не совпадают");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются");
        assertEquals(1, epics.size(), "Неверное количество эпиков");
        assertEquals(taskDescription, epics.getFirst(), "Эпики не совпадают");

        final List<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Субтаски не возвращаются");
        assertEquals(3, subtasks.size(), "Неверное количество Субтаска");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Субтаски не совпадают");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("Описание1", "Дубляж1");
        taskManager.addTask(expected);
        final Task updatedTask = new Task("Описание2", "Дубляж2", Status.DONE,expected.getId());
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулся таск с другим id");
    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic("Описание2", "Дубляж2", Status.DONE,expected.getId());
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("Описание2", "Дубляж2", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask("Описание3", "Дубляж3",
                Status.DONE, epic.getId(),expected.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулись Субтаски с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Описание1", "Дубляж1"));
        taskManager.addTask(new Task("Описание2", "Дубляж2"));
        taskManager.deleteTasks();
        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty(), "Должен быть пуст таск");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Описание", "Дубляж"));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "Должен быть пуст эпик");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic epicDescription = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(epicDescription);
        taskManager.addSubtask(new Subtask("Описание2", "Дубляж2",
                epicDescription.getId()));
        taskManager.addSubtask(new Subtask("Описание3", "Дубляж3",
                epicDescription.getId()));
        taskManager.addSubtask(new Subtask("Описание4", "Дубляж4",
                epicDescription.getId()));

        taskManager.deleteSubtasks();
        List<Subtask> subtasks = taskManager.getSubtasks();
        assertTrue(subtasks.isEmpty(), "Должен быть пуст субтаск");
    }

    @Test
    public void deleteTaskByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addTask(new Task("Описание1", "Дубляж1", Status.NEW,1));
        taskManager.addTask(new Task("Описание2", "Дубляж2", Status.DONE,2));
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteEpicByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addEpic(new Epic("Описание", "Дубляж", Status.IN_PROGRESS,1));
        taskManager.deleteEpicByID(1);
        assertNull(taskManager.deleteTaskByID(1));
    }

    @Test
    public void deleteSubtaskByIdShouldReturnNullIfKeyIsMissing() {
        Epic epicsDescription = new Epic("Описание1", "Дубляж1");
        taskManager.addEpic(epicsDescription);
        taskManager.addSubtask(new Subtask("Описание2", "Дубляж2",
                epicsDescription.getId()));
        taskManager.addSubtask(new Subtask("Описание3", "Дубляж3",
                epicsDescription.getId()));
        taskManager.addSubtask(new Subtask("Описание4", "Дубляж4",
                epicsDescription.getId()));
        assertNull(taskManager.deleteSubtaskByID(5));
    }


    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task("Описание", "Дубляж", Status.DONE,1);
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }

}
