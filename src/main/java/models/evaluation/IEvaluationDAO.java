package models.evaluation;

import java.util.List;

public interface IEvaluationDAO {
    boolean createEvaluation(Evaluation evaluation);
    boolean checkEvaluationExist(String pupilId, String dateId);
    boolean updateNoteByPupilAndDay(Evaluation evaluation);
    public boolean updateEvaluationByPupilAndDay(Evaluation evaluation);
    int getNumberOfStatus(String evaluation, String pupilId, String weekId);
    List<Evaluation> getEvaluationByWeek(String weekId);
}
