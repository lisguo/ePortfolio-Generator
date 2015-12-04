package epg.view;

import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_ADD_IMAGE_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_SS_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_TEXT_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_ADD_VIDEO_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_EDIT_COMPONENT;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_COMPONENT;
import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_LABEL;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_PANE;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_PAGE_SETTINGS_PANE;
import static epg.StartupConstants.CSS_CLASS_SELECTED_COMPONENT;
import static epg.StartupConstants.CSS_CLASS_SITE_TOOLBAR_VBOX;
import static epg.StartupConstants.ICON_ADD_IMAGE_COMPONENT;
import static epg.StartupConstants.ICON_ADD_SS_COMPONENT;
import static epg.StartupConstants.ICON_ADD_TEXT_COMPONENT;
import static epg.StartupConstants.ICON_ADD_VIDEO_COMPONENT;
import static epg.StartupConstants.ICON_EDIT_COMPONENT;
import static epg.StartupConstants.ICON_REMOVE_PAGE;
import static epg.StartupConstants.PATH_ICONS;
import epg.controller.ComponentController;
import epg.controller.PageSettingsController;
import epg.model.Component;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.TextComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

public class PageSettingsView extends HBox{

    VBox pageSettingsPane;
    VBox componentToolbar;
    Button addTextComponent;
    Button addImageComponent;
    Button addSlideShowComponent;
    Button addVideoComponent;
    Button removeComponent;
    Button editComponent;
    ToggleGroup layoutButtons;
    ToggleGroup colorButtons;
    ComboBox pageFont;
    TextField studentField;
    TextField footerField;
    Button browse;
    Label bannerFileName;
    
    Page pageToEdit;
    
    //CONTROLLERS
    PageSettingsController pageSettingsController;
    ComponentController componentController;
    
    //COMPONENTS
    VBox componentPane;
    ScrollPane componentScrollPane;
    VBox textComponentPane;
    VBox imageComponentPane;
    VBox slideShowComponentPane;
    VBox videoComponentPane;

