/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.model;

/**
 *
 * @author BunnyRailgun
 */
public class TextComponent extends Component{
    //CAN BE PARAGRAPH, HEADER, OR LIST
    String type; 
    String text;
    
    public TextComponent(String initType, String initText){
        type = initType;
        text = initText;
    }
    //ACCESSOR
    public String getText(){
        return text;
    }
    public String getType(){
        return type;
    }
    //MUTATOR
    public void setText(String newText){
        text = newText;
    }
    public void setType(String newType){
        type = newType;
    }
}
