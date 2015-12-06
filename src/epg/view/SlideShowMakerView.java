package epg.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
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
import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.LABEL_SLIDESHOW_TITLE;
import static epg.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static epg.LanguagePropertyType.TOOLTIP_EXIT;
import static epg.LanguagePropertyType.TOOLTIP_LOAD_SLIDE_SHOW;
import static epg.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static epg.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static epg.LanguagePropertyType.TOOLTIP_NEW_SLIDE_SHOW;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_SLIDE;
import static epg.LanguagePropertyType.TOOLTIP_SAVE_SLIDE_SHOW;
import static epg.LanguagePropertyType.TOOLTIP_VIEW_SLIDE_SHOW;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW;
import static epg.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static epg.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX;
import static epg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_SELECTED_WORKSPACE;
import static epg.StartupConstants.CSS_CLASS_SLIDESHOW_READY;
import static epg.StartupConstants.CSS_CLASS_SLIDE_EDITOR_PANE;
import static epg.StartupConstants.CSS_CLASS_TITLE_PANE;
import static epg.StartupConstants.CSS_LABEL;
import static epg.StartupConstants.ICON_ADD_SLIDE;
import static epg.StartupConstants.ICON_EXIT;
import static epg.StartupConstants.ICON_LOAD_SLIDE_SHOW;
import static epg.StartupConstants.ICON_MOVE_DOWN;
import static epg.StartupConstants.ICON_MOVE_UP;
import static epg.StartupConstants.ICON_NEW_SLIDE_SHOW;
import static epg.StartupConstants.ICON_REMOVE_SLIDE;
import static epg.StartupConstants.ICON_SAVE_SLIDE_SHOW;
import static epg.StartupConstants.ICON_VIEW_SLIDE_SHOW;
import static epg.StartupConstants.PATH_ICONS;
import static epg.StartupConstants.SSM_STYLE_SHEET_UI;
import epg.controller.SlideShowFileController;
import epg.controller.SlideShowEditController;
import epg.model.Slide;
import epg.model.SlideShowModel;
import epg.error.ErrorHandler;
import epg.file.SlideShowFileManager;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.SlideShowComponent;
import javafx.geometry.Pos;

/**
 * This class provides the User Interface for this application,
 * providing controls and the entry points for creating, loading, 
 * saving, editing, and viewing slide shows.
 * 
 * @author McKilla Gorilla & Lisa Guo (Bunny Railgun)
 */
public class SlideShowMakerView {

    // THIS IS THE MAIN APPLICATION UI WINDOW AND ITS SCENE GRAPH
    Stage primaryStage;
    Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane epgPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newSlideShowButton;
    Button loadSlideShowButton;
    Button saveSlideShowButton;
    Button viewSlideShowButton;
    Button exitButton;
    
    // WORKSPACE
    HBox workspace;

    // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
    VBox slideEditToolbar;
    Button addSlideButton;
    Button removeSlideButton;
    Button moveSlideUpButton;
    Button moveSlideDownButton;
    
    // FOR THE SLIDE SHOW TITLE
    FlowPane titlePane;
    Label titleLabel;
    TextField titleTextField;
    
    // AND THIS WILL GO IN THE CENTER
    ScrollPane slidesEditorScrollPane;
    VBox slidesEditorPane;
    
    //GO ON THE RIGHT
    VBox portfolioSettings;
    Label ready;
    Button addToPortfolio;

    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    SlideShowModel slideShow;

    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
    SlideShowFileManager fileManager;

    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    private ErrorHandler errorHandler;

    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    private SlideShowFileController fileController;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private SlideShowEditController editController;
    
    Page pageToEdit;
    PortfolioModel portfolio;
    /**
     * Default constructor, it initializes the GUI for use, but does not yet
     * load all the language-dependent controls, that needs to be done via the
     * startUI method after the user has selected a language.
     */
    public SlideShowMakerView(SlideShowFileManager initFileManager) {
	// FIRST HOLD ONTO THE FILE MANAGER
	fileManager = initFileManager;
	
	// MAKE THE DATA MANAGING MODEL
	slideShow = new SlideShowModel(this);

	// WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
	errorHandler = new ErrorHandler(this);
    }

    // ACCESSOR METHODS
    public SlideShowModel getSlideShow() {
	return slideShow;
    }

    public Stage getWindow() {
	return primaryStage;
    }

    public ErrorHandler getErrorHandler() {
	return errorHandler;
    }

