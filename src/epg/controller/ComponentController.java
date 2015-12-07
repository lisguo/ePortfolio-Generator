/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.PortfolioGenerator;
import epg.model.Component;
import epg.model.ImageComponent;
import epg.model.SlideShowComponent;
import epg.model.TextComponent;
import epg.model.VideoComponent;
import epg.view.ImageComponentEditor;
import epg.view.PageSettingsView;
import epg.view.SlideShowComponentEditor;
import epg.view.TextComponentEditor;
import epg.view.VideoComponentEditor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void handleAddTextComponent(){
        System.out.println("ADD TEXT COMPONENT CLICKED");
        TextComponentEditor editor = new TextComponentEditor(ui.getPageToEdit());
        editor.setHeight(350);
        editor.setWidth(400);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleAddImageComponent(){
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
    public void handleAddVideoComponent(){
        VideoComponentEditor editor = new VideoComponentEditor(ui.getPortfolio(), ui.getPageToEdit());
        editor.setHeight(350);
        editor.setWidth(500);
        editor.showAndWait();
        ui.reloadComponentPane();
    }
    public void handleEditComponent(Component compToEdit){
        System.out.println("EDIT COMPONENT CLICKED");
        if(compToEdit.getComponentType().equals("text")){
            TextComponent tc = ((TextComponent)compToEdit);
            TextComponentEditor editor = new TextComponentEditor(ui.getPageToEdit(),tc);
            editor.setHeight(350);
            editor.setWidth(400);
            editor.showAndWait();
            ui.reloadComponentPane();
        }
        if(compToEdit.getComponentType().equals("image")){
            ImageComponent ic = (ImageComponent)compToEdit;
            ImageComponentEditor editor = new ImageComponentEditor(ui.getPageToEdit(), ic);
            editor.setHeight(400);
            editor.setWidth(400);
            editor.showAndWait();
            ui.reloadComponentPane();
        }
        if(compToEdit.getComponentType().equals("slideshow")){
            try {
                SlideShowComponent ssc = (SlideShowComponent) compToEdit;
                SlideShowComponentEditor editor = new SlideShowComponentEditor(ui.getPortfolio(),ui.getPageToEdit(), ssc);
                Stage s = new Stage();
                s.setHeight(900);
                s.setWidth(1080);
                editor.showAndWait();
                ui.reloadComponentPane();
            } catch (IOException ex) {
                Logger.getLogger(ComponentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(compToEdit.getComponentType().equals("video")){
            VideoComponent vc = (VideoComponent)compToEdit;
            VideoComponentEditor editor = new VideoComponentEditor(ui.getPageToEdit(), vc);
                editor.setHeight(350);
                editor.setWidth(400);
                editor.showAndWait();
                ui.reloadComponentPane();
        }
    }
    public void handleRemoveComponent(Component compToEdit){
        System.out.println("REMOVE COMPONENT CLICKED");
        ObservableList<Component> components = ui.getPageToEdit().getComponents();
        components.remove(compToEdit);
        ui.reloadComponentPane();
    }
}
