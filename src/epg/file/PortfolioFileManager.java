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
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.TextComponent;
import java.math.BigDecimal;


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
    //TEXT COMPONENTS
    public static String JSON_TEXT_COMPONENTS = "text_components";
    public static String JSON_TEXT_TYPE = "text_type";
    public static String JSON_STYLE = "style";
    public static String JSON_FONT = "font";
    public static String JSON_SIZE = "size";
            
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
        String jsonFilePath = PATH_PORTFOLIOS + SLASH + portfolioTitle + JSON_EXT;
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
            boolean hasBannerImg = !pageJso.getString(JSON_BANNER_FILE_NAME).equals("");
            Page page = new Page(pageJso.getString(JSON_PAGE_NAME),
                    pageJso.getInt(JSON_LAYOUT),
                    pageJso.getInt(JSON_COLOR),
                    pageJso.getString(JSON_PAGE_FONT),
                    hasBannerImg);
            portfolioToLoad.addPage(page);
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
                .add(JSON_TEXT_COMPONENTS, makeTextComponentsJsonArray(page.getTextComponents()))
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
}
