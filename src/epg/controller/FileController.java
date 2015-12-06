/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.LanguagePropertyType;
import static epg.StartupConstants.PATH_PORTFOLIOS;
import epg.error.ErrorHandler;
import epg.file.PortfolioFileManager;
import static epg.file.SlideShowFileManager.JSON_EXT;
import epg.model.PortfolioModel;
import epg.model.SlideShowModel;
import epg.view.PortfolioGeneratorView;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class FileController {

    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;

    // THE APP UI
    private PortfolioGeneratorView ui;
    
    // THIS GUY KNOWS HOW TO READ AND WRITE SLIDE SHOW DATA
    private PortfolioFileManager portfolioIO;

    /**
     * This default constructor starts the program without a slide show file being
     * edited.
     *
     * @param initPortfolioGeneratorIO The object that will be reading and writing slide show
     * data.
     */
    public FileController(PortfolioGeneratorView initUI, PortfolioFileManager initPortfolioIO) {
        // NOTHING YET
        saved = true;
	ui = initUI;
        portfolioIO = initPortfolioIO;
    }
    
    public void markAsEdited() {
        saved = false;
        ui.updateSiteToolbarControls(saved);
    }

    /**
     * This method starts the process of editing a new slide show. If a pose is
     * already being edited, it will prompt the user to save it first.
     */
    public void handleNewPortfolioRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                PortfolioModel portfolio = ui.getPortfolio();
		portfolio.reset();
                saved = false;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                ui.updateSiteToolbarControls(saved);
                ui.removePageSettings();
                ui.reloadPageEditorPane();
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            //eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }

    /**
     * This method lets the user open a slideshow saved to a file. It will also
     * make sure data for the current slideshow is not lost.
     */
    public void handleLoadPortfolioRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A POSE
            if (continueToOpen) {
                // GO AHEAD AND PROCEED MAKING A NEW POSE
                promptToOpen();
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            //eH.processError(LanguagePropertyType.ERROR_DATA_FILE_LOADING);
        }
    }

    public boolean handleSavePortfolioRequest() {
        try{
            PortfolioModel portfolioToSave = ui.getPortfolio();
            
                //SAVE to FILE
                System.out.println("SAVING PORTFOLIO" + portfolioToSave.getTitle() + "...");
                portfolioIO.savePortfolio(portfolioToSave);
                
                saved = true;
                
                ui.updateSiteToolbarControls(saved);
                System.out.println("PORFOLIO SAVED AS " + portfolioToSave.getTitle());
                return true;
        }catch(IOException ioe){
            System.out.println("IO ERROR");
            return false;
        }
    }
    
    public void handleSaveAsPortfolioRequest(){
        try {
            FileChooser portfolioFileChooser = new FileChooser();
            portfolioFileChooser.setInitialDirectory(new File(PATH_PORTFOLIOS));
            portfolioFileChooser.setInitialFileName(ui.getPortfolio().getTitle() + JSON_EXT);
            FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.JSON");
            portfolioFileChooser.getExtensionFilters().add(jsonFilter);
            File f = portfolioFileChooser.showSaveDialog(null);
            //UPDATE TITLE
            ui.getPortfolio().setTitle(f.getName().substring(0,f.getName().lastIndexOf(JSON_EXT)));
            System.out.println("NEW TITLE: " + ui.getPortfolio().getTitle());
            portfolioIO.savePortfolio(ui.getPortfolio());
            saved=true;
            ui.updateSiteToolbarControls(saved);
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handleExitRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            System.out.println("ERROR");
        }
    }

    
    private boolean promptToSave() throws IOException {
        Stage dialog = new Stage();
        dialog.setWidth(700);
        dialog.setHeight(300);
        VBox dialogOptions = new VBox();
        Label save = new Label("Would you like to save?");
        HBox confirm = new HBox();
        Button ok = new Button("YES");
        Button cancel = new Button("NO");
        confirm.getChildren().addAll(ok,cancel);
        dialogOptions.getChildren().addAll(save,confirm);
        Scene scene = new Scene(dialogOptions);
        dialog.setScene(scene);
        
        ok.setOnAction(e->{
            handleSavePortfolioRequest();
        });
        
        return false;
    }

    
    private void promptToOpen() {
        FileChooser portfolioFileChooser = new FileChooser();
        portfolioFileChooser.setInitialDirectory(new File(PATH_PORTFOLIOS));
        File selectedFile = portfolioFileChooser.showOpenDialog(ui.getWindow());
        
        if(selectedFile != null){
            try{
                PortfolioModel portfolioToLoad = ui.getPortfolio();
                portfolioIO.loadPortfolio(portfolioToLoad, selectedFile.getAbsolutePath());
                ui.reloadPageEditorPane();
                saved = true;
                ui.updateSiteToolbarControls(saved);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    
    public void markFileAsNotSaved() {
        saved = false;
    }

    public boolean isSaved() {
        return saved;
    }
}