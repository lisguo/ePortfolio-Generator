package epg.controller;

import epg.model.Page;
import epg.view.PageSettingsView;
import epg.view.PortfolioGeneratorView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class PageSettingsController{
    PageSettingsView ui;
    Page selectedPage;
    
    public PageSettingsController(PageSettingsView initUI){
        ui = initUI;
        selectedPage = ui.getPortfolio().getSelectedPage();
    }

    public void handleSetLayout(ToggleGroup layoutButtons) {
        int layout = layoutButtons.getToggles().indexOf(layoutButtons.getSelectedToggle());
        selectedPage.setLayout(layout);
        System.out.println("LAYOUT CHANGED TO : " + layout);
        
        //CHECK IF THERE IS BANNER
        if(layout == 0 || layout == 1 || layout == 2){
            selectedPage.setHasBannerImage(true);
        }
        else{
            selectedPage.setHasBannerImage(false);
        }
        System.out.println("PAGE HAS BANNER IMAGE : " + selectedPage.getHasBannerImage());
    }

    public void handleSetColor(ToggleGroup colorButtons) {
        int color = colorButtons.getToggles().indexOf(colorButtons.getSelectedToggle());
        selectedPage.setColor(color);
        System.out.println("COLOR CHANGED TO : " + color);
    }
    public void handleSetPageFont(ComboBox pageFont){
        String font = (String)(pageFont.getSelectionModel().getSelectedItem());
        selectedPage.setPageFont(font);
        System.out.println("FONT CHANGED TO : " + font);
    }
    public void handleSetStudentName(TextField textField){
        String name = textField.getText();
        ui.getPortfolio().setStudentName(name);
        System.out.println("STUDENT NAME CHANGED TO : " + name);
    }
    public void handleSetFooter(TextField textField){
        String footer = textField.getText();
        selectedPage.setFooter(footer);
        System.out.println("FOOTER CHANGD TO : " + footer);
    }
    
}