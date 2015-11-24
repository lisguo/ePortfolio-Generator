/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.PortfolioGenerator;
import epg.model.Component;
import epg.model.TextComponent;
import epg.view.ImageComponentEditor;
import epg.view.PortfolioGeneratorView;
import epg.view.SlideShowComponentEditor;
import epg.view.TextComponentEditor;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentController {
    PortfolioGeneratorView ui;
    
    public ComponentController(PortfolioGeneratorView initUI){
        ui = initUI;
    }
    
    public void handleAddTextComponent(){
        System.out.println("ADD TEXT COMPONENT CLICKED");
        TextComponentEditor editor = new TextComponentEditor(ui.getPortfolio());
        editor.setHeight(350);
        editor.setWidth(400);
        editor.showAndWait();
        //ui.reloadComponentPane();
    }
    public void handleAddImageComponent(){
        System.out.println("ADD IMAGE COMPONENT CLICKED!");
        ImageComponentEditor editor = new ImageComponentEditor(ui.getPortfolio());
        editor.setHeight(400);
        editor.setWidth(400);
        editor.showAndWait();
        //ui.reloadComponentPane();
    }
    public void handleAddSlideShowComponent(){
        SlideShowComponentEditor editor = new SlideShowComponentEditor();
    }
    public void handleAddVideoComponent(){
        
    }
    public void handleEditComponent(Component compToEdit){
        System.out.println("EDIT COMPONENT CLICKED");
        if(compToEdit.getComponentType().equals("text")){
            TextComponent tc = ((TextComponent)compToEdit);
            TextComponentEditor editor = new TextComponentEditor(tc);
            editor.setHeight(350);
            editor.setWidth(400);
            editor.showAndWait();
        }
    }
}
