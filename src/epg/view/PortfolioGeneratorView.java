package epg.view;

import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_ADD_IMAGE_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_PAGE;
import static epg.LanguagePropertyType.TOOLTIP_ADD_SS_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_TEXT_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_VIDEO_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_EDIT_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_EXIT;
import static epg.LanguagePropertyType.TOOLTIP_EXPORT_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_LOAD_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_NEW_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_PAGE;
import static epg.LanguagePropertyType.TOOLTIP_SAVEAS_PORTFOLIO;
import static epg.LanguagePropertyType.TOOLTIP_SAVE_PORTFOLIO;
import epg.PortfolioGenerator;
import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_PANE;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX;
import static epg.StartupConstants.CSS_CLASS_PAGE_EDITOR_PANE;
import static epg.StartupConstants.CSS_CLASS_PAGE_EDIT_VIEW;
import static epg.StartupConstants.CSS_CLASS_PAGE_SETTINGS_PANE;
import static epg.StartupConstants.CSS_CLASS_SELECTED_COMPONENT;
import static epg.StartupConstants.CSS_CLASS_SELECTED_PAGE_EDIT_VIEW;
import static epg.StartupConstants.CSS_CLASS_SELECTED_WORKSPACE;
import static epg.StartupConstants.CSS_CLASS_SITE_TOOLBAR_VBOX;
import static epg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_WORKSPACE_MODE_TOOLBAR;
import static epg.StartupConstants.ICON_ADD_IMAGE_COMPONENT;
import static epg.StartupConstants.ICON_ADD_PAGE;
import static epg.StartupConstants.ICON_ADD_SS_COMPONENT;
import static epg.StartupConstants.ICON_ADD_TEXT_COMPONENT;
import static epg.StartupConstants.ICON_ADD_VIDEO_COMPONENT;
import static epg.StartupConstants.ICON_EDIT_COMPONENT;
import static epg.StartupConstants.ICON_EXIT;
import static epg.StartupConstants.ICON_EXPORT_PORTFOLIO;
import static epg.StartupConstants.ICON_LOAD_PORTFOLIO;
import static epg.StartupConstants.ICON_NEW_PORTFOLIO;
import static epg.StartupConstants.ICON_REMOVE_PAGE;
import static epg.StartupConstants.ICON_SAVEAS_PORTFOLIO;
import static epg.StartupConstants.ICON_SAVE_PORTFOLIO;
import static epg.StartupConstants.PATH_ICONS;
import static epg.StartupConstants.STYLE_SHEET_UI;
import static epg.StartupConstants.PATH_PORTFOLIOS;
import epg.controller.ComponentController;
import epg.controller.FileController;
import epg.controller.PageEditController;
import epg.controller.PageSettingsController;
import epg.file.PortfolioFileManager;
import epg.model.Component;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.TextComponent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    ComponentController componentController;
    FileController fileController;
    PortfolioFileManager fileManager;
    PageEditController pageEditController;
    PageSettingsController pageSettingsController;
    
    Component selectedComponent;
    
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
    VBox pageEditorPane;
    ScrollPane pageEditorScrollPane;
    Button addPageButton;
    Button removePageButton;
    Label bannerImg;
    PageSettingsView settingsView = new PageSettingsView();
    
    //PAGE SETTINGS
    HBox pageSettingsPaneWithComponents;
    VBox pageSettingsPane;
    
    
    
    
    //SITE VIEWER
    Pane pageViewerPane;
    String htmlPath;
   
   
    //THE PORTFOLIO GENERATOR WE WILL BE WORKING ON
    PortfolioModel portfolio;
    
    public PortfolioGeneratorView() throws IOException{
        portfolio = new PortfolioModel(this);
        //htmlPath = PATH_PORFOLIOS + portfolio.getTitle() + "/index.html";
        htmlPath = PATH_PORTFOLIOS + portfolio.getTitle() + "/index.html";
        
        File f = new File(htmlPath);
        htmlPath = f.getCanonicalPath();
        fileManager = new PortfolioFileManager();
    }
    
    public PortfolioModel getPortfolio(){
        return portfolio;
    }
    public Stage getWindow() {
	return primaryStage;
    }
    public void startUI(Stage initPrimaryStage, String windowTitle){
        initFileToolbar();
        initWorkspace();
        initEventHandlers();
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
        savePortfolioButton = initChildButton(fileToolbarPane, ICON_SAVE_PORTFOLIO, TOOLTIP_SAVE_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        saveAsPortfolioButton = initChildButton(fileToolbarPane, ICON_SAVEAS_PORTFOLIO, TOOLTIP_SAVEAS_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        exportPortfolioButton = initChildButton(fileToolbarPane, ICON_EXPORT_PORTFOLIO, TOOLTIP_EXPORT_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }
    private void initWorkspace(){
        workspaceModeToolbarPane = new TabPane();
        //workspaceModeToolbarPane.getStyleClass().add(CSS_CLASS_SELECTED_WORKSPACE);
        //INITIALIZING PORTFOLIO EDITOR
        portfolioEditor = new Tab();
        portfolioEditor.setClosable(false);
        portfolioEditor.setText("Portfolio Editor");
        portfolioEditorPane = new HBox();
        //SITE TOOLBAR
        VBox siteToolbar = new VBox();
	siteToolbar.getStyleClass().add(CSS_CLASS_SITE_TOOLBAR_VBOX);
	addPageButton = this.initChildButton(siteToolbar, ICON_ADD_PAGE, TOOLTIP_ADD_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	removePageButton = this.initChildButton(siteToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
        
        //PAGE PANE
        pageEditorPane = new VBox();
	pageEditorScrollPane = new ScrollPane(pageEditorPane);
        //SETTING THE WIDTH!!!!!! CHANGE LATER
        pageEditorPane.setPrefWidth(200);
        pageEditorScrollPane.getStyleClass().add(CSS_CLASS_PAGE_EDITOR_PANE);
        
        
        portfolioEditorPane.getChildren().add(siteToolbar);
        portfolioEditorPane.getChildren().add(pageEditorScrollPane);
        portfolioEditor.setContent(portfolioEditorPane);
        
        //INITIALIZE PAGE SETTINGS
        pageSettingsPaneWithComponents = new HBox();
        
        
        //WEB VIEWER
        WebView viewer = new WebView();
        WebEngine engine = viewer.getEngine();
        String loadStr = "file:///" + htmlPath;
        System.out.println("Loading " + loadStr);
        engine.load(loadStr);
        //CHANGE SIZE
        viewer.setPrefSize(1900, 900);
        viewer.setManaged(true);
        pageViewerPane = new Pane();
        pageViewerPane.getChildren().add(viewer);
        siteViewer = new Tab();
        siteViewer.setClosable(false);
        siteViewer.setText("Site Viewer");
        siteViewer.setContent(pageViewerPane);
        workspaceModeToolbarPane.getTabs().addAll(portfolioEditor, siteViewer);
    }
    
    
    private void initEventHandlers() {
        //FILE TOOLBAR CONTROLS
        fileController = new FileController(this, fileManager);
        newPortfolioButton.setOnAction(e ->{
            fileController.handleNewPortfolioRequest();
        });
        savePortfolioButton.setOnAction(e ->{
            fileController.handleSavePortfolioRequest();
        });
        loadPortfolioButton.setOnAction(e ->{
            fileController.handleLoadPortfolioRequest();
        });
        
        //PAGE EDIT CONTROLS
        pageEditController = new PageEditController(this);
        addPageButton.setOnAction(e ->{
            pageEditController.processAddPageRequest();
        });
        removePageButton.setOnAction(e->{
            pageEditController.processRemovePageRequest();
        });
	
    }
    
    public void reloadPageEditorPane() {
	pageEditorPane.getChildren().clear();
	for (Page page : portfolio.getPages()) {
	    PageEditView pageEditor = new PageEditView(page);
	    if (portfolio.isSelectedPage(page))
		pageEditor.getStyleClass().add(CSS_CLASS_SELECTED_PAGE_EDIT_VIEW);
	    else
		pageEditor.getStyleClass().add(CSS_CLASS_PAGE_EDIT_VIEW);
	    pageEditorPane.getChildren().add(pageEditor);
	    pageEditor.setOnMousePressed(e -> {
		portfolio.setSelectedPage(page);
		this.reloadPageEditorPane();
	    });
	}
        //SHOW SETTINGS AND ADD THEM
        if(portfolio.getSelectedPage() != null){
            //showPageSettingsWorkspace(portfolio.getSelectedPage());
            settingsView.getChildren().clear();
            settingsView = new PageSettingsView(portfolio, 
                                                portfolio.getSelectedPage());
            portfolioEditorPane.getChildren().add(settingsView);
            System.out.println("SELECTED PAGE: " + portfolio.getSelectedPage().getName());
        }
	updateSiteToolbarControls(false);
    }
    
    
    public void updateSiteToolbarControls(boolean isSaved) {
	//SET WORKSPACE
        epgPane.setCenter(workspaceModeToolbarPane);
        //ENABLE ADD PAGE BUTTON
	addPageButton.setDisable(false);
        
        savePortfolioButton.setDisable(isSaved);
        saveAsPortfolioButton.setDisable(false);
        
        updatePortfolioEditToolbarControls();
    }
    public void updatePortfolioEditToolbarControls() {
	// AND THE SLIDESHOW EDIT TOOLBAR
	addPageButton.setDisable(false);
	boolean pageSelected = portfolio.isPageSelected();
	removePageButton.setDisable(!pageSelected);
    }
    public void removePageSettings(){
        portfolioEditorPane.getChildren().remove(settingsView);
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
