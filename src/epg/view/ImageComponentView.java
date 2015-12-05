/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import epg.error.ErrorHandler;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.ImageComponent;
import epg.model.Page;
import java.io.File;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class ImageComponentView extends ComponentView{
    String imgPath;
    String imgFileName;
    String caption;
    int width;
    int height;
    String floatOption;
    ImageView imageViewer;
    
    HBox hBox;
    VBox captionControls;
    HBox fontControls;
    Label fontLabel;
    Label styleLabel;
    Label fontSizeLabel;
    ObservableList<String> fonts;
    ObservableList<String> styles;
    ObservableList<String> sizes;
    ComboBox fontSelection;
    ComboBox styleSelection;
    ComboBox sizeSelection;
    
    HBox fontControl,styleControl,sizeControl;
    
    ImageComponent ic;
    public ImageComponentView(Page pageToEdit, ImageComponent imageComponent){
        super();
        ic = imageComponent;
        imageViewer = new ImageView();
        updateImage();
        caption = imageComponent.getCaption();
        
        fontControl = new HBox();
        styleControl = new HBox();
        sizeControl = new HBox();
        
        //NOW FOR CAPTION CONTROLS
        fontControls = new HBox();
        fontLabel = new Label("Font: ");
        styleLabel = new Label("Style: ");
        fontSizeLabel = new Label("Size: ");
        
        fonts = FXCollections.observableArrayList();
        fonts.add("Righteous");
        fonts.add("Lora");
        fonts.add("Roboto Slab");
        fonts.add("Rock Salt");
        
        styles = FXCollections.observableArrayList();
        styles.add("Bold");
        styles.add("Italic");
        styles.add("Underline");
        
        sizes = FXCollections.observableArrayList();
        sizes.add("4");
        sizes.add("6");
        sizes.add("8");
        sizes.add("10");
        sizes.add("12");
        sizes.add("14");
        sizes.add("16");
        sizes.add("18");
        sizes.add("20");
        sizes.add("22");
        sizes.add("24");
        sizes.add("26");
        sizes.add("28");
        sizes.add("30");
        sizes.add("32");
        sizes.add("34");
        sizes.add("36");
        sizes.add("38");
        sizes.add("40");
        sizes.add("42");
        sizes.add("44");
        
        fontSelection = new ComboBox(fonts);
        fontSelection.getSelectionModel().select(imageComponent.getCaptionFont());
        
        styleSelection = new ComboBox(styles);
        styleSelection.getSelectionModel().select("None");
        
        sizeSelection = new ComboBox(sizes);
        sizeSelection.getSelectionModel().select("12");
        
        fontControl.getChildren().addAll(fontLabel,fontSelection);
        styleControl.getChildren().addAll(styleLabel,styleSelection);
        sizeControl.getChildren().addAll(fontSizeLabel,sizeSelection);
        
        fontControls.getChildren().addAll(fontControl,styleControl,sizeControl);
        
        captionControls = new VBox();
        Label captionLabel = new Label("CAPTION:");
        Label captionText = new Label(caption);
        captionControls.getChildren().addAll(captionLabel,fontControls,captionText);
        
        //PUT ON HBOX
        hBox = new HBox();
        hBox.getChildren().addAll(imageViewer,captionControls);
        getChildren().add(hBox);
                
        
    }
    public void updateImage(){
        String imgPath = ic.getImagePath() + SLASH + ic.getImageFileName();
        File file = new File(imgPath);
        try{
            URL fileURL = file.toURI().toURL();
            Image componentImage = new Image(fileURL.toExternalForm());
            imageViewer.setImage(componentImage);
            
            //RESZIZE
            double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
	    double perc = scaledWidth / componentImage.getWidth();
	    double scaledHeight = componentImage.getHeight() * perc;
	    imageViewer.setFitWidth(scaledWidth);
	    imageViewer.setFitHeight(scaledHeight);
        }catch (Exception e){
            ErrorHandler eH = new ErrorHandler();
            eH.processError("ERROR IN SHOWING IMAGE");
        }
    }
}
