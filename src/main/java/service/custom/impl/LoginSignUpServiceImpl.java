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