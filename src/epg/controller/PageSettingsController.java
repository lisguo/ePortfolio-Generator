package epg.controller;

import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.Page;
import epg.view.PageSettingsView;
import epg.view.PortfolioGeneratorView;
import java.io.File;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

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
            selectedPage.setBannerFileName("");
            selectedPage.setBannerImgPath("");
        }
        System.out.println("PAGE HAS BANNER IMAGE : " + selectedPage.getHasBannerImage());
        //REFRESH
        ui.getChildren().clear();
        ui.showPageSettingsWorkspace();
    }

    public void handleSetColor(ToggleGroup colorButtons) {
        int color = colorButtons.getToggles().indexOf(colorButtons.getSelectedToggle());
        selectedPage.setColor(color);
        System.out.println("COLOR CHANGED TO : " + color);
        ui.reloadUISettings(false);
    }
    public void handleSetPageFont(ComboBox pageFont){
        String font = (String)(pageFont.getSelectionModel().getSelectedItem());
        selectedPage.setPageFont(font);
        System.out.println("FONT CHANGED TO : " + font);
        ui.reloadUISettings(false);
    }
    public void handleSetStudentName(TextField textField){
        String name = textField.getText();
        ui.getPortfolio().setStudentName(name);
        System.out.println("STUDENT NAME CHANGED TO : " + name);
        ui.reloadUISettings(false);
    }
    public void handleSetFooter(TextField textField){
        String footer = textField.getText();
        selectedPage.setFooter(footer);
        System.out.println("FOOTER CHANGD TO : " + footer);
        ui.reloadUISettings(false);
    }
    public void handleBannerSelection(){
        FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
        File f = new File(PATH_PORTFOLIOS + ui.getPortfolio().getTitle() + SLASH + selectedPage.getName() + SLASH);
        f.mkdirs();
	imageFileChooser.setInitialDirectory(f);
        
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
	    selectedPage.setBannerFileName(fileName);
            selectedPage.setBannerImgPath(path);
            //REFRESH
            ui.getChildren().clear();
            ui.showPageSettingsWorkspace();
	}
        
        System.out.println("BANNER : " + selectedPage.getBannerImgPath()
                                         + selectedPage.getBannerFileName());
        ui.reloadUISettings(false);
    }
    
}