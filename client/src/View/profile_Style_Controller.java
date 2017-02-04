/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClientController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 *
 * @author bo
 */
public class profile_Style_Controller implements Initializable {
    
    @FXML
    private Label label;
   
    @FXML private ComboBox comboStatus;
    @FXML ObservableList<String> states=FXCollections.observableArrayList("available","busy","away");
    ClientController controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            // TODO
            comboStatus.getItems().addAll(states);
            comboStatus.getSelectionModel().selectFirst();

    }
    public void setCon(ClientController  con){
        this.controller= con;
        
    }
}
