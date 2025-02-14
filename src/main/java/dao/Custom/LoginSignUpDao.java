package dao.Custom;

import dao.SuperDao;
import dto.User;

public interface LoginSignUpDao extends SuperDao {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
}
