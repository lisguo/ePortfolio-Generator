/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;
import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static epg.LanguagePropertyType.TOOLTIP_LOAD_SLIDE_SHOW;
import static epg.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static epg.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static epg.LanguagePropertyType.TOOLTIP_NEW_SLIDE_SHOW;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_SLIDE;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX;
import static epg.StartupConstants.CSS_CLASS_SELECTED_WORKSPACE;
import static epg.StartupConstants.CSS_CLASS_SLIDE_EDITOR_PANE;
import static epg.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static epg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.ICON_ADD_SLIDE;
import static epg.StartupConstants.ICON_LOAD_SLIDE_SHOW;
import static epg.StartupConstants.ICON_MOVE_DOWN;
import static epg.StartupConstants.ICON_MOVE_UP;
import static epg.StartupConstants.ICON_NEW_SLIDE_SHOW;
import static epg.StartupConstants.ICON_REMOVE_SLIDE;
import static epg.StartupConstants.ICON_SAVE_SLIDE_SHOW;
import static epg.StartupConstants.ICON_WINDOW_LOGO;
import static epg.StartupConstants.PATH_DATA;
import static epg.StartupConstants.PATH_ICONS;
import static epg.StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import epg.error.ErrorHandler;
import epg.file.SlideShowFileManager;
import epg.model.Page;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;


/**
 *
 * @author BunnyRailgun
 */
public class SlideShowComponentEditor extends Application{
    // THIS WILL PERFORM SLIDESHOW READING AND WRITING
    SlideShowFileManager fileManager = new SlideShowFileManager();

    // THIS HAS THE FULL USER INTERFACE AND ONCE IN EVENT
    // HANDLING MODE, BASICALLY IT BECOMES THE FOCAL
    // POINT, RUNNING THE UI AND EVERYTHING ELSE
    SlideShowMakerView ui = new SlideShowMakerView(fileManager);

    Page pageToEdit;
    
    public SlideShowComponentEditor(Page pageToEdit){
        this.pageToEdit = pageToEdit;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // SET THE WINDOW ICON
	String imagePath = PATH_ICONS + ICON_WINDOW_LOGO;
	File file = new File(imagePath);
	
	// GET AND SET THE IMAGE
	//URL fileURL = file.toURI().toURL();
        System.out.println(file.getCanonicalPath());
	Image windowIcon = new Image("file:"+file.getCanonicalPath());
	primaryStage.getIcons().add(windowIcon);

	String languageCode = "EN";

	
        // LOAD APP SETTINGS INTO THE GUI AND START IT UP
        boolean success = loadProperties(languageCode);
        if (success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = "Slide Show Maker";

	    // NOW START THE UI IN EVENT HANDLING MODE
	    ui.startUI(primaryStage, appTitle ,pageToEdit);
	} // THERE WAS A PROBLEM LOADING THE PROPERTIES FILE
	else {
	    // LET THE ERROR HANDLER PROVIDE THE RESPONSE
	    ErrorHandler errorHandler = ui.getErrorHandler();
	    errorHandler.processError(LanguagePropertyType.ERROR_DATA_FILE_LOADING);
	    System.exit(0);
	}
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     * 
     * @return true if the properties file was loaded successfully, false otherwise.
     */
    public boolean loadProperties(String languageCode) {
        try {
	    // FIGURE OUT THE PROPER FILE NAME
	    String propertiesFileName = "properties_" + languageCode + ".xml";

	    // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_PROPERTIES_FILE_LOADING);
            return false;
        }        
    }

    /**
     * This is where the application starts execution. We'll load the
     * application properties and then use them to build our user interface and
     * start the window in event handling mode. Once in that mode, all code
     * execution will happen in response to user requests.
     *
     * @param args This application does not use any command line arguments.
     */
    public static void main(String[] args) {
	launch(args);
    }
}