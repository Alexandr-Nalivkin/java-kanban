import org.junit.jupiter.api.Test;
import tasks.Subtask;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskTest {
    Subtask subtask1 = new Subtask("testName1", "testDescription1", 1, TaskStatus.NEW, 1);
    Subtask subtask2 = new Subtask("testName1", "testDescription1", 1, TaskStatus.NEW, 1);

    @Test
    void subtasksWithSameIdAreEqual() {
        assertEquals(subtask1, subtask2);
    }
}
