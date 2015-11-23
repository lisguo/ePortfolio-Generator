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
public class ImageComponentEditor extends Stage {
    Scene scene;
    VBox vBox;
    HBox imageSelection;
    Button selectImage;
    Label imageName;
    Label captionLabel;
    TextField caption;
    Button okButton;
    
    public ImageComponentEditor(PortfolioModel portfolio){
        vBox = new VBox();
        imageName = new Label();
        imageSelection = new HBox();
        caption = new TextField();
        okButton = new Button("OK");
        selectImage = new Button("Select Image");
        caption.setMinHeight(100);
        captionLabel = new Label("Caption:");
        imageSelection.getChildren().addAll(selectImage, imageName);
        vBox.getChildren().addAll(imageSelection, captionLabel,caption, okButton);
        
        scene = new Scene(vBox);
        setScene(scene);
    }
}
