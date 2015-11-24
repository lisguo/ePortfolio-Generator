/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import static epg.StartupConstants.STYLE_SHEET_UI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class VideoComponentEditor extends Stage{
    Scene scene;
    VBox vBox;
    Label videoName;
    TextField caption;
    Button okButton;
    HBox videoSelection;
    Button selectVideo;
    Label captionLabel;
    
    //SIZE
    Label displaySize;
    TextField widthField;
    Label x;
    TextField heightField;
    public VideoComponentEditor(){
        setTitle("Add an Image Component");
        vBox = new VBox();
        videoName = new Label("videoName.mp4");
        videoSelection = new HBox();
        caption = new TextField();
        okButton = new Button("OK");
        selectVideo = new Button("Select Video...");
        caption.setMinHeight(100);
        captionLabel = new Label("Caption:");
        videoSelection.getChildren().addAll(selectVideo, videoName);
        videoSelection.setAlignment(Pos.CENTER);
        
        //SIZE
        displaySize = new Label("Size (Width x Height): ");
        HBox size = new HBox();
        widthField = new TextField();
        widthField.setPrefWidth(80);
        x = new Label("x");
        heightField = new TextField();
        heightField.setPrefWidth(80);
        size.getChildren().addAll(widthField, x , heightField);
        size.setAlignment(Pos.CENTER);
        
        vBox.getChildren().addAll(videoSelection, captionLabel,caption, 
                displaySize, size, okButton);
        vBox.setAlignment(Pos.CENTER);
        
        scene = new Scene(vBox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        videoName.getStyleClass().add(CSS_SMALL_LABEL);
        vBox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        setScene(scene);
        
        okButton.setOnAction(e ->{
            close();
        });
    }
    
}
