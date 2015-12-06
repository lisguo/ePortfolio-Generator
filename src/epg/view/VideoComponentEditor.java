/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.error.ErrorHandler;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.VideoComponent;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class VideoComponentEditor extends Stage{
    Scene scene;
    VBox vBox;
    Label videoName;
    TextField captionField;
    Button okButton;
    HBox videoSelection;
    Button selectVideo;
    Label captionFieldLabel;
    
    //SIZE
    Label displaySize;
    TextField widthField;
    Label x;
    TextField heightField;
    
    PortfolioModel portfolio;
    Page pageToEdit;
    
    String videoPath;
    String videoFileName;
    String caption;
    int width;
    int height;
    
    public VideoComponentEditor(PortfolioModel portfolio, Page page){
        this.portfolio = portfolio;
        pageToEdit = page;
        
        videoFileName = "";
        setTitle("Add a Video Component");
        vBox = new VBox();
        videoName = new Label("");
        videoSelection = new HBox();
        captionField = new TextField();
        okButton = new Button("OK");
        selectVideo = new Button("Select Video...");
        captionField.setMinHeight(100);
        captionFieldLabel = new Label("Caption:");
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
        
        vBox.getChildren().addAll(videoSelection, captionFieldLabel,captionField, 
                displaySize, size, okButton);
        vBox.setAlignment(Pos.CENTER);
        
        scene = new Scene(vBox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        videoName.getStyleClass().add(CSS_SMALL_LABEL);
        vBox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        setScene(scene);
        
        initHandlers();
        
    }
    public void initHandlers(){
        selectVideo.setOnAction(e ->{
            //OPEN IMAGE SELECTION
            FileChooser imageFileChooser = new FileChooser();
	
            // SET THE STARTING DIRECTORY
            File videoFile = new File(PATH_PORTFOLIOS + portfolio.getTitle() + SLASH + "videos" + SLASH);
            videoFile.mkdirs();
            imageFileChooser.setInitialDirectory(videoFile);

            // LET'S ONLY SEE IMAGE FILES
            FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.MP4");
            FileChooser.ExtensionFilter flvFilter = new FileChooser.ExtensionFilter("FLV files (*.flv)", "*.FLV");
            imageFileChooser.getExtensionFilters().addAll(mp4Filter, flvFilter);

            // LET'S OPEN THE FILE CHOOSER
            File file = imageFileChooser.showOpenDialog(null);
            if (file != null) {
                videoPath = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
                videoFileName = file.getName();
                updateVideoName();
            }
        });
        
        okButton.setOnAction(e ->{
            //IF NO IMAGE SELECTED SHOW ERROR
            if(videoFileName.equals("")){
                ErrorHandler eH = new ErrorHandler();
                eH.processError("NO VIDEO SELECTED");
            }
            else{
                //GET CAPTION AND SIZE
                caption = captionField.getText();
                try{
                    width = Integer.parseInt(widthField.getText());
                    height = Integer.parseInt(heightField.getText());
                    
                    //PUT IN AN IMAGE COMPONENT AND ADD TO PAGE
                    VideoComponent videoComponent = new VideoComponent(
                        videoPath, videoFileName, caption, width, height);
                    //SET FONT AS PAGE FONT
                    videoComponent.setCaptionFont(pageToEdit.getPageFont());
                    pageToEdit.addVideoComponent(videoComponent);
                    System.out.println("VIDEO COMPONENT ADDED");
                    close();
                }catch(Exception ex){ 
                //IF SIZES ARE NOT INTEGERS OR EMPTY
                    //ERROR DIALOG
                    ErrorHandler eH = new ErrorHandler();
                    eH.processError("INVALID SIZES");
                }
            }
        });
    }
    public void updateVideoName(){
        videoName.setText(videoFileName);
    }
    
}
