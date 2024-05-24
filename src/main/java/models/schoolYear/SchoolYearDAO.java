package models.schoolYear;

import models.personnel.Personnel;
import models.personnel.PersonnelDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SchoolYearDAO extends DBContext {
    private SchoolYear createNewSchoolYear(ResultSet rs) throws SQLException {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(rs.getString("id"));
        schoolYear.setName(rs.getString("name"));
        schoolYear.setStartDate(rs.getDate("start_date"));
        schoolYear.setEndDate(rs.getDate("end_date"));
        schoolYear.setDescription(rs.getString("description"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel personnel = personnelDAO.getPersonnel(rs.getString("created_by"));
        schoolYear.setCreatedBy(personnel);
        return schoolYear;
    }

    public List<SchoolYear> getAll() {
        List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
        String sql = "select * from schoolYears";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                schoolYears.add(schoolYear);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return schoolYears;
    }

    public SchoolYear getLatest() {
        String sql = "SELECT TOP 1 * FROM SchoolYears ORDER BY ID DESC";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createNewSchoolYear(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNewSchoolYear(SchoolYear schoolYear) {
        String sql = "insert into schoolYear values(?,?,?,?,?,?)";
        try{
            //get the latest id
            String sql1 = "SELECT TOP 1 * FROM SchoolYears ORDER BY ID DESC";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            ResultSet rs1 = statement1.executeQuery();
            if (rs1.next()) {
                schoolYear.setId(rs1.getString("id"));
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYear.getId());
            statement.setString(2, schoolYear.getName());
            statement.setString(3, schoolYear.getStartDate().toString());
            statement.setString(4, schoolYear.getEndDate().toString());
            statement.setString(5, schoolYear.getDescription());
            statement.setString(6, schoolYear.getCreatedBy().getId());
            statement.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String generateId(int role){
        String id ;
        int newid ;
        PersonnelDAO personnelDAO = new PersonnelDAO();
        newid= pdao.getNumberOfPersonByRole(role)+1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        id= decimalFormat.format(newid);
        if (role == 0) {
            id = "AD" + id;
        } else if (role == 1) {
            id = "HT" + id;
        } else if (role == 2) {
            id = "AS" + id;
        } else if (role==3){
            id = "ACC"+ id;
        }else if (role == 4){
            id = "TEA"+ id;
        }

        return id;
    }

    public SchoolYear getSchoolYear(String id) {
        String sql = "select * from schoolYears where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                return schoolYear;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void createWeeksForSchoolYear(SchoolYear schoolYear) {
        long dayBetween = (schoolYear.getEndDate().getTime() - schoolYear.getStartDate().getTime()) / (24 * 60 * 60 * 1000);

    }
}
