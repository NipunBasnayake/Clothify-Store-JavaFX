package service.custom.impl;

import dao.Custom.LoginSignUpDao;
import dao.DaoFactory;
import db.DBConnection;
import model.User;
import service.custom.LoginSignupService;
import util.DaoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSignUpServiceImpl implements LoginSignupService {
    private static LoginSignUpServiceImpl loginSignUpController;

    public static LoginSignUpServiceImpl getInstance() {
        if (loginSignUpController == null) {
            loginSignUpController = new LoginSignUpServiceImpl();
        }
        return loginSignUpController;
    }

    LoginSignUpDao loginSignUpDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public User login(String email, String password) {
        return loginSignUpDao.login(email, password);
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return loginSignUpDao.updatePassword(email, password);
    }

}
