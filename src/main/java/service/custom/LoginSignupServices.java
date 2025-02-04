package service.custom;

import model.User;

public interface LoginSignupServices {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
}
