package tasks;

import org.junit.jupiter.api.Test;
import status.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic("Описание1", "Дубляж1", Status.NEW,10);
        Epic epic2 = new Epic("Описание2", "Дубляж2",
                Status.IN_PROGRESS,10);
        assertEquals(epic1, epic2,
                "Ошибка!");
    }
}