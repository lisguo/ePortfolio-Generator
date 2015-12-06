/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import static epg.StartupConstants.PATH_IMAGES;
import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.error.ErrorHandler;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.ImageComponent;
import epg.model.Page;
import epg.model.PortfolioModel;
import java.io.File;
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
import javafx.stage.FileChooser;
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
    Label captionFieldLabel;
    TextField captionField;
    Button okButton;
    Label displaySize;
    TextField widthField;
    Label x;
    TextField heightField;
    ComboBox imageFloat;
    
    //Image Component
    String imgPath;
    String imgFileName;
    String caption;
    int width;
    int height;
    String floatOption;
    
    Page pageToEdit;
    PortfolioModel portfolio;
    public ImageComponentEditor(PortfolioModel portfolio, Page page){
        this.portfolio = portfolio;
        pageToEdit = page;
        imgFileName = "";
        setTitle("Add an Image Component");
        vBox = new VBox();
        imageSelection = new HBox();
        captionField = new TextField();
        okButton = new Button("OK");
        selectImage = new Button("Select Image...");
        imageName = new Label(imgFileName);
        captionField.setMinHeight(100);
        captionFieldLabel = new Label("Caption:");
        imageSelection.getChildren().addAll(selectImage, imageName);
        imageSelection.setAlignment(Pos.CENTER);
        
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
        
        //FLOAT
        ObservableList<String> floatOptions = FXCollections.observableArrayList();
        floatOptions.add("Float Left");
        floatOptions.add("Float Right");
        floatOptions.add("Float Neither");
        imageFloat = new ComboBox(floatOptions);
        imageFloat.getSelectionModel().select("Float Neither");
        
        vBox.getChildren().addAll(imageSelection, captionFieldLabel,captionField, 
                displaySize, size, imageFloat, okButton);
        vBox.setAlignment(Pos.CENTER);
        
        scene = new Scene(vBox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        imageName.getStyleClass().add(CSS_SMALL_LABEL);
        vBox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        setScene(scene);
        
        initHandlers();
    }
    
    public void initHandlers(){
        selectImage.setOnAction(e ->{
            //OPEN IMAGE SELECTION
            FileChooser imageFileChooser = new FileChooser();
	
            // SET THE STARTING DIRECTORY
            File dir = new File(PATH_PORTFOLIOS + portfolio.getTitle() + SLASH + "images" + SLASH);
            dir.mkdirs();
            imageFileChooser.setInitialDirectory(dir);

            // LET'S ONLY SEE IMAGE FILES
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
            imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);

            // LET'S OPEN THE FILE CHOOSER
            File file = imageFileChooser.showOpenDialog(null);
            if (file != null) {
                imgPath = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
                imgFileName = file.getName();
                updateImageName();
            }
        });
        
        okButton.setOnAction(e ->{
            //IF NO IMAGE SELECTED SHOW ERROR
            if(imgFileName.equals("")){
                ErrorHandler eH = new ErrorHandler();
                eH.processError("NO IMAGE SELECTED");
            }
            else{
                //GET CAPTION AND SIZE
                caption = captionField.getText();
                try{
                    width = Integer.parseInt(widthField.getText());
                    height = Integer.parseInt(heightField.getText());
                    
                    //GET FLOAT
                    floatOption = (String) imageFloat.getSelectionModel().getSelectedItem();

                    //PUT IN AN IMAGE COMPONENT AND ADD TO PAGE
                    ImageComponent imageComponent = new ImageComponent(
                            imgPath, imgFileName, caption, width, height, floatOption);
                    //SET FONT AS PAGE FONT
                    imageComponent.setCaptionFont(pageToEdit.getPageFont());
                    pageToEdit.addImageComponent(imageComponent);
                    System.out.println("IMAGE COMPONENT ADDED");
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
    public void updateImageName(){
        imageName.setText(imgFileName);
    }
}
