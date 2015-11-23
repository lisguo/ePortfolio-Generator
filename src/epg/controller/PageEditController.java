/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.controller;

import epg.model.PortfolioModel;
import epg.view.PortfolioGeneratorView;

/**
 *
 * @author BunnyRailgun
 */
public class PageEditController {
    PortfolioGeneratorView ui;
    public PageEditController(PortfolioGeneratorView initUi){
        ui = initUi;
    }
    public void processRemovePageRequest() {
	PortfolioModel portfolio = ui.getPortfolio();
	//portfolio.removeSelectedPage();
	ui.reloadPortfolioPane();
        //Enable save
        //ui.updateToolbarControls(false);
    }
}
