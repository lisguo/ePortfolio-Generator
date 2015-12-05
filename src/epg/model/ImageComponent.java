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
public class ImageComponent extends Component{
    String imagePath;
    String imageFileName;
    String caption;
    int width;
    int height;
    String floatOption;
    
    String captionFont;
    String captionStyle;
    int captionSize;
    
    public ImageComponent(String initImagePath, String initImageFileName,
          String initCaption, int initWidth, int initHeight, String initFloatOption){
        imagePath = initImagePath;
        imageFileName = initImageFileName;
        caption = initCaption;
        //CAPTION CONTROLS
        captionFont = "Righteous";
        captionStyle = "None";
        captionSize = 12;
        width = initWidth;
        height = initHeight;
        floatOption = initFloatOption;
    }
    public String getImagePath(){
        return imagePath;
    }
    public String getImageFileName(){
        return imageFileName;
    }
    public String getCaption(){
        return caption;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public String getFloatOption(){
        return floatOption;
    }
    public String getCaptionFont(){
        return captionFont;
    }
    public String getCaptionStyle(){
        return captionStyle;
    }
    public int getCaptionSize(){
        return captionSize;
    }
    public void setImagePath(String initImagePath){
        imagePath = initImagePath;
    }
    public void setImageFileName(String initFileName){
        imageFileName = initFileName;
    }
    public void setCaption(String initCaption){
        caption = initCaption;
    }
    public void setWidth(int initWidth){
        width = initWidth;
    }
    public void setHeight(int initHeight){
        height = initHeight;
    }
    public void setFloatOption(String initFloatOption){
        floatOption = initFloatOption;
    }
    public void setCaptionFont(String font){
        captionFont = font;
    }
    public void setCaptionStyle(String style){
        captionStyle = style;
    }
    public void setCaptionSize(int size){
        captionSize = size;
    }
}
