package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import status.Status;

class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task("Описание1", "Дубляж1", Status.NEW,10);
        Task task2 = new Task("Описание2", "Дубляж2", Status.DONE,10);
        assertEquals(task1, task2,
                "Ошибка!");
    }
}