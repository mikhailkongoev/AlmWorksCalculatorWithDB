package core.evals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void getClichedMessage() {
        Evaluator evaluator = new Evaluator();

        assertEquals(evaluator.getClichedMessage("5*7+7*5"), 70d, 0.00001);
        assertEquals(evaluator.getClichedMessage("-5"), -5d, 0.00001);
        assertEquals(evaluator.getClichedMessage("2.56/4"), 0.64d, 0.00001);
        assertEquals(evaluator.getClichedMessage("2+2+2+2-3"), 5d, 0.00001);
    }
}