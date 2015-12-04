/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.LanguagePropertyType;
import epg.error.ErrorHandler;
import epg.file.PortfolioFileManager;
import epg.model.PortfolioModel;
import epg.model.SlideShowModel;
import epg.view.PortfolioGeneratorView;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.stage.FileChooser;

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
    public void handleExitRequest() {
        
    }

    
    private boolean promptToSave() throws IOException {
        return true;
    }

    
    private void promptToOpen() {
       
    }

    
    public void markFileAsNotSaved() {
        saved = false;
    }

    public boolean isSaved() {
        return saved;
    }
}