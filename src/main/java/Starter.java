import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login-signup-view.fxml"))));
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dahboard-view.fxml"))));
//        stage.setTitle("Clothify - Login");
        stage.setTitle("Clothify - Dashboard");
        stage.setResizable(false);
        stage.show();
    }
}
