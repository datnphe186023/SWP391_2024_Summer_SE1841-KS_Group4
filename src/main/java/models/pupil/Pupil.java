package models.pupil;

import models.evaluation.EvaluationDAO;
import models.evaluation.IEvaluationDAO;
import models.evaluation.SchoolYearSummarize;
import models.personnel.Personnel;
import models.week.IWeekDAO;
import models.week.WeekDAO;

import java.util.Date;

public class Pupil {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String status;
    private Date birthday;
    private boolean gender;
    private String firstGuardianName;
    private String firstGuardianPhoneNumber;
    private String avatar;
    private String secondGuardianName;
    private String secondGuardianPhoneNumber;
    private Personnel createdBy;
    private String parentSpecialNote;

    public Pupil(String id, String userId, String firstName, String lastName,
                 String address, String email, String status, Date birthday, boolean gender,
                 String firstGuardianName, String firstGuardianPhoneNumber, String avatar,
                 String secondGuardianName, String secondGuardianPhoneNumber, Personnel createdBy,
                 String parentSpecialNote) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.status = status;
        this.birthday = birthday;
        this.gender = gender;
        this.firstGuardianName = firstGuardianName;
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
        this.avatar = avatar;
        this.secondGuardianName = secondGuardianName;
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
        this.createdBy = createdBy;
        this.parentSpecialNote = parentSpecialNote;
    }

    public Pupil() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getfirstGuardianName() {
        return firstGuardianName;
    }

    public void setfirstGuardianName(String firstGuardianName) {
        this.firstGuardianName = firstGuardianName;
    }

    public String getfirstGuardianPhoneNumber() {
        return firstGuardianPhoneNumber;
    }

    public void setfirstGuardianPhoneNumber(String firstGuardianPhoneNumber) {
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getsecondGuardianName() {
        return secondGuardianName;
    }

    public void setsecondGuardianName(String secondGuardianName) {
        this.secondGuardianName = secondGuardianName;
    }

    public String getsecondGuardianPhoneNumber() {
        return secondGuardianPhoneNumber;
    }

    public void setsecondGuardianPhoneNumber(String secondGuardianPhoneNumber) {
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public String getParentSpecialNote() {
        return parentSpecialNote;
    }

    public void setParentSpecialNote(String parentSpecialNote) {
        this.parentSpecialNote = parentSpecialNote;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearEvaluation(String School_year_id){
        IEvaluationDAO dao = new EvaluationDAO();
        IWeekDAO weekDAO = new WeekDAO();
        String evaluation = dao.PupilReportYearly(this.id,School_year_id);
        String result="";
            String[] cut = evaluation.split("-");
            if(School_year_id.equals(cut[0])){
               result=cut[1]+" / "+cut[2];
            }
        return result;
    }
    public String evaluate(String School_year_id){
        IEvaluationDAO dao = new EvaluationDAO();
        IWeekDAO weekDAO = new WeekDAO();
        String evaluation = dao.PupilReportYearly(this.id,School_year_id);
        String result="";
        String[] cut = evaluation.split("-");
        if(School_year_id.equals(cut[0])){
            if(Integer.parseInt(cut[1])>Integer.parseInt(cut[2]) /2){
                result = "Đạt";
            }else{
                result = "Không đạt";
            }
        }
        return result;
    }

    public boolean isSummarized(String schoolYearId){
        IEvaluationDAO evaluationDAO = new EvaluationDAO();
        SchoolYearSummarize schoolYearSummarize = evaluationDAO.getSchoolYearSummarize(getId(), schoolYearId);
        return schoolYearSummarize != null;
    }

}
