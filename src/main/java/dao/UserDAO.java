package dao;

import db.MyConnection;
import model.User;

import java.sql.*;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String e = rs.getString(1);
            if (e.equals(email))
                return true;
        }
        return false;
    }
    public static boolean validatePassword(String email, String enteredPassword) {
        Connection connection = MyConnection.getConnection();
        try (PreparedStatement rs = connection.prepareStatement("SELECT password FROM users WHERE email = ?")) {
            rs.setString(1, email);
            ResultSet rls = rs.executeQuery();

            if (rls.next()) {
                String storedPassword = rls.getString("password");
                if (storedPassword.equals(enteredPassword)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public static int saveUser(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps =connection.prepareStatement("insert into users values(default, ?, ?, ?)");
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();
        return 1;
    }
}
