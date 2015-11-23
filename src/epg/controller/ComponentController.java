/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.PortfolioGenerator;
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
        editor.setHeight(300);
        editor.setWidth(400);
        editor.showAndWait();
        //ui.reloadComponentPane();
    }
    public void handleAddSlideShowComponent(){
        SlideShowComponentEditor editor = new SlideShowComponentEditor();
    }
}
