package controller;

import animatefx.animation.SlideInRight;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.custom.impl.LoginSignUpControllerImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


public class LoginSignupViewController {
    static String otp;

    public Label lblForgitPassword;
    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane paneForgotPassword;

    @FXML
    private AnchorPane paneLogin;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtForgotEmail;

    @FXML
    private TextField txtLoginEmail;

    @FXML
    private PasswordField txtLoginPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtOTP;

    @FXML
    void btnLogInOnAction(ActionEvent event) {
        if (txtLoginEmail.getText().isEmpty() || txtLoginPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Fields are empty");
            alert.show();
        } else {
            if (LoginSignUpControllerImpl.getInstance().login(txtLoginEmail.getText(), txtLoginPassword.getText()) == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Username or Password is incorrect");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Success");
                alert.setHeaderText("Login Success");
                alert.show();
            }

        }
    }

    @FXML
    void btnSendOTP(ActionEvent event) {
        if (txtForgotEmail.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Email cannot be empty");
            alert.show();
        } else {
            otp = generateOTP();
            sendOtpEmail(txtForgotEmail.getText(), otp);
        }
    }

    @FXML
    void btnResetPassword(ActionEvent event) {
        if (txtOTP.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("OTP cannot be empty");
            alert.show();
            return;
        }

        if (!txtOTP.getText().equals(otp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid OTP");
            alert.show();
            return;
        }

        if (txtNewPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password cannot be empty");
            alert.show();
            return;
        }

        if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password doesn't match");
            alert.show();
            return;
        }

        boolean isUpdated = LoginSignUpControllerImpl.getInstance().updatePassword(txtForgotEmail.getText(), txtNewPassword.getText());
        if (isUpdated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Password updated successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password update failed");
            alert.show();
        }
    }

    @FXML
    void lblForgotPasswordMouseEntered(MouseEvent event) {
        lblForgitPassword.setStyle("-fx-text-fill: #a30000;");
    }

    @FXML
    public void lblForgotPasswordMouseExited(MouseEvent mouseEvent) {
        lblForgitPassword.setStyle("-fx-text-fill: #000000;");
    }

    @FXML
    void lblForgotPasswordOnAction(MouseEvent event) {
        new SlideInRight(paneForgotPassword).play();
        paneForgotPassword.toFront();

        txtForgotEmail.setText(txtLoginEmail.getText());
    }

    @FXML
    void btnBackOnAction(MouseEvent event) {
        new SlideInRight(paneLogin).play();
        paneLogin.toFront();
    }

    private String generateOTP() {
        String otp = "";
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            otp += rand.nextInt(10);
        }
        return otp;
    }

    private void sendOtpEmail(String recipientEmail, String otp) {
        String host = "smtp.gmail.com";
        final String user = "reg.clothify@gmail.com";
        final String password = "wyds qhbr aiqx moja";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Reset Your Password - Clothify Store");

            String emailContent = "<html><body>"
                    + "<h2>Clothify Store</h2>"
                    + "<p>Hello,</p>"
                    + "<p>You requested to reset your password at Clothify Store. Please use the following OTP to proceed:</p>"
                    + "<h2 style='color: #007BFF;'>" + otp + "</h2>"
                    + "<p>If you did not request a password reset, please ignore this email.</p>"
                    + "<p>Thank you for choosing Clothify Store!</p>"
                    + "<p>Best Regards,<br/>The Clothify Store Team</p>"
                    + "</body></html>";

            message.setContent(emailContent, "text/html");
            Transport.send(message);

            txtOTP.setEditable(true);
            txtNewPassword.setEditable(true);
            txtConfirmPassword.setEditable(true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
