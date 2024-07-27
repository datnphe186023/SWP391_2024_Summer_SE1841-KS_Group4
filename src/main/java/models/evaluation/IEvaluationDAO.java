package models.evaluation;

public interface IEvaluationDAO {
    boolean createEvaluation(Evaluation evaluation);
    boolean checkEvaluationExist(String pupilId, String dateId);
    boolean updateNoteByPupilAndDay(Evaluation evaluation);
    public boolean updateEvaluationByPupilAndDay(Evaluation evaluation);
}
