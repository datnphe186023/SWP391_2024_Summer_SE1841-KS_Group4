/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.personnel;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author asus
 */
public class PersonnelDAO extends DBContext{
    // Get all personnel infomation
    public List<Personnel> getAllPersonnels(){
        String sql = "select * from [Personnels] ";
        List<Personnel> persons =new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByRole(int role){
        String sql = "select * from [Personnels] where role_id like ?";
        List<Personnel> persons =new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + role + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByNameOrId(String data){
        String sql = "select * from [Personnels] where id like ? or first_name like ? or last_name like ? ";
        List<Personnel> persons =new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + data + "%");
            statement.setString(2, "%" + data + "%");
            statement.setString(3, "%" + data + "%");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        }catch (Exception e){
            System.out.println("error in function");
        }
        return persons;
    }

    public Personnel getPersonnel(String id){
        String sql = "select * from [Personnels] where id like ? ";
        Personnel person = new Personnel();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + id + "%");

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){

                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));

            }
        }catch (Exception e){
            System.out.println("error in function");
        }
        return person;
    }

    public int getNumberOfPersonByRole(int id){
        String sql = "select count(id) as numberofpersonbyrole\n" +
              "from Personnels where role_id = ? ";
        int number = 0;
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                number =resultSet.getInt("numberofpersonbyrole");
            }
        }catch (Exception e){
            System.out.println("error in function");
        }
        return number;
    }

    public void insertPersonnel(String id,String firstname,String lastname,int gender,String birthday,String address,String email,String phone, int role,String avatar){
        String sql="insert into Personnels values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, firstname);
            st.setString(3, lastname);
            st.setInt(4, gender);
            st.setString(5, birthday);
            st.setString(6, address);
            st.setString(7, email);
            st.setString(8, phone);
            st.setInt(9, role);
            st.setString(10, "đang chờ xử lý");
            st.setString(11, avatar);
            st.setString(12, null);

            st.executeUpdate();
            }catch(Exception e){
            System.out.println(e);
        }
    }
    public Personnel getPersonnelByUserId(String userId){
        String sql="select * from [User] u join Personnels p on u.id=p.user_id \n" +
                "where u.id = ?";
        Personnel personnel = new Personnel();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                personnel.setId(resultSet.getString(7)); /// 7 is position of personnel Id on join of two table
                personnel.setFirstName(resultSet.getString("first_name"));
                personnel.setLastName(resultSet.getString("last_name"));
                personnel.setGender(resultSet.getBoolean("gender"));
                personnel.setBirthday(resultSet.getDate("birthday"));
                personnel.setEmail(resultSet.getString("email"));
                personnel.setAddress(resultSet.getString("address"));
                personnel.setPhoneNumber(resultSet.getString("phone_number"));
                personnel.setRoleId(resultSet.getInt("role_id"));
                personnel.setStatus(resultSet.getString("status"));
                personnel.setAvatar(resultSet.getString("avatar"));
                personnel.setUserId(resultSet.getString("user_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personnel;
    }
        }
