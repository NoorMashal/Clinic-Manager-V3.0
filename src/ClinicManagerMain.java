/**
 * @author Noor Mashal
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ClinicManagerMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/clinic-view.fxml")));  /* Load the FXML file */
        primaryStage.setTitle("Clinic Manager"); /* Set the title for the stage */
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); /* Launch the JavaFX application */
    }
}
