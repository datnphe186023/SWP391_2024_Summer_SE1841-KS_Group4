package models.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

public class RoleDAO extends DBContext {

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM dbo.Role";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getString("id"));
                role.setDescription(rs.getString("description"));
                roles.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public int insert(Role role) {
        String sql = "INSERT INTO dbo.Role (id, description) VALUES (?, ?)";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role.getId());
            ps.setString(2, role.getDescription());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Role findById(String id) {
        String sql = "SELECT * FROM dbo.Role WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getString("id"));
                    role.setDescription(rs.getString("description"));
                    return role;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Role role) {
        String sql = "UPDATE dbo.Role SET description = ? WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role.getDescription());
            ps.setString(2, role.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteById(String id) {
        String sql = "DELETE FROM dbo.Role WHERE id = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
