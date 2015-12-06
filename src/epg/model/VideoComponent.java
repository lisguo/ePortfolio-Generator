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
public class VideoComponent extends Component{
    String videoPath;
    String videoFileName;
    String caption;
    int width;
    int height;
    
    String captionFont;
    String captionStyle;
    int captionSize;
    
    public VideoComponent(String initVideoPath, String initVideoFileName,
            String initCaption, int initWidth, int initHeight){
        super.setComponentType("video");
        videoPath = initVideoPath;
        videoFileName = initVideoFileName;
        caption = initCaption;
        width = initWidth;
        height = initHeight;
        
        captionFont = "Righteous";
        captionStyle = "None";
        captionSize = 12;
        width = initWidth;
    }
    public String getVideoPath(){
        return videoPath;
    }
    public String getVideoFileName(){
        return videoFileName;
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
    public String getCaptionFont(){
        return captionFont;
    }
    public String getCaptionStyle(){
        return captionStyle;
    }
    public int getCaptionSize(){
        return captionSize;
    }
    
    public void setVideoPath(String initPath){
        videoPath = initPath;
    }
    public void setVideoFileName(String fileName){
        videoFileName = fileName;
    }
    public void setCaption(String newCaption){
        caption = newCaption;
    }
    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }
    public void setCaptionFont(String newFont){
        captionFont = newFont;
    }
    public void setCaptionStyle(String newStyle){
        captionStyle = newStyle;
    }
    public void setCaptionSize(int newSize){
        captionSize = newSize;
    }
}
