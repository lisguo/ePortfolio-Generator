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
public class SlideShowComponent extends Component{
    String title;
    ObservableList<Slide> slides;
    
    public SlideShowComponent(String title, ObservableList<Slide> slides){
        super.setComponentType("slideshow");
        this.title = title;
        this.slides = slides;
    }
    public String getTitle(){
        return title;
    }
    public ObservableList<Slide> getSlides(){
        return slides;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }
    public void setSlides(ObservableList<Slide> newSlides){
        slides = newSlides;
    }
}
