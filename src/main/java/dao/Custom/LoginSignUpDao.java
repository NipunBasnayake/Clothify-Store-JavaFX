package dao.Custom;

import dao.SuperDao;
import model.User;

public interface LoginSignUpDao extends SuperDao {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
}
