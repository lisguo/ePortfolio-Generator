/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.view;

import epg.LanguagePropertyType;
import static epg.LanguagePropertyType.TOOLTIP_NEXT_SLIDE;
import static epg.LanguagePropertyType.TOOLTIP_PREVIOUS_SLIDE;
import static epg.StartupConstants.CSS_CLASS_COMPONENT_TOOLBAR_BUTTON;
import static epg.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_HBOX;
import static epg.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import static epg.StartupConstants.ICON_NEXT;
import static epg.StartupConstants.ICON_PREVIOUS;
import static epg.StartupConstants.PATH_ICONS;
import epg.error.ErrorHandler;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.Page;
import epg.model.Slide;
import epg.model.SlideShowComponent;
import java.io.File;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;

/**
 *
 * @author BunnyRailgun
 */
public class SlideShowComponentView extends ComponentView{
    ObservableList<String> images;
    ObservableList<String> captions;
    ObservableList<Slide> slides;
    int currSlide = 0;
    Label title;
    ImageView image;
    Label caption;
    
    HBox buttonsPane;
    Button prev;
    Button next;
    
    Page pageToEdit;
    
    public SlideShowComponentView(Page pageToEdit, SlideShowComponent ssc){
        slides = ssc.getSlides();
        images = FXCollections.observableArrayList();
        captions = FXCollections.observableArrayList();
        for(Slide s : ssc.getSlides()){
            images.add(s.getImagePath() + SLASH + s.getImageFileName());
            captions.add(s.getCaption());
        }
        this.pageToEdit = pageToEdit;
        title = new Label(ssc.getTitle());
        
        buttonsPane = new HBox();
        prev = initChildButton(buttonsPane, ICON_PREVIOUS, TOOLTIP_PREVIOUS_SLIDE,
                CSS_CLASS_COMPONENT_TOOLBAR_BUTTON, false);
        next = initChildButton(buttonsPane, ICON_NEXT, TOOLTIP_NEXT_SLIDE,
                CSS_CLASS_COMPONENT_TOOLBAR_BUTTON, false);
        buttonsPane.setAlignment(Pos.CENTER);
        title = new Label(ssc.getTitle());
        image = new ImageView();
        caption = new Label();
        updateImage();
        updateCaption();
        initHandlers();
        getChildren().addAll(title, image,caption,buttonsPane);
        
    }
    public void initHandlers(){
        prev.setOnAction(e -> {
            if(currSlide == 0)
                currSlide = slides.size()-1;
            else
                currSlide--;
            updateImage();
            updateCaption();
        });
        next.setOnAction(e->{
            if(currSlide == slides.size()-1){
                currSlide = 0;
            }
            else
                currSlide++;
            updateImage();
            updateCaption();
        });
    }
    public void updateImage(){
        String imgPath = images.get(currSlide);
        File file = new File(imgPath);
        try{
            URL fileURL = file.toURI().toURL();
            Image componentImage = new Image(fileURL.toExternalForm());
            image.setImage(componentImage);
            
            //RESZIZE
            double scaledHeight = 150;
	    double perc = scaledHeight / componentImage.getHeight();
	    double scaledWidth = componentImage.getWidth() * perc;
	    image.setFitWidth(scaledWidth);
	    image.setFitHeight(scaledHeight);
        }catch (Exception e){
            ErrorHandler eH = new ErrorHandler();
            eH.processError("ERROR IN SHOWING IMAGE");
        }
    }
    public void updateCaption(){
        caption.setText(captions.get(currSlide));
    }
    public javafx.scene.control.Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    LanguagePropertyType tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	javafx.scene.control.Button button = new javafx.scene.control.Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
}
