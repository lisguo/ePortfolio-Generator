/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import epg.model.PortfolioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponentEditor extends Stage{
    Scene scene;
    VBox vbox;
    HBox hBox;
    Label textType;
    ComboBox textTypeComboBox;
    TextField text;
    Button okButton;
    
    public TextComponentEditor(PortfolioModel portfolio){
        vbox = new VBox();
        hBox = new HBox();
        text = new TextField();
        
        text.setAlignment(Pos.TOP_LEFT);
        text.setPrefHeight(200);
        
        okButton = new Button("OK");
        
        textType = new Label("Text Type: ");
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Header");
        types.add("Paragraph");
        types.add("List");
        textTypeComboBox = new ComboBox(types);
        textTypeComboBox.getSelectionModel().select("Header");
        
        hBox.getChildren().addAll(textType, textTypeComboBox);
        hBox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hBox, text, okButton);
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        scene = new Scene(vbox);
        setTitle("Add a Text Component");
        setScene(scene);
        
        okButton.setOnAction(e ->{
            close();
        });
    }
}
