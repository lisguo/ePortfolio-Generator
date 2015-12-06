/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_SMALL_LABEL;
import epg.model.Component;
import epg.model.Page;
import epg.model.TextComponent;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponentView extends ComponentView{
    HBox fontControls = new HBox();
    
    HBox fontControl = new HBox();
    Label fontLabel;
    ObservableList<String> fonts;
    ComboBox fontSelection;
    
    HBox styleControl = new HBox();
    Label styleLabel;
    ObservableList<String> styles;
    ComboBox styleSelection;
    
    HBox sizeControl = new HBox();
    Label fontSizeLabel;
    ObservableList<String> sizes;
    ComboBox sizeSelection;
    
    Label textType;
    Label text;
    
    Page pageToEdit;
    TextComponent textComponent;
    public TextComponentView(Page p, TextComponent textComponent){
        pageToEdit = p;
        this.textComponent = textComponent;
        textType = new Label("Type: " + textComponent.getType());
        fontControls = new HBox();
        fontLabel = new Label("Font: ");
        styleLabel = new Label("Style: ");
        fontSizeLabel = new Label("Size: ");
        
        fonts = FXCollections.observableArrayList();
        fonts.add("Righteous");
        fonts.add("Lora");
        fonts.add("Roboto Slab");
        fonts.add("Rock Salt");
        
        styles = FXCollections.observableArrayList();
        styles.add("Bold");
        styles.add("Italic");
        styles.add("Underline");
        
        sizes = FXCollections.observableArrayList();
        sizes.add("4");
        sizes.add("6");
        sizes.add("8");
        sizes.add("10");
        sizes.add("12");
        sizes.add("14");
        sizes.add("16");
        sizes.add("18");
        sizes.add("20");
        sizes.add("22");
        sizes.add("24");
        sizes.add("26");
        sizes.add("28");
        sizes.add("30");
        sizes.add("32");
        sizes.add("34");
        sizes.add("36");
        sizes.add("38");
        sizes.add("40");
        sizes.add("42");
        sizes.add("44");
        
        fontSelection = new ComboBox(fonts);
        fontSelection.getSelectionModel().select(textComponent.getFont());
        
        styleSelection = new ComboBox(styles);
        styleSelection.getSelectionModel().select("None");
        
        sizeSelection = new ComboBox(sizes);
        sizeSelection.getSelectionModel().select("12");
        
        fontControl.getChildren().addAll(fontLabel,fontSelection);
        styleControl.getChildren().addAll(styleLabel,styleSelection);
        sizeControl.getChildren().addAll(fontSizeLabel,sizeSelection);
        
        fontControls.setAlignment(Pos.CENTER);
        fontControls.getChildren().addAll(fontControl,styleControl,sizeControl);
        
        text = new Label(textComponent.getText());
        text.getStyleClass().add(CSS_SMALL_LABEL);
        getChildren().addAll(textType,fontControls,text);
        
        initTextComponentHandlers();
    }

    public void initTextComponentHandlers(){
        fontSelection.setOnAction(e -> {
            String font = (String)fontSelection.getSelectionModel().getSelectedItem();
            textComponent.setFont(font);
            System.out.println("FONT UPDATED TO : " + font);
        });
        styleSelection.setOnAction(e ->{
            String style = (String)styleSelection.getSelectionModel().getSelectedItem();
            textComponent.setStyle(style);
            System.out.println("STYLE UPDATED TO : " + style);
        });
        sizeSelection.setOnAction(e->{
            int size = Integer.getInteger((String)sizeSelection.getSelectionModel().getSelectedItem());
            textComponent.setSize(size);
            System.out.println("SIZE CHANGED TO " + size);
        });
    }
    
}
