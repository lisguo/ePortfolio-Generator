/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.model.PortfolioModel;
import epg.view.PortfolioGeneratorView;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class PageEditController {
    PortfolioGeneratorView ui;
    public PageEditController(PortfolioGeneratorView initUi){
        ui = initUi;
    }
     public void processAddPageRequest() {
         System.out.println("ADD PAGE");
	PortfolioModel portfolio = ui.getPortfolio();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        portfolio.addPage("New Page", 1, 1, false);
        //SET NEW PAGE AS SELECTED
        portfolio.setSelectedPage(portfolio.getPages().get(portfolio.getPages().size()-1));
        //Enable save
        ui.updateSiteToolbarControls(false);
        ui.reloadPageEditorPane();
    }
    public void processRemovePageRequest() {
	PortfolioModel portfolio = ui.getPortfolio();
	portfolio.removeSelectedPage();
	ui.reloadPageEditorPane();
        //Enable save
        ui.updateSiteToolbarControls(false);
    }
}
