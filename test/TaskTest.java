import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    Task task1 = new Task("testName1", "testDescription1", 1);
    Task task2 = new Task("testName2", "testDescription2", 1);

    @Test
    void tasksWithSameIdAreEqual() {
        assertEquals(task1, task2);
    }
}
