/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_SMALL_LABEL;
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
import static epg.StartupConstants.STYLE_SHEET_UI;
import javafx.event.ActionEvent;

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
    Button continueButton;
    VBox nextDialog;
    public TextComponentEditor(PortfolioModel portfolio){
        vbox = new VBox();
        hBox = new HBox();
        text = new TextField();
        
        
        //SELECT A TEXT TYPE
        textType = new Label("Text Type: ");
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Header");
        types.add("Paragraph");
        types.add("List");
        textTypeComboBox = new ComboBox(types);
        textTypeComboBox.getSelectionModel().select("Header");
        textTypeComboBox.getStyleClass().add(CSS_SMALL_LABEL);
        continueButton = new Button("Continue");
        scene = new Scene(vbox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        setTitle("Add a Text Component");
        hBox.getChildren().addAll(textType, textTypeComboBox);
        hBox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hBox, continueButton);
        vbox.setAlignment(Pos.CENTER);
        setScene(scene);
        okButton = new Button("OK");
        vbox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        
        okButton.setOnAction(e ->{
            close();
        });
        continueButton.setOnAction(e->{
            String type = (String)textTypeComboBox.getSelectionModel().getSelectedItem();
            nextDialog = new VBox();
            nextDialog.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
            if(type.equals("Header")){
                Label header = new Label("Header:");
                nextDialog.getChildren().addAll(header,text,okButton);
            }
            else if(type.equals("Paragraph")){
                Label paragraph = new Label("Paragraph:");
                //FONTS
                ObservableList<String> fonts = FXCollections.observableArrayList();
                fonts.add("Righteous");
                fonts.add("Lora");
                fonts.add("Roboto Slab");
                fonts.add("Rock Salt");
                ComboBox fontSelection = new ComboBox(fonts);
                fontSelection.getSelectionModel().select("Righteous");
                
                text.setAlignment(Pos.TOP_LEFT);
                text.setPrefHeight(200);
                nextDialog.getChildren().addAll(paragraph, fontSelection, 
                        text, okButton);
            }
            else{
                Label list = new Label("List:");
                Button add = new Button("Add");
                Button remove = new Button("Remove");
                HBox addOrRemove = new HBox();
                addOrRemove.getChildren().addAll(add,remove);
                TextField text2 = new TextField();
                nextDialog.getChildren().addAll(list,addOrRemove,text,text2);
                addOrRemove.setAlignment(Pos.CENTER);
                
            }
            nextDialog.setAlignment(Pos.CENTER);
            scene = new Scene(nextDialog);
            scene.getStylesheets().add(STYLE_SHEET_UI);
            setScene(scene);
        });
    }
}
