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
    
    public PortfolioModel(PortfolioGeneratorView initUI){
        ui = initUI;
        pages = FXCollections.observableArrayList();
        //FOR HOMEWORK 6 ONLY
        addPage("Homepage","layout1","blue", true);
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
    
    //MUTATOR
    public void setSelectedPage(Page newPage){
        selectedPage = newPage;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }
    
    public void addPage(   String initName,
			    String initLayout,
			    String initColor,
                            boolean initHasBannerImage) {
	Page slideToAdd = new Page(initName, initLayout, initColor,initHasBannerImage);
	pages.add(slideToAdd);
	//ui.reloadPortfolioPane();
    }
    
     public void removeSelectedPage() {
	if (isPageSelected()) {
	    pages.remove(selectedPage);
	    selectedPage = null;
	    ui.reloadPortfolioPane();
	}
    }
     public void previous() {
	if (selectedPage == null)
	    return;
	else {
	    int index = pages.indexOf(selectedPage);
	    index--;
	    if (index < 0)
		index = pages.size() - 1;
	    selectedPage = pages.get(index);
	}
    }
     public void next() {
    	if (selectedPage == null)
	    return;
	else {
	    int index = pages.indexOf(selectedPage);
	    index++;
	    if (index >= pages.size())
		index = 0;
	    selectedPage = pages.get(index);
	}
    }    
    public void reset() {
	pages.clear();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	title = props.getProperty(LanguagePropertyType.DEFAULT_PORFOLIO_TITLE);
	selectedPage = null;
    }
}
