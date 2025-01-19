package controller.loginSignup;

import db.DBConnection;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignUpController implements LoginSignupServices {
    private static LoginSignUpController loginSignUpController;

    public static LoginSignUpController getInstance() {
        return loginSignUpController==null?loginSignUpController=new LoginSignUpController():loginSignUpController;
    }

    @Override
    public User login(String email, String password) {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM user WHERE email ='" + email + "'");
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    return new User(
                            resultSet.getInt("UserID"),
                            resultSet.getString("name"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("Role"),
                            resultSet.getString("RegDate")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        System.out.println(email +" - "+ password);
        try (PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE user SET password = ? WHERE email = ?")) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
