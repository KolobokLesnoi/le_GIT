import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setTitle("le_GIT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}