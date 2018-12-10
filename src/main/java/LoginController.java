import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

public class LoginController {

    public static RepositoryService repositoryService;
    public static User user;

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text textErrorLogin;

    private void login(Event event){
        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setCredentials(loginField.getText(),passwordField.getText());

        UserService userService = new UserService(gitHubClient);
        try {
            user = userService.getUser(); // даст ошибку если не верный логин или пароль
            repositoryService = new RepositoryService(gitHubClient);

            ((Node)event.getSource()).getScene().getWindow().hide(); // закрытие окна входа

            // создание и загрузка основного окна
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("content.fxml"));
            Scene content = new Scene(parent);
            stage.setMinHeight(400);
            stage.setMinWidth(720);
            stage.setTitle("le_GIT");
            stage.setScene(content);
            stage.show();

        } catch (Exception e) {
            // отображение ошибки входа
            textErrorLogin.setVisible(true);
        }
    }

    public void loginButton(ActionEvent actionEvent) {
        login(actionEvent);
    }

    public void enterButton(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            login(keyEvent);
        }
    }
}
