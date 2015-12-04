package epg.controller;

import epg.view.PageSettingsView;
import epg.view.PortfolioGeneratorView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class PageSettingsController{
    PageSettingsView ui;
    
    public PageSettingsController(PageSettingsView initUI){
        ui = initUI;
    }

    public void handleSetLayout(ToggleGroup layoutButtons) {
        int layout = layoutButtons.getToggles().indexOf(layoutButtons.getSelectedToggle());
        ui.getPortfolio().getSelectedPage().setLayout(layout);
        System.out.println("LAYOUT CHANGED TO : " + layout);
    }

    public void handleSetColor(ToggleGroup colorButtons) {
        int color = colorButtons.getToggles().indexOf(colorButtons.getSelectedToggle());
        ui.getPortfolio().getSelectedPage().setColor(color);
        System.out.println("COLOR CHANGED TO : " + color);
    }
    
    
}