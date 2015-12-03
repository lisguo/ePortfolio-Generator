/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.model;

import javafx.collections.FXCollections;
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
    ObservableList<Component> components;
    
    public Page(String initName, String initLayout, String initColor,
            boolean initHasBannerImage){
        name = initName;
        layout = initLayout;
        color = initColor;
        hasBannerImage = initHasBannerImage;
        components = FXCollections.observableArrayList();
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
    public ObservableList<Component> getComponents(){
        return components;
    }
    public ObservableList<TextComponent> getTextComponents(){
        ObservableList<TextComponent> tc = FXCollections.observableArrayList();
        for(Component c : components){
            if(c instanceof TextComponent){
                tc.add((TextComponent)c);
            }
        }
        return tc;
    }
    public ObservableList<ImageComponent> getImageComponents(){
        ObservableList<ImageComponent> ic = FXCollections.observableArrayList();
        for(Component c : components){
            if(c instanceof ImageComponent){
                ic.add((ImageComponent)c);
            }
        }
        return ic;
    }
    public ObservableList<SlideShowComponent> getSlideShowComponents(){
        ObservableList<SlideShowComponent> sc = FXCollections.observableArrayList();
        for(Component c : components){
            if(c instanceof SlideShowComponent){
                sc.add((SlideShowComponent)c);
            }
        }
        return sc;
    }
    public ObservableList<VideoComponent> getVideoComponents(){
        ObservableList<VideoComponent> vc = FXCollections.observableArrayList();
        for(Component c : components){
            if (c instanceof VideoComponent){
                vc.add((VideoComponent) c);
            }
        }
        return vc;
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
