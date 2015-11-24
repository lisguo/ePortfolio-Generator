/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import static epg.StartupConstants.STYLE_SHEET_UI;
import epg.model.Component;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentView extends VBox{
    Component comp;
    
    public ComponentView(){
        getStylesheets().add(STYLE_SHEET_UI);
        getStyleClass().add(CSS_CLASS_COMPONENT);
    }
    public ComponentView(Component initComponent){
        comp = initComponent;
    }
    
    
}
