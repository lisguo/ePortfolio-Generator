/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.model;

import epg.LanguagePropertyType;
import epg.view.PortfolioGeneratorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class PortfolioModel {
    PortfolioGeneratorView ui;
    ObservableList<Page> pages;
    Page selectedPage;
    String title;
    String studentName;
    
    public PortfolioModel(PortfolioGeneratorView initUI){
        ui = initUI;
        pages = FXCollections.observableArrayList();
        reset();
    }
    
    //ACCESSOR
     public boolean isPageSelected() {
	return selectedPage != null;
    }
    
    public boolean isSelectedPage(Page testPage) {
	return selectedPage == testPage;
    }
    public ObservableList<Page> getPages(){
        return pages;
    }
    public Page getSelectedPage(){
        return selectedPage;
    }
    public String getTitle(){
        return title;
    }
    public String getStudentName(){
        return studentName;
    }
    
    //MUTATOR
    public void setSelectedPage(Page newPage){
        selectedPage = newPage;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }
    public void setStudentName(String newName){
        studentName = newName;
    }
    
    public void addPage(   String initName,
			    int initLayout,
			    int initColor, 
                            String initFont,
                            boolean initHasBannerImage) {
	Page pageToAdd = new Page(initName, initLayout, 
                initColor,initFont, initHasBannerImage);
	pages.add(pageToAdd);
	ui.reloadPageEditorPane();
    }
    
     public void removeSelectedPage() {
	if (isPageSelected()) {
	    pages.remove(selectedPage);
	    selectedPage = null;
	    ui.reloadPageEditorPane();
	}
    } 
    public void reset() {
	pages.clear();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	title = props.getProperty(LanguagePropertyType.DEFAULT_PORTFOLIO_TITLE);
	selectedPage = null;
        studentName = "ENTER STUDENT NAME";
    }
}
