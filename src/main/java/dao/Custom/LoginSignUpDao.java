package dao.Custom;

import dao.SuperDao;
import dto.User;
import entity.UserEntity;

public interface LoginSignUpDao extends SuperDao {
    User login(String email, String password);
    boolean updatePassword(String email, String password);
    UserEntity getUserById(int id);
}
