import org.junit.jupiter.api.Test;
import tasks.Epic;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {
    Epic epic1 = new Epic("testName1", "testDescription1", 1);
    Epic epic2 = new Epic("testName2", "testDescription2", 1);

    @Test
    void epicsWithSameIdAreEqual() {
        assertEquals(epic1, epic2);
    }
}
