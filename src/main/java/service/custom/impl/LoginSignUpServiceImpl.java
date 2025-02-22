package service.custom.impl;

import dao.Custom.LoginSignUpDao;
import dao.DaoFactory;
import dto.User;
import entity.UserEntity;
import org.modelmapper.ModelMapper;
import service.custom.LoginSignupService;
import util.DaoType;

public class LoginSignUpServiceImpl implements LoginSignupService {
    private static LoginSignUpServiceImpl loginSignUpServiceImpl;
    private final LoginSignUpDao loginSignUpDao;

    private LoginSignUpServiceImpl() {
        loginSignUpDao = DaoFactory.getInstance().getDao(DaoType.USER);
    }

    public static LoginSignUpServiceImpl getInstance() {
        if (loginSignUpServiceImpl == null) {
            loginSignUpServiceImpl = new LoginSignUpServiceImpl();
        }
        return loginSignUpServiceImpl;
    }

    @Override
    public User login(String email, String password) {
        UserEntity userEntity = loginSignUpDao.login(email, password);
        if (userEntity == null) {
            return null;
        }
        return new ModelMapper().map(userEntity, User.class);
    }

    @Override
    public User updatePassword(String email, String password) {
        return new ModelMapper().map(loginSignUpDao.updatePassword(email, password), User.class);
    }

    @Override
    public User getUserById(int id) {
        UserEntity userEntity = loginSignUpDao.getUserById(id);
        if (userEntity != null) {
            return new ModelMapper().map(userEntity, User.class);
        }
        return null;
    }

    @Override
    public boolean addNewUser(User newUser) {
        if (newUser != null) {
            UserEntity userEntity = new ModelMapper().map(newUser, UserEntity.class);
            return loginSignUpDao.saveUser(userEntity);
        }
        return false;
    }
}