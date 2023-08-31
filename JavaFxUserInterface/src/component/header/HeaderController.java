package component.header;

import component.mainapp.AppController;
import engine.api.Engine;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HeaderController {


    @FXML private AppController appController;
    @FXML public Button ButtonResults;
    @FXML public Label LabelFilePath;
    @FXML public Button ButtonNewExec;
    @FXML
    public Button ButtonDetails;
    @FXML
    public Button ButtonLoadFile;
    @FXML
    public Button ButtonQueueManage;

    private SimpleStringProperty filePath;
    private SimpleBooleanProperty isNewExecutionPressed;
    private SimpleBooleanProperty isDetails;
    private SimpleBooleanProperty isQueueManage;
    private SimpleBooleanProperty isFileSelected;

    private Engine engine;
    private Stage primaryStage;



    public HeaderController() {
        this.filePath = new SimpleStringProperty("");
        this.isNewExecutionPressed = new SimpleBooleanProperty(false);
        this.isDetails = new SimpleBooleanProperty(false);
        this.isQueueManage = new SimpleBooleanProperty(false);
        this.isFileSelected = new SimpleBooleanProperty(false);
    }

    public void bindFileNameToEngine() {
        appController.setFileNameToEngine(filePath);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        LabelFilePath.textProperty().bind(filePath);
        ButtonDetails.disableProperty().bind(isFileSelected.not());
        ButtonNewExec.disableProperty().bind(isFileSelected.not());
        ButtonResults.disableProperty().bind(isNewExecutionPressed.not());
    }

    @FXML
    private void onClickLoadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Flows XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null)
            return;
        String absolutePath = selectedFile.getAbsolutePath();
        filePath.set(absolutePath);
        isFileSelected.set(true);
        try {
            //appController.readWorld();
        }
        catch (IllegalArgumentException e) {
            filePath.set(e.getMessage());
            isFileSelected.set(false);
        }
    }

    @FXML
    private void onNewExecutionClicked(ActionEvent event) throws IOException {
        appController.moveToNewExecutionScreen();
        isNewExecutionPressed.set(true);
    }


    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void onDetailsClicked(ActionEvent actionEvent) {
        appController.onDetailsChosen();
    }
}
