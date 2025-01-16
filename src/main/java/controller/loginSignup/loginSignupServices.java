package controller.loginSignup;

import model.User;

public interface loginSignupServices {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
}
