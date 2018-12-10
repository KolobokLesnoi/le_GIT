import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.SearchRepository;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;

public class ContentController {

    private ObservableList<LeGITRepository> listRepositories;
    private ContextMenu contextMenu = new ContextMenu(new MenuItem("copy git url"));

    @FXML
    private ImageView personPhoto;
    @FXML
    private Text personName;
    @FXML
    private Hyperlink personBio;
    @FXML
    private TextField searchField;

    @FXML
    TableView personRepositories;
    @FXML
    private TableColumn<LeGITRepository, Integer> idColumn;
    @FXML
    private TableColumn<LeGITRepository, String> nameColumn;
    @FXML
    private TableColumn<LeGITRepository, String> descriptionColumn;
    @FXML
    private TableColumn<LeGITRepository, Integer> watchedColumn;
    @FXML
    private TableColumn<LeGITRepository, Integer> starsColumn;
    @FXML
    private TableColumn<LeGITRepository, Integer> forksColumn;




    @FXML
    public void initialize(){
        personPhoto.setImage(new Image(LoginController.user.getAvatarUrl(),true));
        personName.setText(LoginController.user.getName());
        personBio.setText(LoginController.user.getBlog());

        idColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,String>("Name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,String>("Description"));
        watchedColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,Integer>("Watched"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,Integer>("Stars"));
        forksColumn.setCellValueFactory(new PropertyValueFactory<LeGITRepository,Integer>("Forks"));

        load();

    }


    public void searchButton(ActionEvent actionEvent) {
        search();
    }
    public void searchEnterButton(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) search();
        if(keyEvent.getCode() == KeyCode.F5) load();
    }

    private void search(){
        try {
            List<SearchRepository> searchRepository = LoginController.repositoryService.searchRepositories(searchField.getText());
            listRepositories = FXCollections.observableArrayList();
            for(int i = 0; i<searchRepository.size(); i++){
                listRepositories.add(new LeGITRepository(i+1,searchRepository.get(i)));
            }
            personRepositories.setItems(listRepositories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadButton(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.F5) load();
    }

    private void load(){
        try {
            // обнуление списка, заполнение списка и заполение таблицы новым списком
            listRepositories = FXCollections.observableArrayList();
            initListRepositories(LoginController.repositoryService.getRepositories());
            personRepositories.setItems(listRepositories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListRepositories(List<Repository> list){
        for(int i = 0; i<list.size(); i++){
            listRepositories.add(new LeGITRepository(i+1,list.get(i)));
        }
    }

    public void copyButton(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.SECONDARY) {

            contextMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // получение выбраного репозитория в таблице и копирование его url в буфер
                    SelectionModel selectionModel = personRepositories.getSelectionModel();
                    LeGITRepository leGITRepository = (LeGITRepository) selectionModel.getSelectedItem();
                    StringSelection stringSelection = new StringSelection(leGITRepository.getCloneGitURL());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                }
            });
            // отоброжение контекста копирования в координатах мышки
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point point = pointerInfo.getLocation();
            contextMenu.show((Node) mouseEvent.getSource(),point.getX(),point.getY());

        }
    }

    public void exitButton(ActionEvent actionEvent) {
        try {
            ((Node)actionEvent.getSource()).getScene().getWindow().hide(); // выход из основного окна

            // создание и загрузка окна входа
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(parent);
            stage.setMinWidth(300);
            stage.setMinHeight(300);
            stage.setTitle("le_GIT");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
