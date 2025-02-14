package service.custom;

import model.User;
import service.SuperService;

public interface LoginSignupService extends SuperService {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
}
