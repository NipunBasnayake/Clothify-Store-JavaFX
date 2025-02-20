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
        if (txtName.getText().isEmpty() || cmbRole.getSelectionModel().getSelectedItem().toString().trim() == null || txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty() || txtConfirmPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Create User");
            alert.setHeaderText("Fields cannot be empty");
            alert.show();
        } else {
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                User newUser = new User(
                        0,
                        txtName.getText().trim(),
                        txtEmail.getText().trim(),
                        txtPassword.getText().trim(),
                        cmbRole.getSelectionModel().getSelectedItem().toString().trim(),
                        "nullDate"
                );
                boolean isUserSaved = loginSignupService.addNewUser(newUser);
                if (isUserSaved) {
                    Stage stage = (Stage) txtName.getScene().getWindow();
                    stage.close();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Create User");
                    alert.setHeaderText("Error Creating User");
                    alert.show();
                }
            }
        }
    }
}
