/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import epg.model.PortfolioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponentEditor extends Stage{
    Scene scene;
    VBox vBox;
    HBox hBox;
    Label textType;
    ComboBox textTypeComboBox;
    TextField text;
    Button okButton;
    
    public TextComponentEditor(PortfolioModel portfolio){
        vBox = new VBox();
        hBox = new HBox();
        text = new TextField();
        okButton = new Button("OK");
        
        textType = new Label("Text Type: ");
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Header");
        types.add("Paragraph");
        types.add("List");
        textTypeComboBox = new ComboBox(types);
        
        text.setMinHeight(500);
        hBox.getChildren().addAll(textType, textTypeComboBox);
        vBox.getChildren().addAll(hBox, text, okButton);
        
        scene = new Scene(vBox);
        setScene(scene);
    }
}
