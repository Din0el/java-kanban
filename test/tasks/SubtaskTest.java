package tasks;

import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void SubtasksWithEqualIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("Описание1", "Дубляж1", Status.NEW, 5,10);
        Subtask subtask2 = new Subtask("Описание2", "Дубляж2", Status.DONE, 5,10);
        assertEquals(subtask1, subtask2,
                "Ошибка!");
    }
}