/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import static epg.StartupConstants.PATH_PORTFOLIOS;
import epg.model.ImageComponent;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.Slide;
import epg.model.SlideShowComponent;
import epg.model.TextComponent;
import epg.model.VideoComponent;
import java.math.BigDecimal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PortfolioFileManager {

    // JSON FILE READING AND WRITING CONSTANTS

    public static String JSON_TITLE = "title";
    public static String JSON_STUDENT_NAME = "student_name";
    public static String JSON_PAGES = "pages";
    
    //PAGE
    public static String JSON_PAGE_NAME = "page_name";
    public static String JSON_LAYOUT = "layout";
    public static String JSON_COLOR = "color";
    public static String JSON_PAGE_FONT = "page_font";
    public static String JSON_BANNER_PATH = "banner_path";
    public static String JSON_BANNER_FILE_NAME = "banner_file_name";
    public static String JSON_FOOTER = "footer";
    public static String JSON_TEXT = "text";
    public static String JSON_HAS_BANNER = "has_banner";
    
    //TEXT COMPONENTS
    public static String JSON_TEXT_COMPONENTS = "text_components";
    public static String JSON_TEXT_TYPE = "text_type";
    public static String JSON_STYLE = "style";
    public static String JSON_FONT = "font";
    public static String JSON_SIZE = "size";
            
    //IMAGE COMPONENTS
    public static String JSON_IMAGE_COMPONENTS = "image_components";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_IMAGE_FILE_NAME = "image_file_name";
    public static String JSON_IMAGE_CAPTION = "image_caption";
    public static String JSON_IMAGE_WIDTH = "image_width";
    public static String JSON_IMAGE_HEIGHT = "image_height";
    public static String JSON_IMAGE_FLOAT_OPTION = "image_float_option";
    public static String JSON_IMAGE_CAPTION_FONT = "image_caption_font";
    public static String JSON_IMAGE_CAPTION_STYLE = "image_caption_style";
    public static String JSON_IMAGE_CAPTION_SIZE = "image_caption_size";
    
    //SLIDESHOW COMPONENTS
    public static String JSON_SLIDESHOW_COMPONENTS = "slideshow_components";
    public static String JSON_SLIDESHOW_TITLE = "slideshow_title";
    public static String JSON_SLIDES = "slides";
    //FOR SLIDES
    public static String JSON_SLIDE_IMAGE_FILE_NAME = "slide_image_file_name";
    public static String JSON_SLIDE_IMAGE_PATH = "slide_image_path";
    public static String JSON_SLIDE_CAPTION = "slide_caption";
    
    //VIDEO COMPONENT
    public static String JSON_VIDEO_COMPONENTS = "video_components";
    public static String JSON_VIDEO_PATH = "video_path";
    public static String JSON_VIDEO_FILE_NAME = "video_file_name";
    public static String JSON_VIDEO_CAPTION = "video_caption";
    public static String JSON_VIDEO_WIDTH = "video_width";
    public static String JSON_VIDEO_HEIGHT = "video_height";
    public static String JSON_VIDEO_CAPTION_FONT = "video_caption_font";
    public static String JSON_VIDEO_CAPTION_STYLE = "video_caption_style";
    public static String JSON_VIDEO_CAPTION_SIZE = "video_caption_size";
    public static String JSON_EXT = ".json";
    public static String SLASH = "/";

    /**
     * This method saves all the data associated with a slide show to a JSON
     * file.
     *
     * @param portfolioToSave The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    public void savePortfolio(PortfolioModel portfolioToSave) throws IOException {
	StringWriter sw = new StringWriter();
        
        //BUILD PAGESARRAY
        JsonArray pagesJsonArray = makePagesJsonArray(portfolioToSave.getPages());
        //BUILD PORTFOLIO
        System.out.println("PORTFOLIO TITLE : " + portfolioToSave.getTitle());
        JsonObject portfolioJsonObject = Json.createObjectBuilder()
                .add(JSON_TITLE, portfolioToSave.getTitle())
                .add(JSON_STUDENT_NAME, portfolioToSave.getStudentName())
                .add(JSON_PAGES, pagesJsonArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(portfolioJsonObject);
        jsonWriter.close();
        
        //INIT WRITER
        String portfolioTitle = "" + portfolioToSave.getTitle();
        String jsonFilePath = PATH_PORTFOLIOS + SLASH + portfolioTitle + SLASH + portfolioTitle+ JSON_EXT;
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(portfolioJsonObject);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(jsonFilePath);
	pw.write(prettyPrinted);
	pw.close();
	System.out.println(prettyPrinted);
    }
    public void loadPortfolio(PortfolioModel portfolioToLoad, String jsonFilePath) throws IOException{
        JsonObject json = loadJSONFile(jsonFilePath);
        
        portfolioToLoad.reset();;
        portfolioToLoad.setTitle(json.getString(JSON_TITLE));
        JsonArray jsonPagesArray = json.getJsonArray(JSON_PAGES);
        for(int i = 0 ; i < jsonPagesArray.size(); i++){
            JsonObject pageJso = jsonPagesArray.getJsonObject(i);
            Page page = new Page(pageJso.getString(JSON_PAGE_NAME),
                    pageJso.getInt(JSON_LAYOUT),
                    pageJso.getInt(JSON_COLOR),
                    pageJso.getString(JSON_PAGE_FONT),
                    pageJso.getBoolean(JSON_HAS_BANNER));
            if(pageJso.getBoolean(JSON_HAS_BANNER) == true){
                page.setBannerFileName(pageJso.getString(JSON_BANNER_FILE_NAME));
                page.setBannerImgPath(pageJso.getString(JSON_BANNER_PATH));
            }
            //TEXT COMPONENTS
            JsonArray jsonTextComponentsArray = pageJso.getJsonArray(JSON_TEXT_COMPONENTS);
            for(int j = 0; j < jsonTextComponentsArray.size(); j++){
                JsonObject tcJso = jsonTextComponentsArray.getJsonObject(j);
                TextComponent tc = new TextComponent(tcJso.getString(JSON_TEXT_TYPE),
                                                    tcJso.getString(JSON_TEXT));
                tc.setFont(tcJso.getString(JSON_FONT));
                tc.setSize(tcJso.getInt(JSON_SIZE));
                tc.setStyle(tcJso.getString(JSON_STYLE));
                page.addTextComponent(tc);
            }
            //IMAGE COMPONENTS
            JsonArray jsonImageComponentsArray = pageJso.getJsonArray(JSON_IMAGE_COMPONENTS);
            for(int j = 0; j < jsonImageComponentsArray.size(); j++){
                JsonObject icJso = jsonImageComponentsArray.getJsonObject(j);
                ImageComponent ic = new ImageComponent(icJso.getString(JSON_IMAGE_PATH),
                icJso.getString(JSON_IMAGE_FILE_NAME), icJso.getString(JSON_IMAGE_CAPTION)
                        ,icJso.getInt(JSON_IMAGE_WIDTH),icJso.getInt(JSON_IMAGE_HEIGHT),
                        icJso.getString(JSON_IMAGE_FLOAT_OPTION));
                ic.setCaptionFont(icJso.getString(JSON_IMAGE_CAPTION_FONT));
                ic.setCaptionStyle(icJso.getString(JSON_IMAGE_CAPTION_STYLE));
                ic.setCaptionSize(icJso.getInt(JSON_IMAGE_CAPTION_SIZE));
                page.addImageComponent(ic);
            }
            //SLIDESHOW COMPONENTS
            JsonArray jsonSlideShowComponentsArray = pageJso.getJsonArray(JSON_SLIDESHOW_COMPONENTS);
            for(int j = 0; j < jsonSlideShowComponentsArray.size(); j++){
                JsonObject sscJso = jsonSlideShowComponentsArray.getJsonObject(j);
                //SLIDES
                ObservableList<Slide> slides = FXCollections.observableArrayList();
                JsonArray jsonSlidesArray = sscJso.getJsonArray(JSON_SLIDES);
                for(int k = 0; k < jsonSlidesArray.size(); k++){
                    JsonObject slideJso = jsonSlidesArray.getJsonObject(k);
                    Slide s = new Slide(slideJso.getString(JSON_SLIDE_IMAGE_FILE_NAME),
                            slideJso.getString(JSON_SLIDE_IMAGE_PATH),
                            slideJso.getString(JSON_SLIDE_CAPTION));
                    slides.add(s);
                }
                SlideShowComponent ssc = new SlideShowComponent(
                        sscJso.getString(JSON_SLIDESHOW_TITLE),slides);
                page.addSlideShowComponent(ssc);
            }
            //VIDEO COMPONENTS
            JsonArray jsonVideoComponentsArray = pageJso.getJsonArray(JSON_VIDEO_COMPONENTS);
            for(int j = 0; j < jsonVideoComponentsArray.size(); j++){
                JsonObject vcJso = jsonVideoComponentsArray.getJsonObject(j);
                VideoComponent vc = new VideoComponent(
                    vcJso.getString(JSON_VIDEO_PATH),
                    vcJso.getString(JSON_VIDEO_FILE_NAME),
                    vcJso.getString(JSON_VIDEO_CAPTION),
                    vcJso.getInt(JSON_VIDEO_WIDTH),
                    vcJso.getInt(JSON_VIDEO_HEIGHT));
                vc.setCaptionFont(vcJso.getString(JSON_VIDEO_CAPTION_FONT));
                vc.setCaptionStyle(vcJso.getString(JSON_VIDEO_CAPTION_STYLE));
                vc.setCaptionSize(vcJso.getInt(JSON_VIDEO_CAPTION_SIZE));
                page.addVideoComponent(vc);
            }
            portfolioToLoad.addPage(page);
        }
    }
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException{
        InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
	JsonObject json = loadJSONFile(jsonFilePath);
	ArrayList<String> items = new ArrayList();
	JsonArray jsonArray = json.getJsonArray(arrayName);
	for (JsonValue jsV : jsonArray) {
	    items.add(jsV.toString());
	}
	return items;
    }
    private JsonArray makePagesJsonArray(List<Page> pages){
        System.out.println("MAKING PAGES ARRAY");
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Page page : pages){
            JsonObject jso = makePageJsonObject(page);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makePageJsonObject(Page page){
        System.out.println("MAKING PAGE OBJECT");
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_PAGE_NAME, page.getName())
                .add(JSON_LAYOUT, page.getLayout())
                .add(JSON_COLOR, page.getColor())
                .add(JSON_PAGE_FONT, page.getPageFont())
                .add(JSON_FOOTER, page.getFooter())
                .add(JSON_BANNER_PATH, page.getBannerImgPath())
                .add(JSON_BANNER_FILE_NAME, page.getBannerFileName())  
                .add(JSON_HAS_BANNER, page.getHasBannerImage())
                .add(JSON_TEXT_COMPONENTS, makeTextComponentsJsonArray(page.getTextComponents()))
                .add(JSON_IMAGE_COMPONENTS, makeImageComponentsJsonArray(page.getImageComponents()))
                .add(JSON_SLIDESHOW_COMPONENTS, makeSlideShowComponentsJsonArray(page.getSlideShowComponents()))
                .add(JSON_VIDEO_COMPONENTS, makeVideoComponentsJsonArray(page.getVideoComponents()))
                .build();
        return jso;
    }
    private JsonArray makeTextComponentsJsonArray(List<TextComponent> tcs){
        System.out.println("MAKING TEXT COMPONENT ARRAY");
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(TextComponent tc : tcs){
            JsonObject jso = makeTextComponentJsonObject(tc);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makeTextComponentJsonObject(TextComponent tc){
        System.out.println("MAKING TEXT COMPONENT OBJECT");
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_TEXT_TYPE, tc.getType())
                .add(JSON_STYLE, tc.getStyle())
                .add(JSON_FONT, tc.getFont())
                .add(JSON_SIZE, tc.getSize())
                .add(JSON_TEXT, tc.getText())
                .build();
        return jso;
    }
    private JsonArray makeImageComponentsJsonArray(List<ImageComponent> ics){
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(ImageComponent ic : ics){
            JsonObject jso = makeImageComponentJsonObject(ic);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makeImageComponentJsonObject(ImageComponent ic){
        System.out.println("MAKING IMAGE COMPONENT OBJECT");
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_IMAGE_PATH, ic.getImagePath())
                .add(JSON_IMAGE_FILE_NAME,ic.getImageFileName())
                .add(JSON_IMAGE_CAPTION, ic.getCaption())
                .add(JSON_IMAGE_WIDTH, ic.getWidth())
                .add(JSON_IMAGE_HEIGHT, ic.getHeight())
                .add(JSON_IMAGE_FLOAT_OPTION, ic.getFloatOption())
                .add(JSON_IMAGE_CAPTION_FONT,ic.getCaptionFont())
                .add(JSON_IMAGE_CAPTION_STYLE, ic.getCaptionStyle())
                .add(JSON_IMAGE_CAPTION_SIZE, ic.getCaptionSize())
                .build();
        return jso;
    }
    private JsonArray makeVideoComponentsJsonArray(List<VideoComponent> vcs){
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(VideoComponent vc : vcs){
            JsonObject jso = makeVideoComponentJsonObject(vc);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makeVideoComponentJsonObject(VideoComponent vc){
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_VIDEO_PATH, vc.getVideoPath())
                .add(JSON_VIDEO_FILE_NAME, vc.getVideoFileName())
                .add(JSON_VIDEO_CAPTION, vc.getCaption())
                .add(JSON_VIDEO_WIDTH, vc.getWidth())
                .add(JSON_VIDEO_HEIGHT, vc.getHeight())
                .add(JSON_VIDEO_CAPTION_FONT, vc.getCaptionFont())
                .add(JSON_VIDEO_CAPTION_STYLE, vc.getCaptionStyle())
                .add(JSON_VIDEO_CAPTION_SIZE, vc.getCaptionSize())
                .build();
        return jso;
    }
    private JsonArray makeSlideShowComponentsJsonArray(List<SlideShowComponent> sscs){
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(SlideShowComponent ssc : sscs){
            JsonObject jso = makeSlideShowComponentJsonObject(ssc);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makeSlideShowComponentJsonObject(SlideShowComponent ssc){
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_SLIDESHOW_TITLE, ssc.getTitle())
                .add(JSON_SLIDES, makeSlidesJsonArray(ssc.getSlides()))
                .build();
        return jso;
    }
    private JsonArray makeSlidesJsonArray(List<Slide> slides){
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(Slide s : slides){
            JsonObject jso = makeSlideJsonObject(s);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    private JsonObject makeSlideJsonObject(Slide s){
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_SLIDE_IMAGE_FILE_NAME, s.getImageFileName())
                .add(JSON_SLIDE_IMAGE_PATH, s.getImagePath())
                .add(JSON_SLIDE_CAPTION, s.getCaption())
                .build();
        return jso;
    }
}
