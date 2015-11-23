/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import epg.model.PortfolioModel;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponentEditor extends Stage{
    VBox vBox;
    Label textType;
    ComboBox textTypeComboBox;
    TextField text;
    Button okButton;
    
    public TextComponentEditor(PortfolioModel portfolio){
        
    }
}