    PortfolioModel portfolio;
    public PageSettingsView(){
        
    }
    public PageSettingsView(PortfolioModel p, Page page){
        portfolio = p;
        pageToEdit = page;
        showPageSettingsWorkspace();
    }
    public PortfolioModel getPortfolio(){
        return portfolio;
    }
    public Page getPageToEdit(){
        return pageToEdit;
    }
    public void showPageSettingsWorkspace(){
        //PAGE SETTINGS PANE
        pageSettingsPane = new VBox();
        //SETTING THE WIDTH!!!! CHANGE LATER
        pageSettingsPane.setPrefWidth(450);
        pageSettingsPane.getStyleClass().add(CSS_CLASS_PAGE_SETTINGS_PANE);
        
        //LAYOUT SELECTION
        VBox layoutSelection = new VBox();
        Label layoutLabel= new Label("Select Layout:");
        layoutButtons = new ToggleGroup();
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
        //SET THE INITIAL TOGGLE
        layoutButtons.selectToggle(layoutButtons.getToggles()
                .get(pageToEdit.getLayout()));
        //PUT LABEL AND BUTTONS ON VBOX
        layoutSelection.getChildren().addAll(layoutLabel, layout1Button,
                layout2Button, layout3Button, layout4Button, layout5Button);
        
        //COLOR SELECTION
        VBox colorSelection = new VBox();
        Label colorLabel = new Label("Select Color Scheme:");
        colorButtons = new ToggleGroup();
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
        //SET THE INITIAL TOGGLE
        colorButtons.selectToggle(colorButtons.getToggles()
                .get(pageToEdit.getColor()));
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
        pageFont = new ComboBox(fonts);
        pageFont.getSelectionModel().select(pageToEdit.getPageFont());
        pageFontSettings.getChildren().addAll(fontLabel, pageFont);
        
        //BANNER SELECTION
        VBox bannerSelection = new VBox();
        Label bannerLabel = new Label("Select Banner Image:");
        HBox bannerButtonAndName = new HBox();
        browse = new Button("Browse...");
        bannerFileName = new Label(pageToEdit.getBannerFileName());
        bannerButtonAndName.getChildren().addAll(browse,bannerFileName);
        bannerSelection.getChildren().addAll(bannerLabel, bannerButtonAndName);
        
        //STUDENT NAME
        VBox studentBox = new VBox();
        Label nameLabel = new Label("Enter Student Name:");
        studentField = new TextField();
        //SET THE TEXT
        studentField.setText(portfolio.getStudentName());
        studentBox.getChildren().add(nameLabel);
        studentBox.getChildren().add(studentField);
        
        //UPDATE FOOTER
        VBox footerBox = new VBox();
        Label footerLabel = new Label("Enter Footer:");
        footerField = new TextField();
        footerField.setAlignment(Pos.TOP_LEFT);
        footerField.setPrefHeight(200);
        footerField.setText(pageToEdit.getFooter());
        footerBox.getChildren().addAll(footerLabel,footerField);
        
        //ADD TO PAGE SETTINGS
        pageSettingsPane.getChildren().addAll(layoutSelection, colorSelection,
                pageFontSettings,studentBox, footerBox);
        
        //ADD BANNER IF LAYOUT HAS ONE
        if(pageToEdit.getHasBannerImage()){
            pageSettingsPane.getChildren().add(bannerSelection);
        }//ELSE REMOVE
        else{
            pageSettingsPane.getChildren().remove(bannerSelection);
        }
        
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
        componentScrollPane.setContent(componentPane);
        //MAKE VBOXES PER EACH COMPONENT TYPE
        textComponentPane = new VBox();
        //MAKE LABEL FOR COMPONENT BOX
        Label textComponentLabel = new Label("TEXT COMPONENTS");
        textComponentLabel.getStyleClass().add(CSS_CLASS_COMPONENT_LABEL);
        
        imageComponentPane = new VBox();
        Label imageComponentLabel = new Label("IMAGE COMPONENTS");
        imageComponentLabel.getStyleClass().add(CSS_CLASS_COMPONENT_LABEL);
        
        slideShowComponentPane = new VBox();
        Label slideShowComponentLabel = new Label("SLIDE SHOW COMPONENTS");
        slideShowComponentLabel.getStyleClass().add(CSS_CLASS_COMPONENT_LABEL);
        
        videoComponentPane = new VBox();
        Label videoComponentLabel = new Label("VIDEO COMPONENTS");
        videoComponentLabel.getStyleClass().add(CSS_CLASS_COMPONENT_LABEL);
        
        //FINALLY ADD EVERYTHING TO PORTFOLIO SETTINGS VIEW
        componentPane.getChildren().addAll(textComponentLabel, textComponentPane,
                imageComponentLabel,imageComponentPane,
                slideShowComponentLabel, slideShowComponentPane, videoComponentPane);
        getChildren().addAll(pageSettingsPane,componentToolbar,componentScrollPane);
        
        reloadTextComponentPane();
        initPageSettingHandlers();
        initComponentHandlers();
    }
    private void initPageSettingHandlers(){
        //SETTING THE LAYOUT
        pageSettingsController = new PageSettingsController(this);
        for(Toggle t : layoutButtons.getToggles()){
            RadioButton b = (RadioButton) t;
            b.setOnAction(e ->{
                pageSettingsController.handleSetLayout(layoutButtons); 
            });
        }
        //SETTING COLOR
        for(Toggle t : colorButtons.getToggles()){
            RadioButton b = (RadioButton) t;
            b.setOnAction(e ->{
                pageSettingsController.handleSetColor(colorButtons); 
            });
        }
        //SETTING PAGE FONT
        pageFont.setOnAction(e ->{
            pageSettingsController.handleSetPageFont(pageFont);
        });
        
        //SETTING STUDENT NAME
        studentField.setOnAction(e ->{
            pageSettingsController.handleSetStudentName(studentField);
        });
        //SETTING FOOTER
        footerField.setOnAction(e ->{
            pageSettingsController.handleSetFooter(footerField);
        });
        
        //SETTING BANNER
        browse.setOnAction(e ->{
            pageSettingsController.handleBannerSelection();
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
            componentController.handleEditComponent(pageToEdit.getSelectedComponent());
        });
    }
     public void reloadTextComponentPane() {
	textComponentPane.getChildren().clear();
	for (TextComponent component : pageToEdit.getTextComponents()) {
	    TextComponentView componentEditor = new TextComponentView(pageToEdit, component);
	    if (pageToEdit.isSelectedComponent(component))
		componentEditor.getStyleClass().add(CSS_CLASS_SELECTED_COMPONENT);
	    else
		componentEditor.getStyleClass().add(CSS_CLASS_COMPONENT);
	    textComponentPane.getChildren().add(componentEditor);
	    componentEditor.setOnMousePressed(e -> {
		pageToEdit.setSelectedComponent(component);
                System.out.println("SELECTED COMPONENT : " + component.getText());
		this.reloadTextComponentPane();
	    });
	}
    }
     public void reloadImageComponentPane(){
         imageComponentPane.getChildren().clear();
        // for(ImageComponent component : pageToEdit.getImageComponents()){
             
         //}
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