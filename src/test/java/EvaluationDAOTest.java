import models.evaluation.EvaluationDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class EvaluationDAOTest {
    @Test
    public void test() {
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        assertEquals(2, evaluationDAO.getNumberOfStatus("Ngoan","HS000006","W000084"));
    }

}
