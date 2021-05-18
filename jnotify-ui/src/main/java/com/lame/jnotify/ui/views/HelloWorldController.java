package com.lame.jnotify.ui.views;

import com.lame.jnotify.analy.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelloWorldController implements Initializable {

    @FXML
    public ListView fileList;

    @FXML
    public TextField scanDir;

    @FXML
    public Button scanProject;
    public Label scanResult;

    @FXML
    private Button fileSearchBut;

    @FXML
    private TextField fileSearchInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hallo World Controller is initialize");
//        ObservableList<String> items = FXCollections.observableArrayList (
//                "A", "B", "C", "D","A", "B", "C", "D","A", "B", "C", "D","A", "B", "C", "D");
//        fileList.setItems(items);
    }

    @FXML
    public void scanProject(MouseEvent event) {
        String text = scanDir.getText();
        if (text == null || text.equals("")) {
            scanResult.setText("扫描失败");
            System.out.println("error");
            return;
        }
        List<Project> projects = Project.scan(text);
        ObservableList<String> items = FXCollections.observableArrayList(projects.stream().map(Project::getBasePkg).collect(Collectors.toList()));
        fileList.setItems(items);
    }

    @FXML public void hallo(MouseEvent event) {
        fileList.getItems().add(System.currentTimeMillis() + "");
        fileSearchInput.setText(System.currentTimeMillis() + "");
//        fileSearchBut.setText(System.currentTimeMillis() + "");
//        System.out.println("Hallo current timestamp is " + System.currentTimeMillis());
    }
}
