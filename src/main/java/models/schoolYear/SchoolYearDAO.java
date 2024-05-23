package models.schoolYear;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SchoolYearDAO extends DBContext {
    public List<SchoolYear> getAll() {
        List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
        String sql = "select * from schoolYears";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SchoolYear schoolYear = new SchoolYear();
                schoolYear.setId(rs.getString("id"));
                schoolYear.setName(rs.getString("name"));
                schoolYear.setStartDate(rs.getDate("start_date"));
                schoolYear.setEndDate(rs.getDate("end_date"));
                schoolYear.setDescription(rs.getString("description"));
//                schoolYear.setCreatedBy(rs.getString("created_by"));
                schoolYears.add(schoolYear);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return schoolYears;
    }

    public void createSchoolYear(SchoolYear schoolYear) {
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
//            statement.setString(6, schoolYear.getCreatedBy());
            statement.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createWeeksForSchoolYear(SchoolYear schoolYear) {
        long dayBetween = (schoolYear.getEndDate().getTime() - schoolYear.getStartDate().getTime()) / (24 * 60 * 60 * 1000);

    }
}
