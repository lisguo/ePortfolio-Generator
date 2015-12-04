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
    int layout;
    int color;
    String pageFont;
    
    //BANNER
    boolean hasBannerImage;
    String bannerImgPath;
    String bannerFileName;
    
    //FOOTER
    String footer;
    
    ObservableList<Component> components;
    Component selectedComponent;
    
    public Page(String initName, int initLayout, int initColor, String initFont,
            boolean initHasBannerImage){
        name = initName;
        layout = initLayout;
        color = initColor;
        pageFont = initFont;
        hasBannerImage = initHasBannerImage;
        components = FXCollections.observableArrayList();
        footer = "";
        bannerImgPath = "";
        bannerFileName = "";
    }
    
    //ACCESSOR
    public String getName(){
        return name;
    }
    public int getLayout(){
        return layout;
    }
    public int getColor(){
        return color;
    }
    public boolean getHasBannerImage(){
        return hasBannerImage;
    }
    public String getBannerImgPath(){
        return bannerImgPath;
    }
    public String getBannerFileName(){
        return bannerFileName;
    }
    public String getPageFont(){
        return pageFont;
    }
    public String getFooter(){
        return footer;
    }
    public ObservableList<Component> getComponents(){
        return components;
    }
    public Component getSelectedComponent(){
        return selectedComponent;
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
    public void addTextComponent(TextComponent tc){
        components.add(tc);
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
    public void setLayout(int newLayout){
        layout = newLayout;
    }
    public void setColor(int newColor){
        color = newColor;
    }
    public void setHasBannerImage(boolean newHasBannerImage){
        hasBannerImage = newHasBannerImage;
    }
    public void setBannerImgPath(String path){
        bannerImgPath = path;
    }
    public void setBannerFileName(String name){
        bannerFileName = name;
    }
    public void setPageFont(String font){
        pageFont = font;
    }
    public void setFooter(String f){
        footer = f;
    }
    public void setSelectedComponent(Component c){
        selectedComponent = c;
    }
    public boolean isComponentSelected() {
	return selectedComponent != null;
    }
    
    public boolean isSelectedComponent(Component testComponent) {
	return selectedComponent == testComponent;
    }
}
