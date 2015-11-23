/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import epg.model.Component;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentView extends VBox{
    double width = 800;
    double height = 200;
    Component comp;
    
    public ComponentView(){
        setWidth(width);
        setHeight(height);
    }
    public ComponentView(Component initComponent){
        setWidth(width);
        comp = initComponent;
    }
    
    
}
