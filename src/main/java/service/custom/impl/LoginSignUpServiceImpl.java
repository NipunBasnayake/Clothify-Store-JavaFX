package service.custom.impl;

import dao.Custom.LoginSignUpDao;
import dao.DaoFactory;
import dto.User;
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
        try {
            User user = loginSignUpDao.login(email, password);
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        try {
            // Consider hashing the password before updating it in the database
            return loginSignUpDao.updatePassword(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}