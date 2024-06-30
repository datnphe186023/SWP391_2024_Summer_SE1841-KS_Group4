import models.pupil.PupilDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PupilDAOTest {
    @Test
    public void testPupilDAO() {
        PupilDAO pupilDAO = new PupilDAO();
        assertEquals(10, pupilDAO.getPupilsByTeacherAndTimetable("TEA000008", "2025-10-28").size());
    }
}
