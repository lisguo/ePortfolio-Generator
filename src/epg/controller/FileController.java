/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.file.PortfolioFileManager;
import epg.model.PortfolioModel;
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
        //ui.updateToolbarControls(saved);
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
    public void handleLoadPortfolioGeneratorRequest() {
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

    /**
     * This method will save the current slideshow to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     */
    public boolean handleSavePortfolioGeneratorRequest() {
       return true;
    }

    /**
     * This method shows the current slide show in a separate window.
     */
    public void handleViewPortfolioGeneratorRequest() throws IOException, URISyntaxException {
        
    }

     /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     */
    public void handleExitRequest() {
        
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * pose, or opening another pose, or exiting. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is retuned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave() throws IOException {
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
       
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the pose is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current pose has been saved
     * since it was last editing. If the current file matches the pose data,
     * we'll return true, otherwise false.
     *
     * @return true if the current pose is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }

    public void processNewPortfolioRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}