/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_ADD_LIST;
import static epg.LanguagePropertyType.TOOLTIP_HYPERLINK;
import static epg.LanguagePropertyType.TOOLTIP_REMOVE_LIST;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_EDITOR;
import static epg.StartupConstants.CSS_CLASS_HYPERLINK_BUTTON;
import static epg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import static epg.StartupConstants.CSS_STYLE_LIST_BUTTONS;
import static epg.StartupConstants.ICON_ADD_PAGE;
import static epg.StartupConstants.ICON_HYPERLINK;
import static epg.StartupConstants.ICON_REMOVE_PAGE;
import static epg.StartupConstants.PATH_ICONS;
import epg.model.PortfolioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.model.Component;
import epg.model.Page;
import epg.model.TextComponent;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponentEditor extends Stage{
    Scene scene;
    VBox vbox;
    HBox hBox;
    Label textTypeLabel;
    ComboBox textTypeComboBox;
    TextField textField;
    Button okButton;
    Button continueButton;
    boolean edit;
    Page pageToEdit;
    ComboBox fontSelection;
    //COMPONENTS OF THE COMPONENT
    String type;
    String text;
    VBox textFields;
    String listText = "";
    boolean editing = false;
        
        
    public TextComponentEditor(Page initPageToEdit){
        vbox = new VBox();
        hBox = new HBox();
        textField = new TextField();
        pageToEdit = initPageToEdit;
        
        //SELECT A TEXT TYPE
        textTypeLabel = new Label("Text Type: ");
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Header");
        types.add("Paragraph");
        types.add("List");
        textTypeComboBox = new ComboBox(types);
        textTypeComboBox.getSelectionModel().select("Header");
        textTypeComboBox.getStyleClass().add(CSS_SMALL_LABEL);
        continueButton = new Button("Continue");
        scene = new Scene(vbox);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        setTitle("Add a Text Component");
        hBox.getChildren().addAll(textTypeLabel, textTypeComboBox);
        hBox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hBox, continueButton);
        vbox.setAlignment(Pos.CENTER);
        setScene(scene);
        okButton = new Button("OK");
        vbox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        
        okButton.setOnAction(e ->{
            close();
        });
        continueButton.setOnAction(e->{
            String type = (String)textTypeComboBox.getSelectionModel().getSelectedItem();
            textEditDialog(type);
        });
    }
    public TextComponentEditor(Page initPageToEdit , TextComponent tc){
        pageToEdit = initPageToEdit;
        type = tc.getType();
        textField = new TextField();
        editing = true;
        if(type.equals("List")){
            listText = tc.getText();
        }
        else{
            textField.setText(tc.getText());
        }
        System.out.println("STARTING TEXT: " + textField.getText());
        okButton = new Button("OK");
        textEditDialog(type);
    }
    public void textEditDialog(String type){
        VBox nextDialog = new VBox();
        nextDialog.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
        if(type.equals("Header")){
                Label header = new Label("Header:");
                nextDialog.getChildren().addAll(header,textField,okButton);
            }
            else if(type.equals("Paragraph")){
                Label paragraph = new Label("Paragraph:");
                //FONTS
                ObservableList<String> fonts = FXCollections.observableArrayList();
                fonts.add("Righteous");
                fonts.add("Lora");
                fonts.add("Roboto Slab");
                fonts.add("Rock Salt");
                fontSelection = new ComboBox(fonts);
                fontSelection.getSelectionModel().select("Righteous");
                
                //HYPERLINK CONTROLS
                textField.setAlignment(Pos.TOP_LEFT);
                textField.setPrefHeight(200);
                textField.setPrefColumnCount(50);
                nextDialog.getChildren().addAll(paragraph, fontSelection);
                Button hyperLink = initChildButton(nextDialog, ICON_HYPERLINK, TOOLTIP_HYPERLINK, CSS_CLASS_HYPERLINK_BUTTON,false);
                nextDialog.getChildren().addAll(textField, okButton);
                
                hyperLink.setOnAction(e -> {
                    int startingIndex = textField.getSelection().getStart();
                    int endingIndex = textField.getSelection().getEnd();
                    Stage dialog = new Stage();
                    dialog.setHeight(200);
                    dialog.setWidth(500);
                    VBox hyperLinkBox = new VBox();
                    Label hyperLabel = new Label("Hyperlink:");
                    TextField field = new TextField();
                    Button ok = new Button("OK");
                    hyperLinkBox.getChildren().addAll(hyperLabel,field,ok);
                    hyperLinkBox.setAlignment(Pos.CENTER);
                    hyperLinkBox.getStyleClass().add(CSS_CLASS_COMPONENT_EDITOR);
                    Scene s = new Scene(hyperLinkBox);
                    
                    //AFTER HITTING OK, GET THE HYPERLINK
                    ok.setOnAction(e2 -> {
                        Hyperlink link = new Hyperlink(field.getText());
                        String hyperlinkText = "<a href='" + " '>"+ textField.getText(startingIndex, endingIndex) + "</a> ";
                        textField.setText(textField.getText().concat(link.getText()));
                        dialog.close();
                    });
                    dialog.setScene(s);
                    dialog.showAndWait();
                });
            }
            else{
                Label list = new Label("List:");
                
                HBox addOrRemove = new HBox();
                Button add = initChildButton(addOrRemove, ICON_ADD_PAGE, TOOLTIP_ADD_LIST, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
                Button remove = initChildButton(addOrRemove, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_LIST, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
                addOrRemove.getStyleClass().add(CSS_STYLE_LIST_BUTTONS);
                textFields = new VBox();
                textFields.getChildren().add(textField);
                //HYPERLINK CONTROLS
                textField.setAlignment(Pos.TOP_LEFT);
                textField.setPrefHeight(200);
                textField.setPrefColumnCount(50);
                nextDialog.getChildren().addAll(list,addOrRemove);
                Button hyperLink = initChildButton(nextDialog, ICON_HYPERLINK, TOOLTIP_HYPERLINK, CSS_CLASS_HYPERLINK_BUTTON,false);
                nextDialog.getChildren().addAll(textFields,okButton);
                addOrRemove.setAlignment(Pos.CENTER);
                textFields.getStyleClass().add("list");
                this.setHeight(600);
                //INITALIZE LIST IF PREVIOUS LIST HAD
                if(!listText.equals("")){
                    textFields.getChildren().clear();
                    String prevText = listText;
                    System.out.println("INIT: "+prevText);
                    String addText;
                    while(prevText.contains("\n")){
                        addText = prevText.substring(0,prevText.indexOf("\n"));
                        System.out.println("TEXT : " + addText);
                        textFields.getChildren().add(new TextField(addText));
                        //SKIP THE \n
                        addText = prevText.substring(prevText.indexOf("\n")+1);
                        prevText = prevText.substring(prevText.indexOf("\n")+1);
                    }
                    System.out.println("EDITED : " + prevText);
                }
                add.setOnAction(e->{
                    System.out.println("ADDING TO LIST");
                    TextField newTextField = new TextField();
                    newTextField.setAlignment(Pos.TOP_LEFT);
                    newTextField.setPrefHeight(200);
                    newTextField.setPrefColumnCount(50);
                    textFields.getChildren().add(newTextField);
                });
                remove.setOnAction(e->{
                    System.out.println("REMOVING TO LIST");
                    textFields.getChildren().remove(textFields.getChildren().get(textFields.getChildren().size()-1));
                });
                
            }
            nextDialog.setAlignment(Pos.CENTER);
            scene = new Scene(nextDialog);
            scene.getStylesheets().add(STYLE_SHEET_UI);
            setScene(scene);
            text = "";
            okButton.setOnAction(e ->{
                if(type.equals("List")){
                    for(int i = 0; i < textFields.getChildren().size(); i++){
                        String curr = ((TextField)textFields.getChildren().get(i)).getText();
                        text += curr + "\n";
                        System.out.println("TEXT : " + text);
                    }
                }
                else{
                    text = textField.getText();
                }
            TextComponent createdComponent = new TextComponent(type, text);
            //IF IT WAS A PARAGRAPH
            if(type.equals("Paragraph")){
                createdComponent.setFont(
                        (String) fontSelection.getSelectionModel().getSelectedItem());
            }
            else{
                createdComponent.setFont(pageToEdit.getPageFont());
            }
            if(editing){
                Component edit = pageToEdit.getSelectedComponent();
                pageToEdit.getComponents().set(pageToEdit.getComponents().indexOf(edit),createdComponent);
            }
            else{
                pageToEdit.addTextComponent(createdComponent);
            }
            System.out.println("ADDED TEXT COMPONENT : " + type + "-" + text);
            //SET TEXT COMPONENT AS SELECTED
            pageToEdit.setSelectedComponent(createdComponent);
            close();
        });
            
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
