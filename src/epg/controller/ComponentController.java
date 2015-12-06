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
import epg.view.PageSettingsView;
import epg.view.SlideShowComponentEditor;
import epg.view.TextComponentEditor;
import epg.view.VideoComponentEditor;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentController {
    PageSettingsView ui;
    
    public ComponentController(PageSettingsView initUI){
        ui = initUI;
    }
    
    public void handleAddTextComponent() throws IOException{
        System.out.println("ADD TEXT COMPONENT CLICKED");
        TextComponentEditor editor = new TextComponentEditor(ui.getPageToEdit());
        editor.setHeight(350);
        editor.setWidth(400);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleAddImageComponent() throws IOException{
        System.out.println("ADD IMAGE COMPONENT CLICKED!");
        ImageComponentEditor editor = new ImageComponentEditor(ui.getPortfolio(), ui.getPageToEdit());
        editor.setHeight(400);
        editor.setWidth(500);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleAddSlideShowComponent() throws Exception{
        System.out.println("ADD SLIDE SHOW COMPONENT CLICKED!");
        SlideShowComponentEditor editor = new SlideShowComponentEditor(ui.getPortfolio(), ui.getPageToEdit());
        Stage s = new Stage();
        s.setHeight(900);
        s.setWidth(1080);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleAddVideoComponent() throws IOException{
        VideoComponentEditor editor = new VideoComponentEditor(ui.getPortfolio(), ui.getPageToEdit());
        editor.setHeight(350);
        editor.setWidth(500);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleEditComponent(Component compToEdit) throws IOException{
        System.out.println("EDIT COMPONENT CLICKED");
        if(compToEdit.getComponentType().equals("text")){
            TextComponent tc = ((TextComponent)compToEdit);
            TextComponentEditor editor = new TextComponentEditor(ui.getPageToEdit(),tc);
            editor.setHeight(350);
            editor.setWidth(400);
            editor.showAndWait();
            ui.reloadComponentPane();
        }
    }
    public void handleRemoveComponent(Component compToEdit) throws IOException{
        System.out.println("REMOVE COMPONENT CLICKED");
        ObservableList<Component> components = ui.getPageToEdit().getComponents();
        components.remove(compToEdit);
        ui.reloadComponentPane();
    }
}
