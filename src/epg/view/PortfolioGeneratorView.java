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
    //PORTFOLIO TITLE
    String title = "sample";

    Stage primaryStage;
    Scene primaryScene;
    ComponentController componentController;
    FileController fileController;
    PortfolioFileManager fileManager;
    PageEditController pageEditController;
    
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
    
    VBox componentToolbar;
    Button addTextComponent;
    Button addImageComponent;
    Button addSlideShowComponent;
    Button addVideoComponent;
    Button removeComponent;
    Button editComponent;
    
    VBox componentPane;
    ScrollPane componentScrollPane;
    

    //SITE VIEWER
    Pane pageViewerPane;
    String htmlPath;
   
   
    //THE PORTFOLIO GENERATOR WE WILL BE WORKING ON
    //CREATIN A PORTFOLIO MODEL FOR HW 6
    PortfolioModel portfolio;
    
    public PortfolioGeneratorView() throws IOException{
        portfolio = new PortfolioModel(this);
        //htmlPath = PATH_PORFOLIOS + portfolio.getTitle() + "/index.html";
        htmlPath = PATH_PORTFOLIOS + title + "/index.html";
        
        File f = new File(htmlPath);
        htmlPath = f.getCanonicalPath();
    }
    
    public PortfolioModel getPortfolio(){
        return portfolio;
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
        //ADD DUMMY PAGES
        PageEditView page1 = new PageEditView(new Page("Page 1", "layout1","blue",true));
        PageEditView page2 = new PageEditView(new Page("Page 2", "layout1","blue",true));
        pageEditorPane.getChildren().addAll(page1, page2);
        
        
        portfolioEditorPane.getChildren().add(siteToolbar);
        portfolioEditorPane.getChildren().add(pageEditorScrollPane);
        portfolioEditor.setContent(portfolioEditorPane);
        
        //WEB VIEWER
        WebView viewer = new WebView();
        WebEngine engine = viewer.getEngine();
        String loadStr = "file:///" + htmlPath;
        System.out.println("Loading " + loadStr);
        engine.load(loadStr);
        //System.out.println("Loading " + loadStr);
        //CHANGE SIZE
        viewer.setPrefSize(1980, 900);
        viewer.setManaged(true);
        pageViewerPane = new Pane();
        pageViewerPane.getChildren().add(viewer);
        siteViewer = new Tab();
        siteViewer.setClosable(false);
        siteViewer.setText("Site Viewer");
        siteViewer.setContent(pageViewerPane);
        workspaceModeToolbarPane.getTabs().addAll(portfolioEditor, siteViewer);
    }
    
    private void initPageSettingsWorkspace(){
        //PAGE SETTINGS PANE
        VBox pageSettingsPane = new VBox();
        //SETTING THE WIDTH!!!! CHANGE LATER
        pageSettingsPane.setPrefWidth(450);
        pageSettingsPane.getStyleClass().add(CSS_CLASS_PAGE_SETTINGS_PANE);
        
        //LAYOUT SELECTION
        VBox layoutSelection = new VBox();
        Label layoutLabel= new Label("Select Layout:");
        ToggleGroup layoutButtons = new ToggleGroup();
        RadioButton layout1Button = new RadioButton("Layout 1");
        RadioButton layout2Button = new RadioButton("Layout 2");
        RadioButton layout3Button = new RadioButton("Layout 3");
        RadioButton layout4Button = new RadioButton("Layout 4");
        RadioButton layout5Button = new RadioButton("Layout 5");
        //PUT BUTTONS ON SAME GROUP
        layout1Button.setToggleGroup(layoutButtons);
        layout2Button.setToggleGroup(layoutButtons);
        layout3Button.setToggleGroup(layoutButtons);
        layout4Button.setToggleGroup(layoutButtons);
        layout5Button.setToggleGroup(layoutButtons);
        //PUT LABEL AND BUTTONS ON VBOX
        layoutSelection.getChildren().addAll(layoutLabel, layout1Button,
                layout2Button, layout3Button, layout4Button, layout5Button);
        
        //COLOR SELECTION
        VBox colorSelection = new VBox();
        Label colorLabel = new Label("Select Color Scheme:");
        ToggleGroup colorButtons = new ToggleGroup();
        RadioButton color1Button = new RadioButton("Blue");
        RadioButton color2Button = new RadioButton("Red");
        RadioButton color3Button = new RadioButton("Green");
        RadioButton color4Button = new RadioButton("Beige");
        RadioButton color5Button = new RadioButton("Gray");
        //PUT BUTTONS ON SAME GROUP
        color1Button.setToggleGroup(colorButtons);
        color2Button.setToggleGroup(colorButtons);
        color3Button.setToggleGroup(colorButtons);
        color4Button.setToggleGroup(colorButtons);
        color5Button.setToggleGroup(colorButtons);
        
        //PUT LABEL AND BUTTONS ON VBOX
        colorSelection.getChildren().addAll(colorLabel, color1Button,color2Button,
        color3Button,color4Button,color5Button);
        
        //PAGE FONT
        VBox pageFontSettings = new VBox();
        Label fontLabel = new Label("Select a Page Font:");
        ObservableList<String> fonts = FXCollections.observableArrayList();
        fonts.add("Righteous");
        fonts.add("Lora");
        fonts.add("Roboto Slab");
        fonts.add("Rock Salt");
        ComboBox pageFont = new ComboBox(fonts);
        pageFont.getSelectionModel().select("Righteous");
        pageFontSettings.getChildren().addAll(fontLabel, pageFont);
        
        
        //BANNER SELECTION
        VBox bannerSelection = new VBox();
        Label bannerLabel = new Label("Select Banner Image:");
        Button browse = new Button("Browse...");
        bannerImg = new Label("bannerImg.jpg");
        bannerSelection.getChildren().addAll(bannerLabel, browse, bannerImg);
        
        //STUDENT NAME
        VBox studentBox = new VBox();
        Label nameLabel = new Label("Enter Student Name:");
        TextField nameField = new TextField();
        studentBox.getChildren().add(nameLabel);
        studentBox.getChildren().add(nameField);
        
        //UPDATE FOOTER
        VBox footerBox = new VBox();
        Label footerLabel = new Label("Enter Footer:");
        TextField footerField = new TextField();
        footerField.setAlignment(Pos.TOP_LEFT);
        footerField.setPrefHeight(200);
        footerBox.getChildren().addAll(footerLabel,footerField);
        
        //ADD TO PAGE SETTINGS
        pageSettingsPane.getChildren().addAll(layoutSelection, colorSelection,
                pageFontSettings, bannerSelection, studentBox, footerBox);
        
        //COMPONENT TOOLBAR
        componentToolbar = new VBox();
	componentToolbar.getStyleClass().add(CSS_CLASS_SITE_TOOLBAR_VBOX);
	addTextComponent = this.initChildButton(componentToolbar, ICON_ADD_TEXT_COMPONENT, TOOLTIP_ADD_TEXT_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);
        addImageComponent = this.initChildButton(componentToolbar, ICON_ADD_IMAGE_COMPONENT, TOOLTIP_ADD_IMAGE_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);;
        addSlideShowComponent = this.initChildButton(componentToolbar, ICON_ADD_SS_COMPONENT, TOOLTIP_ADD_SS_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);;
        addVideoComponent = this.initChildButton(componentToolbar, ICON_ADD_VIDEO_COMPONENT, TOOLTIP_ADD_VIDEO_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);;
        removeComponent = this.initChildButton(componentToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);;
        editComponent = this.initChildButton(componentToolbar, ICON_EDIT_COMPONENT, TOOLTIP_EDIT_COMPONENT, CSS_CLASS_COMPONENT_TOOLBAR_BUTTON,  false);;
        
        
        //COMPONENT SCROLLPANE
        componentPane = new VBox();
        componentScrollPane = new ScrollPane();
        componentScrollPane.getStyleClass().add(CSS_CLASS_COMPONENT_PANE);
        //SETTING THE WIDTH!!!! CHANGE LATER
        componentPane.setPrefWidth(1000);
        //ADD DUMMY COMPONENTS
        TextComponent tc1 = new TextComponent("Header","This is a header");
        TextComponent tc2 = new TextComponent("Paragraph","This is a paragraph."
                + "This is a paragraph.");
        TextComponent tc3 = new TextComponent("List","This\nIs\nA\nList");
        TextComponentView t1 = new TextComponentView(tc1);
        TextComponentView t2 = new TextComponentView(tc2);
        TextComponentView t3 = new TextComponentView(tc3);
        componentPane.getChildren().addAll(t1,t2,t3);
        
        //HANDLER
        t1.setOnMouseClicked(e ->{
            selectedComponent = tc1;
            t1.getStyleClass().add(CSS_CLASS_SELECTED_COMPONENT);
        });
        t2.setOnMouseClicked(e ->{
            selectedComponent = tc2;
            t2.getStyleClass().add(CSS_CLASS_SELECTED_COMPONENT);
        });
        t3.setOnMouseClicked(e ->{
            selectedComponent = tc3;
            t3.getStyleClass().add(CSS_CLASS_SELECTED_COMPONENT);
        });
        componentScrollPane.setContent(componentPane);
        //FINALLY ADD EVERYTHING TO PORTFOLIO EDITOR
        portfolioEditorPane.getChildren().add(pageSettingsPane);
        portfolioEditorPane.getChildren().add(componentToolbar);
        portfolioEditorPane.getChildren().add(componentScrollPane);
        
        portfolioEditor.setContent(portfolioEditorPane);
        
    }
    private void initEventHandlers() {
        //FILE TOOLBAR CONTROLS
        fileController = new FileController(this, fileManager);
        newPortfolioButton.setOnAction(e ->{
            fileController.handleNewPortfolioRequest();
        });
        
        
        //PAGE EDIT CONTROLS
        pageEditController = new PageEditController(this);
        removePageButton.setOnAction(e->{
            pageEditController.processRemovePageRequest();
        });
	
    }
    private void initComponentHandlers(){
        componentController = new ComponentController(this);
        addTextComponent.setOnAction(e -> {
	    componentController.handleAddTextComponent();
	});
        addImageComponent.setOnAction(e -> {
           componentController.handleAddImageComponent(); 
        });
        addSlideShowComponent.setOnAction(e ->{
            try {
                componentController.handleAddSlideShowComponent();
            } catch (Exception ex) {
                Logger.getLogger(PortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        addVideoComponent.setOnAction(e ->{
            componentController.handleAddVideoComponent();
        });
        editComponent.setOnAction(e -> {
            componentController.handleEditComponent(selectedComponent);
        });
    }
    public void reloadPageEditorPane() {
	pageEditorPane.getChildren().clear();
	for (Page page : portfolio.getPages()) {
	    PageEditView pageEditor = new PageEditView(page);
	    if (portfolio.isSelectedPage(page))
		pageEditor.getStyleClass().add(CSS_CLASS_PAGE_EDIT_VIEW);
	    else
		pageEditor.getStyleClass().add(CSS_CLASS_SELECTED_PAGE_EDIT_VIEW);
	    pageEditorPane.getChildren().add(pageEditor);
	    pageEditor.setOnMousePressed(e -> {
		portfolio.setSelectedPage(page);
		this.reloadPortfolioPane();
	    });
	}
	updateSiteToolbarControls(false);
    }
    public void reloadPortfolioPane() {
	pageEditorPane.getChildren().clear();
	for (Page page : portfolio.getPages()) {
	    PageEditView pageEditor = new PageEditView(page);
	    if (portfolio.isSelectedPage(page))
		pageEditor.getStyleClass().add(CSS_CLASS_PAGE_EDIT_VIEW);
	    else
		pageEditor.getStyleClass().add(CSS_CLASS_SELECTED_PAGE_EDIT_VIEW);
	    pageEditorPane.getChildren().add(pageEditor);
	    pageEditor.setOnMousePressed(e -> {
		portfolio.setSelectedPage(page);
		this.reloadPortfolioPane();
	    });
	}
	updateSiteToolbarControls(false);
    }
    
    public void reloadComponentPane() {
	componentPane.getChildren().clear();
	for (Component component : portfolio.getComponents()) {
	    ComponentView componentEditor = new ComponentView(component);
	    if (portfolio.isSelectedComponent(component))
		componentEditor.getStyleClass().add(CSS_CLASS_COMPONENT);
	    else
		componentEditor.getStyleClass().add(CSS_CLASS_SELECTED_COMPONENT);
	    pageEditorPane.getChildren().add(componentEditor);
	    componentEditor.setOnMousePressed(e -> {
		portfolio.setSelectedComponent(component);
		this.reloadPortfolioPane();
	    });
	}
	updateSiteToolbarControls(false);
    }
    
    public void updateSiteToolbarControls(boolean isSaved) {
	//SET WORKSPACE
        epgPane.setCenter(workspaceModeToolbarPane);
        //ENABLE ADD PAGE BUTTON
	addPageButton.setDisable(false);
        
        if(isSaved){
            savePortfolioButton.setDisable(false);
        }
        
	boolean pageSelected = portfolio.isPageSelected();
	removePageButton.setDisable(!pageSelected);
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
