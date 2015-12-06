/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import epg.error.ErrorHandler;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.Page;
import epg.model.VideoComponent;
import java.io.File;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author BunnyRailgun
 */
public class VideoComponentView extends ComponentView{
    String videoPath;
    String videoFileName;
    int width;
    int height;
    String caption;
    Media video;
    MediaPlayer videoPlayer;
    MediaView videoViewer;
    
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
    
    VideoComponent videoComponent;
    
    public VideoComponentView(Page pageToEdit, VideoComponent videoComponent){
        super();
        this.videoComponent = videoComponent;
        updateVideo();
        caption = videoComponent.getCaption();
        
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
        fontSelection.getSelectionModel().select(videoComponent.getCaptionFont());
        
        styleSelection = new ComboBox(styles);
        styleSelection.getSelectionModel().select("None");
        
        sizeSelection = new ComboBox(sizes);
        sizeSelection.getSelectionModel().select("12");
        
        fontControl.getChildren().addAll(fontLabel,fontSelection);
        styleControl.getChildren().addAll(styleLabel,styleSelection);
        sizeControl.getChildren().addAll(fontSizeLabel,sizeSelection);
        
        fontControls.getChildren().addAll(fontControl,styleControl,sizeControl);
        fontControls.setAlignment(Pos.CENTER);
        captionControls = new VBox();
        Label captionLabel = new Label("CAPTION:");
        Label captionText = new Label(caption);
        captionControls.getChildren().addAll(captionLabel,fontControls,captionText);
        
        //PUT ON HBOX
        hBox = new HBox();
        System.out.println("MAKING VIDEO");
        videoPlayer.setOnReady(new Runnable(){
            
            @Override
            public void run() {
                videoPlayer.stop();
                int width = videoPlayer.getMedia().getWidth();
                int height = videoPlayer.getMedia().getHeight();
                //RESZIZE
                double scaledWidth = 300;
                double perc = scaledWidth / width;
                double scaledHeight = height * perc;
                videoViewer.setFitWidth(scaledWidth);
                videoViewer.setFitHeight(scaledHeight);
            }
            
        });
        
        hBox.getChildren().addAll(videoViewer,captionControls);
        getChildren().add(hBox);
        
        initVideoComponentHandlers();
        
    }
    public void updateVideo(){
        System.out.println("UPDATING VIDEO");
        videoPath = videoComponent.getVideoPath() + videoComponent.getVideoFileName();
        File file = new File(videoPath);
        try{
            URL fileURL = file.toURI().toURL();
            video = new Media(fileURL.toExternalForm());
            videoPlayer = new MediaPlayer(video);
            videoViewer = new MediaView(videoPlayer);
        }catch(Exception e){
            ErrorHandler eH = new ErrorHandler();
            eH.processError("ERROR IN SHOWING VIDEO");
        }
    }
    public void initVideoComponentHandlers(){
        fontSelection.setOnAction(e -> {
            String font = (String)fontSelection.getSelectionModel().getSelectedItem();
            videoComponent.setCaptionFont(font);
            System.out.println("FONT UPDATED TO : " + font);
        });
        styleSelection.setOnAction(e ->{
            String style = (String)styleSelection.getSelectionModel().getSelectedItem();
            videoComponent.setCaptionStyle(style);
            System.out.println("STYLE UPDATED TO : " + style);
        });
        sizeSelection.setOnAction(e->{
            int size = Integer.getInteger((String)sizeSelection.getSelectionModel().getSelectedItem());
            videoComponent.setCaptionSize(size);
            System.out.println("SIZE CHANGED TO " + size);
        });
    }
}
