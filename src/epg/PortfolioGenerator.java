package epg;

//Constants
import static epg.LanguagePropertyType.TITLE_WINDOW;
import static epg.StartupConstants.PATH_ICONS;
import static epg.StartupConstants.ICON_WINDOW_LOGO;
import static epg.StartupConstants.PATH_DATA;
import static epg.StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;

import epg.view.PortfolioGeneratorView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;

public class PortfolioGenerator extends Application{
    PortfolioGeneratorView ui;

    public PortfolioGenerator() throws IOException {
        this.ui = new PortfolioGeneratorView();
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //SET WINDOW ICON
        String imagePath = PATH_ICONS + ICON_WINDOW_LOGO;
        File file = new File(imagePath);
        Image windowIcon = new Image("file:" + file.getCanonicalPath());
        primaryStage.getIcons().add(windowIcon);
        
        //SEt LANGUAGE AS EN
        String languageCode = "EN";
        
        //LOAD SETTINGS TO HUI
        boolean success = loadProperties(languageCode);
        if(success){
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(TITLE_WINDOW);
            
            //START UI
            ui.startUI(primaryStage, appTitle);
        }
        else{
            System.out.println("ERROR IN LOADING PROPERTIES FILE!");
            System.exit(0);
        }
    }
    
    public boolean loadProperties(String languageCode){
        try{
            String propertiesFileName = "properties_" + languageCode + ".xml";
            
            //LOAD SETTINGS
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
        } catch (InvalidXMLFileFormatException ixmlffe){
            //ErrorHandler eH = ui.getErrorHandler();
            System.out.println("Error in loading XML file");
            return false;
        }
    }
    
    public static void main(String[] args){
        launch(args);
    }
}