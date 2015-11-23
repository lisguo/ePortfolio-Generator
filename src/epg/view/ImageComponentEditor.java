/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.model.PortfolioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
public class ImageComponentEditor extends Stage {
    Scene scene;
    VBox vBox;
    HBox imageSelection;
    Button selectImage;
    Label imageName;
    Label captionLabel;
    TextField caption;
    Button okButton;
    Label displaySize;
    TextField widthField;
    Label x;
    TextField heightField;
    ComboBox imageFloat;
    
    public ImageComponentEditor(PortfolioModel portfolio){
        setTitle("Add an Image Component");
        vBox = new VBox();
        imageName = new Label("imgName.jpg");
        imageSelection = new HBox();
        caption = new TextField();
        okButton = new Button("OK");
        selectImage = new Button("Select Image...");
        caption.setMinHeight(100);
        captionLabel = new Label("Caption:");
        imageSelection.getChildren().addAll(selectImage, imageName);
        imageSelection.setAlignment(Pos.CENTER);
        
        //SIZE
        displaySize = new Label("Size (Width x Height): ");
        HBox size = new HBox();
        widthField = new TextField();
        widthField.setPrefWidth(20);
        x = new Label("x");
        heightField = new TextField();
        heightField.setPrefWidth(20);
        size.getChildren().addAll(widthField, x , heightField);
        size.setAlignment(Pos.CENTER);
        
        //FLOAT
        ObservableList<String> floatOptions = FXCollections.observableArrayList();
        floatOptions.add("Float Left");
        floatOptions.add("Float Right");
        floatOptions.add("Float Neither");
        imageFloat = new ComboBox(floatOptions);
        imageFloat.getSelectionModel().select("Float Neither");
        
        vBox.getChildren().addAll(imageSelection, captionLabel,caption, 
                displaySize, size, imageFloat, okButton);
        vBox.setAlignment(Pos.CENTER);
        
        scene = new Scene(vBox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        imageName.getStyleClass().add(CSS_SMALL_LABEL);
        vBox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        setScene(scene);
        
        okButton.setOnAction(e ->{
            close();
        });
    }
}
