package dao.Custom.impl;

import dao.Custom.LoginSignUpDao;
import db.DBConnection;
import entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignupDaoImpl implements LoginSignUpDao {

    @Override
    public UserEntity login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            if (connection == null) {
                throw new SQLException("Database connection is null");
            }

            statement.setString(1, email);
            statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new UserEntity(
                            resultSet.getInt("userId"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            "xxxxx",
                            resultSet.getString("role"),
                            resultSet.getString("registrationDate")
                    );
                }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to login", e);
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserEntity getUserById(int id) {
        String query = "SELECT * FROM user WHERE userId = ?";

        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("userId"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("registrationDate")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
