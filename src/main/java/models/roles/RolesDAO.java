
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 * RolesDAO class provides CRUD operations for Roles entities.
 */
public class RolesDAO extends DBContext {

    /**
     * Retrieves all roles from the database.
     *
     * @return a list of Roles objects.
     */
    public List<Roles> findAll() {
        List<Roles> rolesList = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Roles]";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            // Iterate over the result set and populate the list with Roles objects
            while (rs.next()) {
                Roles role = new Roles();
                role.setId(rs.getInt("id"));
                role.setDescription(rs.getString("description"));
                rolesList.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rolesList;
    }

    /**
     * Inserts a new role into the database.
     *
     * @param role the Roles object to be inserted.
     * @return the number of rows affected.
     */
    public int insert(Roles role) {
        String sql = "INSERT INTO [dbo].[Roles] (id, description) VALUES (?, ?)";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setInt(1, role.getId());
            ps.setString(2, role.getDescription());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Finds a role by its ID.
     *
     * @param id the ID of the role.
     * @return the Roles object if found, otherwise null.
     */
    public Roles findById(int id) {
        String sql = "SELECT * FROM [dbo].[Roles] WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter for the prepared statement
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Roles role = new Roles();
                    role.setId(rs.getInt("id"));
                    role.setDescription(rs.getString("description"));
                    return role;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a role's information in the database.
     *
     * @param role the Roles object with updated information.
     * @return true if the update was successful, otherwise false.
     */
    public boolean update(Roles role) {
        String sql = "UPDATE [dbo].[Roles] SET description = ? WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setString(1, role.getDescription());
            ps.setInt(2, role.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a role by its ID.
     *
     * @param id the ID of the role to be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    public boolean deleteById(int id) {
        String sql = "DELETE FROM [dbo].[Roles] WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter for the prepared statement
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
