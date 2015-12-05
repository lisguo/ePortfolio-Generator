/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.model.Component;
import epg.model.Page;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentView extends VBox{
    Component comp;
    Page pageToEdit;
    
    public ComponentView(){
        getStylesheets().add(STYLE_SHEET_UI);
        setPrefWidth(1060);
        setAlignment(Pos.CENTER);
    }
    public ComponentView(Page pageToEdit, Component initComponent){
        getStylesheets().add(STYLE_SHEET_UI);
        setPrefWidth(1060);
        comp = initComponent;
        this.pageToEdit = pageToEdit;
    }
    public Component getComponent(){
        return comp;
    }
    
}
