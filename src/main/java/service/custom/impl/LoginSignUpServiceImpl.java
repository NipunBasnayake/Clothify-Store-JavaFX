package service.custom.impl;

import com.google.inject.Inject;
import dao.Custom.LoginSignUpDao;
import dto.User;
import entity.UserEntity;
import org.modelmapper.ModelMapper;
import service.custom.CustomerService;
import service.custom.LoginSignupService;

public class LoginSignUpServiceImpl implements LoginSignupService {

    @Inject
    LoginSignUpDao loginSignUpDao;

    @Override
    public User login(String email, String password) {
        UserEntity userEntity = loginSignUpDao.login(email, password);
        if (userEntity == null) {
            return null;
        }
        return new ModelMapper().map(userEntity, User.class);
    }

    @Override
    public boolean updatePassword(String email, String password) {
        try {
            return loginSignUpDao.updatePassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(int id) {
        UserEntity userEntity = loginSignUpDao.getUserById(id);
        if (userEntity != null) {
            return new ModelMapper().map(userEntity, User.class);
        }
        return null;
    }
}