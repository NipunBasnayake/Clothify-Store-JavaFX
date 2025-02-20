package controller;

import com.google.inject.Inject;
import dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import service.custom.LoginSignupService;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserAccountController implements Initializable {

    @Inject
    LoginSignupService loginSignupService;

    @FXML
    private ComboBox cmbRole;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbRole.getItems().addAll("Cashier", "Admin");
    }

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) {
        if (txtName.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtPassword.getText().trim().isEmpty() ||
                txtConfirmPassword.getText().trim().isEmpty() ||
                cmbRole.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Create User");
            alert.setHeaderText("All fields must be filled!");
            alert.show();
            return; // Stop further execution
        }

        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Password Mismatch");
            alert.setHeaderText("Passwords do not match!");
            alert.show();
            return;
        }

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("ClothifySecureKey");
        String encryptedPassword = textEncryptor.encrypt(txtPassword.getText().trim());

//        // Decrypting the password
//        String decryptedPassword = textEncryptor.decrypt(encryptedPassword);
//        System.out.println("Decrypted Password: " + decryptedPassword);

        User newUser = new User(
                0,
                txtName.getText().trim(),
                txtEmail.getText().trim(),
                encryptedPassword,
                cmbRole.getSelectionModel().getSelectedItem().toString().trim(),
                "nullDate"
        );

        boolean isUserSaved = loginSignupService.addNewUser(newUser);
        if (isUserSaved) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Created Successfully");
            alert.setHeaderText("New user has been created successfully!");
            alert.show();

            // Close window after success
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Create User");
            alert.setHeaderText("An error occurred while creating the user.");
            alert.show();
        }
    }

}
