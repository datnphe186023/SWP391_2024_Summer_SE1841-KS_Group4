package models.evaluation;

import java.util.Date;
import java.util.List;

public interface IEvaluationDAO {

    boolean createEvaluation(Evaluation evaluation);

    boolean checkEvaluationExist(String pupilId, String dateId);

    boolean updateNoteByPupilAndDay(Evaluation evaluation);

    public boolean updateEvaluationByPupilAndDay(Evaluation evaluation);

    int getNumberOfStatus(String evaluation, String pupilId, String weekId);

    List<Evaluation> getEvaluationByWeek(String weekId);

    public List<HealthCheckUp> getHealthCheckUpById(String pupil_id);

    public List<HealthCheckUp> getHealthCheckUpByIdandSchoolYearId(String pupil_id, String schoolyear_id);

    public HealthCheckUp getHealthCheckUpByIdandDate(String pupil_id, Date date);

    public List<String> EvaluationReportYearly(String pupil_id);

    public int countEvaluationOfWeek(String week_id,String pupil_id);
    public String PupilReportYearly(String pupil_id,String school_year_id);
    public List<Evaluation> getEvaluationByWeekandPupilId(String weekId,String pupil_id);
    int AccomplishmentAchieveStudents(String schoolYearId);
  
    int getEvaluationByPupilIdandStatusGood(String pupilid);
    int getEvaluationByPupilIdandAllStatus(String pupilid);

    public List<String> NumberOfGoodEvaluationsPerYear(String pupil_id);

    SchoolYearSummarize getSchoolYearSummarize(String pupilId, String schoolYearId);

    String updateSchoolYearSummarize(SchoolYearSummarize schoolYearSummarize);
}
