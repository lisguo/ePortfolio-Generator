/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import static epg.StartupConstants.CSS_CLASS_COMPONENT;
import javafx.scene.layout.VBox;

/**
 *
 * @author BunnyRailgun
 */
public class ComponentView extends VBox{
    double width = 500;
    
    public ComponentView(){
        setWidth(width);
        getStyleClass().add(CSS_CLASS_COMPONENT);
    }
    
    
}