    /**
     * Initializes the UI controls and gets it rolling.
     * 
     * @param initPrimaryStage The window for this application.
     * 
     * @param windowTitle The title for this window.
     */
    public void startUI(Stage initPrimaryStage, String windowTitle, PortfolioModel portfolio, Page pageToEdit) {
        this.portfolio = portfolio;
        this.pageToEdit = pageToEdit;
        
	// THE TOOLBAR ALONG THE NORTH
	initFileToolbar();

        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
	// TO THE WINDOW YET
	initWorkspace();

	// NOW SETUP THE EVENT HANDLERS
	initEventHandlers();

	// AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
	// KEEP THE WINDOW FOR LATER
	primaryStage = initPrimaryStage;
	initWindow(windowTitle);
    }

    // UI SETUP HELPER METHODS
    private void initWorkspace() {
	// FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
	workspace = new HBox();
        workspace.getStyleClass().add(CSS_CLASS_SELECTED_WORKSPACE);
	
	// THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
	slideEditToolbar = new VBox();
	slideEditToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
	addSlideButton = this.initChildButton(slideEditToolbar,		ICON_ADD_SLIDE,	    TOOLTIP_ADD_SLIDE,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	removeSlideButton = this.initChildButton(slideEditToolbar,	ICON_REMOVE_SLIDE,  TOOLTIP_REMOVE_SLIDE,   CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	moveSlideUpButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_UP,	    TOOLTIP_MOVE_UP,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	moveSlideDownButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_DOWN,	    TOOLTIP_MOVE_DOWN,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
        //slideEditToolbar.setAlignment(Pos.CENTER);
	
	// AND THIS WILL GO IN THE CENTER
	slidesEditorPane = new VBox();
	slidesEditorScrollPane = new ScrollPane(slidesEditorPane);
        slidesEditorScrollPane.getStyleClass().add(CSS_CLASS_SLIDE_EDITOR_PANE);
	initTitleControls();
	
	// NOW PUT THESE TWO IN THE WORKSPACE
	workspace.getChildren().add(slideEditToolbar);
	workspace.getChildren().add(slidesEditorScrollPane);
        
        //GO IN THE RIGHT
        portfolioSettings = new VBox();
        ready = new Label("Ready to add slideshow");
        Label ready2 = new Label("to your ePortfolio?");
        addToPortfolio = new Button("Add to ePortfolio");
        portfolioSettings.setAlignment(Pos.CENTER);
        portfolioSettings.getStyleClass().add(CSS_CLASS_SLIDESHOW_READY);
        portfolioSettings.getChildren().addAll(ready,ready2,addToPortfolio);
    }

    private void initEventHandlers() {
	// FIRST THE FILE CONTROLS
	fileController = new SlideShowFileController(this, fileManager);
	newSlideShowButton.setOnAction(e -> {
	    fileController.handleNewSlideShowRequest();
	});
	loadSlideShowButton.setOnAction(e -> {
	    fileController.handleLoadSlideShowRequest();
	});
	saveSlideShowButton.setOnAction(e -> {
	    fileController.handleSaveSlideShowRequest();
	});
	viewSlideShowButton.setOnAction(e -> {
            try {
                fileController.handleViewSlideShowRequest();
            } catch (IOException ex) {
                Logger.getLogger(SlideShowMakerView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(SlideShowMakerView.class.getName()).log(Level.SEVERE, null, ex);
            }
	});
	exitButton.setOnAction(e -> {
	    fileController.handleExitRequest();
	});
	
	// THEN THE SLIDE SHOW EDIT CONTROLS
	editController = new SlideShowEditController(this);
	addSlideButton.setOnAction(e -> {
	    editController.processAddSlideRequest();
	});
	removeSlideButton.setOnAction(e -> {
	    editController.processRemoveSlideRequest();
	});
	moveSlideUpButton.setOnAction(e -> {
	    editController.processMoveSlideUpRequest();
	});
	moveSlideDownButton.setOnAction(e -> {
	    editController.processMoveSlideDownRequest();
	});
        
        //PORTFOLIO CONTROLS
        addToPortfolio.setOnAction(e ->{
            try {
                slideShow.setTitle(titleTextField.getText());
                SlideShowComponent ssc = new SlideShowComponent(slideShow.getTitle(),
                        slideShow.getSlides());
                pageToEdit.addSlideShowComponent(ssc);
                System.out.println("ADDED SLIDESHOW : " + slideShow.getTitle());
                //SAVE
                fileController = new SlideShowFileController(this, fileManager);
                fileController.handleSaveSlideShowRequest();
                //MAKE SITE
                SlideShowViewer viewer = new SlideShowViewer(slideShow);
                viewer.makeCSS();
                viewer.makeJS();
                try {
                    viewer.makeHtml();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(SlideShowMakerView.class.getName()).log(Level.SEVERE, null, ex);
                }
                primaryStage.close();
            } catch (IOException ex) {
                Logger.getLogger(SlideShowMakerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
	fileToolbarPane = new FlowPane();
        fileToolbarPane.getStyleClass().add(CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX);
        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
	// START AS ENABLED (false), WHILE OTHERS DISABLED (true)
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	newSlideShowButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW,	TOOLTIP_NEW_SLIDE_SHOW,	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	loadSlideShowButton = initChildButton(fileToolbarPane, ICON_LOAD_SLIDE_SHOW,	TOOLTIP_LOAD_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	saveSlideShowButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW,	TOOLTIP_SAVE_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	viewSlideShowButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	TOOLTIP_VIEW_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }

    private void initWindow(String windowTitle) {
	// SET THE WINDOW TITLE
	primaryStage.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	primaryStage.setX(bounds.getMinX() * .3);
	primaryStage.setY(bounds.getMinY() * .8);
	primaryStage.setWidth(bounds.getWidth() * .3);
	primaryStage.setHeight(bounds.getHeight() * .8);

        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	epgPane = new BorderPane();
	epgPane.setTop(fileToolbarPane);
        epgPane.getStyleClass().add(CSS_CLASS_SELECTED_WORKSPACE);
	primaryScene = new Scene(epgPane);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(SSM_STYLE_SHEET_UI);
	primaryStage.setScene(primaryScene);
    }
    
    /**
     * This helps initialize buttons in a toolbar, constructing a custom button
     * with a customly provided icon and tooltip, adding it to the provided
     * toolbar pane, and then returning it.
     */
    public Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    LanguagePropertyType tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	Button button = new Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
    
    /**
     * Updates the enabled/disabled status of all toolbar
     * buttons.
     * 
     * @param saved 
     */
    public void updateToolbarControls(boolean saved) {
	// FIRST MAKE SURE THE WORKSPACE IS THERE
	epgPane.setCenter(workspace);
        epgPane.setRight(portfolioSettings);
	
	// NEXT ENABLE/DISABLE BUTTONS AS NEEDED IN THE FILE TOOLBAR
	saveSlideShowButton.setDisable(saved);
	viewSlideShowButton.setDisable(false);
	
	updateSlideshowEditToolbarControls();
    }
    
    public void updateSlideshowEditToolbarControls() {
	// AND THE SLIDESHOW EDIT TOOLBAR
	addSlideButton.setDisable(false);
	boolean slideSelected = slideShow.isSlideSelected();
	removeSlideButton.setDisable(!slideSelected);
	moveSlideUpButton.setDisable(!slideSelected);
	moveSlideDownButton.setDisable(!slideSelected);	
    }

    /**
     * Uses the slide show data to reload all the components for
     * slide editing.
     * 
     * @param slideShowToLoad SLide show being reloaded.
     */
    public void reloadSlideShowPane() {
	slidesEditorPane.getChildren().clear();
	reloadTitleControls();
	for (Slide slide : slideShow.getSlides()) {
	    SlideEditView slideEditor = new SlideEditView(slide);
	    if (slideShow.isSelectedSlide(slide))
		slideEditor.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
	    else
		slideEditor.getStyleClass().add(CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW);
	    slidesEditorPane.getChildren().add(slideEditor);
	    slideEditor.setOnMousePressed(e -> {
		slideShow.setSelectedSlide(slide);
		this.reloadSlideShowPane();
	    });
	}
	updateSlideshowEditToolbarControls();
    }
    
    private void initTitleControls() {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	titlePane = new FlowPane();
        titlePane.getStyleClass().add(CSS_CLASS_TITLE_PANE);
	titleLabel = new Label("TITLE: ");
        titleLabel.getStyleClass().add(CSS_LABEL);
	titleTextField = new TextField();
        titleTextField.getStyleClass().add(CSS_LABEL);
	
	titleTextField.setText("ENTER TITLE");
	titlePane.getChildren().add(titleLabel);
	titlePane.getChildren().add(titleTextField);
	
	titleTextField.textProperty().addListener(e -> {
	    slideShow.setTitle(titleTextField.getText());
	});
    }
    
    public void reloadTitleControls() {
	slidesEditorPane.getChildren().add(titlePane);
	titleTextField.setText(slideShow.getTitle());
    }
}
