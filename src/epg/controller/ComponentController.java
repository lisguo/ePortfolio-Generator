/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.PortfolioGenerator;
import epg.view.PortfolioGeneratorView;
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
        editor.setHeight(800);
        editor.setWidth(400);
        editor.showAndWait();
    }
}
