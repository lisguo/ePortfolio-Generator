package epg.view;

import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_ADD_PAGE;
import static epg.LanguagePropertyType.TOOLTIP_EXIT;
import static epg.LanguagePropertyType.TOOLTIP_EXPORT_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_LOAD_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_NEW_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_PAGE;
import static epg.LanguagePropertyType.TOOLTIP_SAVEAS_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_SAVE_PORTFOLIO;
import epg.PortfolioGenerator;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX;
import static epg.StartupConstants.CSS_CLASS_PAGE_EDITOR_PANE;
import static epg.StartupConstants.CSS_CLASS_SELECTED_WORKSPACE;
import static epg.StartupConstants.CSS_CLASS_SITE_TOOLBAR_VBOX;
import static epg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.ICON_ADD_PAGE;
import static epg.StartupConstants.ICON_EXIT;
import static epg.StartupConstants.ICON_EXPORT_PORTFOLIO;
import static epg.StartupConstants.ICON_LOAD_PORTFOLIO;
import static epg.StartupConstants.ICON_NEW_PORTFOLIO;
import static epg.StartupConstants.ICON_REMOVE_PAGE;
import static epg.StartupConstants.ICON_SAVEAS_PORTFOLIO;
import static epg.StartupConstants.ICON_SAVE_PORTFOLIO;
import static epg.StartupConstants.PATH_ICONS;
import static epg.StartupConstants.STYLE_SHEET_UI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class PortfolioGeneratorView {
    Stage primaryStage;
    Scene primaryScene;
    
    //FileController fileController;
    //GENERAL GUI
    BorderPane epgPane;
    
    //FILE TOOLBAR AND BUTTONS - TOP
    FlowPane fileToolbarPane;
    Button newPortfolioButton;
    Button loadPortfolioButton;
    Button savePortfolioButton;
    Button saveAsPortfolioButton;
    Button exportPortfolioButton;
    Button exitButton;
    
    //WORKSPACE MODE TOOLBAR - CENTER
    TabPane workspaceModeToolbarPane;
    Tab portfolioEditor;
    Tab siteViewer;
    
    //PAGE EDITOR
    HBox portfolioEditorPane;
    Label bannerImg;
    
    
    //SITE VIEWER
    Pane pageViewerPane;
    
    public PortfolioGeneratorView(){
    }
    
    public void startUI(Stage initPrimaryStage, String windowTitle){
        initFileToolbar();
        initWorkspace();
        //initEventHandlers();
        primaryStage = initPrimaryStage;
        initWindow(windowTitle);
    }
    private void initWindow(String windowTitle) {
	// SET THE WINDOW TITLE
	primaryStage.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	primaryStage.setX(bounds.getMinX());
	primaryStage.setY(bounds.getMinY());
	primaryStage.setWidth(bounds.getWidth());
	primaryStage.setHeight(bounds.getHeight());

        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	epgPane = new BorderPane();
	epgPane.setTop(fileToolbarPane);
        epgPane.setCenter(workspaceModeToolbarPane);
        epgPane.getStyleClass().add(CSS_CLASS_SELECTED_WORKSPACE);
	primaryScene = new Scene(epgPane);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(STYLE_SHEET_UI);
	primaryStage.setScene(primaryScene);
	primaryStage.show();
    }
    private void initFileToolbar() {
	fileToolbarPane = new FlowPane();
        fileToolbarPane.getStyleClass().add(CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX);
        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
	// START AS ENABLED (false), WHILE OTHERS DISABLED (true)
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	newPortfolioButton = initChildButton(fileToolbarPane, ICON_NEW_PORTFOLIO, TOOLTIP_NEW_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        loadPortfolioButton = initChildButton(fileToolbarPane, ICON_LOAD_PORTFOLIO, TOOLTIP_LOAD_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        savePortfolioButton = initChildButton(fileToolbarPane, ICON_SAVE_PORTFOLIO, TOOLTIP_SAVE_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        saveAsPortfolioButton = initChildButton(fileToolbarPane, ICON_SAVEAS_PORTFOLIO, TOOLTIP_SAVEAS_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        exportPortfolioButton = initChildButton(fileToolbarPane, ICON_EXPORT_PORTFOLIO, TOOLTIP_EXPORT_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }
    private void initWorkspace(){
        workspaceModeToolbarPane = new TabPane();
        //INITIALIZING PORTFOLIO EDITOR
        portfolioEditor = new Tab();
        portfolioEditor.setText("Portfolio Editor");
        portfolioEditor.setContent(portfolioEditorPane);
        
        portfolioEditorPane = new HBox();
        //SITE TOOLBAR
        VBox siteToolbar = new VBox();
	siteToolbar.getStyleClass().add(CSS_CLASS_SITE_TOOLBAR_VBOX);
	Button addPageButton = this.initChildButton(siteToolbar, ICON_ADD_PAGE, TOOLTIP_ADD_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	Button removePageButton = this.initChildButton(siteToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
        
        //PAGE PANE
        VBox pageEditorPane = new VBox();
	ScrollPane pageEditorScrollPane = new ScrollPane(pageEditorPane);
        pageEditorScrollPane.getStyleClass().add(CSS_CLASS_PAGE_EDITOR_PANE);
        
        //PAGE SETTINGS PANE
        VBox pageSettingsPane = new VBox();
        
        //LAYOUT SELECTION
        VBox layoutSelection = new VBox();
        Label layoutLabel= new Label("Select Layout:");
        RadioButton layout1Button = new RadioButton("Layout 1");
        RadioButton layout2Button = new RadioButton("Layout 2");
        RadioButton layout3Button = new RadioButton("Layout 3");
        RadioButton layout4Button = new RadioButton("Layout 4");
        RadioButton layout5Button = new RadioButton("Layout 5");
        //PUT LABEL AND BUTTONS ON VBOX
        layoutSelection.getChildren().addAll(layoutLabel, layout1Button,
                layout2Button, layout3Button, layout4Button, layout5Button);
        
        //COLOR SELECTION
        VBox colorSelection = new VBox();
        Label colorLabel = new Label("Select Color Scheme:");
        RadioButton color1Button = new RadioButton("Blue");
        RadioButton color2Button = new RadioButton("Red");
        RadioButton color3Button = new RadioButton("Green");
        RadioButton color4Button = new RadioButton("Beige");
        RadioButton color5Button = new RadioButton("Gray");
        //PUT LABEL AND BUTTONS ON VBOX
        colorSelection.getChildren().addAll(colorLabel, color1Button,color2Button,
        color3Button,color4Button,color5Button);
        
        //BANNER SELECTION
        GridPane bannerSelection = new GridPane();
        Label bannerLabel = new Label("Select Banner Image:");
        Button browse = new Button("Browse...");
        bannerImg = new Label();
        bannerSelection.add(bannerLabel, 0, 0);
        bannerSelection.add(browse, 0 , 1);
        bannerSelection.add(bannerImg, 1 , 1);
        //ADD TO PAGE SETTINGS
        pageSettingsPane.getChildren().addAll(layoutSelection, colorSelection,
                bannerSelection);
        
        //FINALLY ADD EVERYTHING TO PORTFOLIO EDITOR
        portfolioEditorPane.getChildren().addAll(siteToolbar, pageEditorPane,
                pageSettingsPane);
        
        siteViewer = new Tab();
        siteViewer.setText("Site Viewer");
        workspaceModeToolbarPane.getTabs().addAll(portfolioEditor, siteViewer);
    }
    
    private void initEventHandlers() {
	// FIRST THE FILE CONTROLS
	
    }
    
    public javafx.scene.control.Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    LanguagePropertyType tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	javafx.scene.control.Button button = new javafx.scene.control.Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
}