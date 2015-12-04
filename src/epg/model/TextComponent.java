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
    String style;
    int size;
    String font;
    
    public TextComponent(String initType, String initText){
        type = initType;
        text = initText;
        style = "None";
        font = "Righteous";
        size = 12;
        super.setComponentType("text");
    }
    //ACCESSOR
    public String getText(){
        return text;
    }
    public String getType(){
        return type;
    }
    public String getFont(){
        return font;
    }
    public String getStyle(){
        return style;
    }
    public int getSize(){
        return size;
    }
    //MUTATOR
    public void setText(String newText){
        text = newText;
    }
    public void setType(String newType){
        type = newType;
    }
    public void setFont(String newFont){
        font = newFont;
    }
    public void setStyle(String newStyle){
        style = newStyle;
    }
    public void setSize(int newSize){
        size = newSize;
    }
}
