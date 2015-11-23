/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.model;

import javafx.collections.ObservableList;

/**
 *
 * @author BunnyRailgun
 */
public class Page {
    String name;
    String layout;
    String color;
    boolean hasBannerImage;
    ObservableList<TextComponent> textComponents;
    ObservableList<ImageComponent> imageComponents;
    ObservableList<SlideShowComponent> slideShowComponents;
    ObservableList<VideoComponent> videoComponents;
    
    public Page(String initName, String initLayout, String initColor,
            boolean initHasBannerImage){
        name = initName;
        layout = initLayout;
        color = initColor;
        hasBannerImage = initHasBannerImage;
    }
    
    //ACCESSOR
    public String getName(){
        return name;
    }
    public String getLayout(){
        return layout;
    }
    public String getColor(){
        return color;
    }
    public boolean getHasBannerImage(){
        return hasBannerImage;
    }
    
    //MUTATOR
    public void setName(String newName){
        name = newName;
    }
    public void setLayout(String newLayout){
        layout = newLayout;
    }
    public void setColor(String newColor){
        color = newColor;
    }
    public void setHasBannerImage(boolean newHasBannerImage){
        hasBannerImage = newHasBannerImage;
    }
}
