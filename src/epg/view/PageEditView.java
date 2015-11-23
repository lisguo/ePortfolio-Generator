/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import epg.LanguagePropertyType;
import static epg.StartupConstants.CSS_CLASS_PAGE_EDIT_VIEW;
import static epg.StartupConstants.CSS_SMALL_LABEL;
import epg.model.Page;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class PageEditView extends VBox {
    // SLIDE THIS COMPONENT EDITS
    Page page;
    
    // CONTROLS FOR EDITING THE PAGE TITLE
    TextField titleTextField;

    /**
     * THis constructor initializes the full UI for this component, using
     * the initPage data for initializing values./
     * 
     * @param initPage The slide to be edited by this component.
     */
    public PageEditView(Page initPage) {
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
	this.getStyleClass().add(CSS_CLASS_PAGE_EDIT_VIEW);
	
	// KEEP THE SLIDE FOR LATER
	page = initPage;

	// SETUP THE TITLE CONTROLS
	titleTextField = new TextField();
	titleTextField.setText(page.getName());
        titleTextField.getStyleClass().add(CSS_SMALL_LABEL);

	// LAY EVERYTHING OUT INSIDE THIS COMPONENT
	getChildren().addAll(titleTextField);

	// SETUP THE EVENT HANDLERS
	titleTextField.textProperty().addListener(e -> {
	    String text = titleTextField.getText();
	    page.setName(text);	 
	});
    }
}