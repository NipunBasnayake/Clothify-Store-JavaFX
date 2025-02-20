package dao.Custom.impl;

import dao.Custom.LoginSignUpDao;
import db.DBConnection;
import entity.UserEntity;
import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignupDaoImpl implements LoginSignUpDao {

    @Override
    public UserEntity login(String email, String password) {
        String sql = "SELECT password FROM user WHERE email = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
                textEncryptor.setPassword("ClothifySecureKey");

                String passwordFromDB = resultSet.getString("password");
                String decryptedPassword = textEncryptor.decrypt(passwordFromDB);

                if (password.trim().equals(decryptedPassword)) {
                    String query = "SELECT * FROM user WHERE email = ?";
                    PreparedStatement statement2 = DBConnection.getInstance().getConnection().prepareStatement(query);
                    statement2.setString(1, email);

                    ResultSet resultSet2 = statement2.executeQuery();
                    if (resultSet2.next()) {
                        return new UserEntity(
                                resultSet2.getInt("userId"),
                                resultSet2.getString("name"),
                                resultSet2.getString("email"),
                                "xxxxx",
                                resultSet2.getString("role"),
                                resultSet2.getString("registrationDate")
                        );
                    }
                } else {
                    System.out.println("Invalid password!");
                }
            } else {
                System.out.println("Email not found!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    @Override
    public boolean addNewUser(UserEntity map) {
        String query = "INSERT INTO user (name, email, password, role) VALUES ( ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, map.getUserName());
            statement.setString(2, map.getEmail());
            statement.setString(3, map.getPassword());
            statement.setString(4, map.getRole());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
