package epg.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.ObservableList;
import static epg.StartupConstants.PATH_SITES;
import epg.model.Slide;
import epg.model.SlideShowModel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.net.URISyntaxException;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import static epg.StartupConstants.PATH_SLIDE_SHOWS;
/**
 * 
 * @author Lisa Guo (BunnyRailgun)
 */
public class SlideShowViewer extends Stage{
    ObservableList<Slide> slides;
    Slide currSlide;
    File htmlFile;
    String htmlPath;
    String cssPath;
    String jsPath;
    String imgPath;
    String currImg;
    String currCap;
    String htmlCode;
    String jsCode;
    String imgPathNonNormal;
    String cssCode;
    
    WebView viewer;
    
    //HTML CONTENT
    String title;
    static final String imgWidth = "650";
    static final String imgHeight = "500";
    
    //CSS PREFERENCES
    
    
    public SlideShowViewer(SlideShowModel m) throws IOException{
        title = m.getTitle();
        slides = m.getSlides();
        htmlPath = PATH_SITES + title + "/index.html";
        cssPath = PATH_SITES + title + "/css/slideshow_style.css";
        jsPath = PATH_SITES + title + "/js/Slideshow.js";
        imgPathNonNormal = "./img/";
        imgPath = PATH_SITES + title + "/img";
        //Normalize imgPath
        File f = new File(imgPath);
        imgPath = f.getCanonicalPath() + "\\";
        
        //Normalize htmlPath
        f = new File(htmlPath);
        htmlPath = f.getCanonicalPath();
        htmlFile = new File(htmlPath);
        
        //Normalize jsPath
        f = new File(jsPath);
        jsPath = f.getCanonicalPath();
        
        //Normalize cssPath
        f = new File(cssPath);
        cssPath = f.getCanonicalPath();
        
        viewer = new WebView();
    }
    public void makeCSS() throws IOException{
        File cssFile = new File(cssPath);
        System.out.println("CSS File: " + cssFile.getCanonicalPath());
        cssFile.mkdirs();
        if(cssFile.exists()){
            cssFile.delete();
        }
        cssFile.createNewFile();
        FileWriter fw = new FileWriter(cssFile);
        BufferedWriter bw = new BufferedWriter(fw);
        cssCode = "h1{\n" +
        "	font-family: Rockwell, 'Courier Bold', Courier, Georgia, Times, 'Times New Roman', serif;\n" +
        "	font-size: 55px;\n" +
        "	line-height: 60px;\n" +
        "	background-height: 80px;\n" +
        "	background-color: #CF2222;\n" +
        "	color: #4D0000;\n" +
        "}\n" +
        "body {\n" +
        "	background-color: #A50C0C;\n" +
        "}\n" +
        "p{\n" +
        "	font-family: Rockwell, 'Courier Bold', Courier, Georgia, Times, 'Times New Roman', serif;\n" +
        "	font-size: 27px;\n" +
        "	line-height: 40px;\n" +
        "	background-color: #E65555;\n" +
        "	text-align: center;\n" +
        "	color: #4D0000;\n" +
        "}\n" +
        "img.center{\n" +
        "	display: block;\n" +
        "	margin-left: auto;\n" +
        "	margin-right: auto;\n" +
        "}";
        bw.write(cssCode);
        bw.close();
    }
    public void makeJS() throws IOException{
        //MAKE JS
        File jsFile = new File(jsPath);
        System.out.println("JS File: " + jsFile.getCanonicalPath());
        jsFile.mkdirs();
        if(jsFile.exists()){ //If there is already a file
            jsFile.delete();
        }
        jsFile.createNewFile();
        //USE BUFFEREDWRITER TO MAKE JS
        FileWriter fw = new FileWriter(jsFile);
        BufferedWriter bw = new BufferedWriter(fw);
        jsCode = "var arrIndex = 0;\n" +
        "var image_names = [];\n" +
        "var captions = [];"
        + "var play;\n" +
        "\n" +
        "$(function() {\n" +
        "var slides = [];\n" +
        "\n" +
        "   $.getJSON('" + title + ".json', function(data) {\n" +
        "       $.each(data.slides, function(i, f) {\n" +
        "          image_names.push(\"./img/\" + f.image_file_name);\n" +
        "		  captions.push(f.caption);\n" +
        "		});\n" +
        "\n" +
        "   });\n" +
        "\n" +
        "});\n" +
        "\n" +
        "function startSlideShow(){\n" +
        "	if (document.getElementById('playPause').children[0].className == \"glyphicon glyphicon-play\"){\n" +
        "		playSlideShow = setInterval(play,3000);\n" +
        "		document.getElementById('playPause').children[0].className = \"glyphicon glyphicon-pause\";\n" +
        "	}\n" +
        "	else if (document.getElementById('playPause').children[0].className == \"glyphicon glyphicon-pause\"){\n" +
        "		clearInterval(playSlideShow);\n" +
        "		document.getElementById('playPause').children[0].className = \"glyphicon glyphicon-play\";\n" +
        "	}\n" +
        "   }\n" +
        "function play(){\n" +
        "	changeSlide(0);\n" +
        "}" +
        "function changeSlide(sw) {\n" +
        "	var pic;\n" +
        "	if(sw == 0) {\n" +
        "		if(arrIndex == image_names.length -1){\n" +
        "			arrIndex = 0;\n" +
        "		}\n" +
        "		else{\n" +
        "			arrIndex = arrIndex + 1;\n" +
        "		}\n" +
        "		pic = image_names[arrIndex];\n" +
        "		caption = captions[arrIndex];\n" +
        "	}else{\n" +
        "		if(arrIndex == 0){\n" +
        "			arrIndex = image_names.length-1;\n" +
        "		}\n" +
        "		else{\n" +
        "			arrIndex = arrIndex -1;\n" +
        "		}\n" +
        "		pic = image_names[arrIndex];\n" +
        "		caption = captions[arrIndex];\n" +
        "	}\n" +
        "	document.getElementById(\"slideImg\").src = pic;\n" +
        "	document.getElementById(\"cap\").innerHTML = caption;\n" +
        "}";
        bw.write(jsCode);
        bw.close();
    }
    public void makeHtml() throws IOException, URISyntaxException{
        //COPY JSON FILE TO SITES
        Path jsonFile = Paths.get(PATH_SLIDE_SHOWS + title + ".json");
        Path copyTo = Paths.get(PATH_SITES + title + "/" + title + ".json");
        System.out.println("Copied " + jsonFile + "\nto\n" + copyTo);
        //OVERWRITE IF EXISTS
        CopyOption[] jsonOptions = new CopyOption[]{
        StandardCopyOption.REPLACE_EXISTING,
        StandardCopyOption.COPY_ATTRIBUTES
        };
        Files.copy(jsonFile,copyTo,jsonOptions);
        //MAKE IMAGE DIRECTORY AND ADD IMAGES
        for(Slide s : slides){
            Path orig = Paths.get(s.getImagePath() + "\\" + s.getImageFileName()); //Original path
            //Make directory
            File f = new File(imgPath + s.getImageFileName());
            f.mkdirs();
            Path img = Paths.get(imgPath + s.getImageFileName());
            //OVERWRITE IF EXISTS
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
            Files.copy(orig,img,options);
            
            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
        }
        //SET CURRENT IMAGE
        currSlide = slides.get(0);
        currImg = imgPath + currSlide.getImageFileName();
        imgPathNonNormal = imgPathNonNormal + currSlide.getImageFileName();
        //SET CURRENT CAPTION
        currCap = currSlide.getCaption();
        //MAKE HTML
        System.out.println(htmlFile.getCanonicalPath());
        htmlFile.mkdirs();
        if(htmlFile.exists()){ //If there is already a html file
            htmlFile.delete();
        }
        htmlFile.createNewFile();
        //USE BUFFEREDWRITER TO MAKE A TEMPLATE HTML
        FileWriter fw = new FileWriter(htmlFile);
        BufferedWriter bw = new BufferedWriter(fw);
        htmlCode = "<!DOCTYPE html>\n" +
        "<html>\n" +
        "<head>\n" +
        "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">\n" +
        "<link rel=\"stylesheet\" href=\"./css/slideshow_style.css\">\n" +
        "<title>Slide Show</title>\n" +
        "<meta charset =\"UTF-8\">\n" +
        "<meta name=\"viewport\" content=\"width=device-width, initial-sale=1.0\"></head>\n" +
        "<body>\n" +
        "<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js\"> </script>\n" +
        "<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>\n"
        + "<script src = \"./js/Slideshow.js\"></script>\n" +
        "<h1>$title</h1>\n" +
        "<img id = \"slideImg\" src = \"$currImg\" alt=\"img\" class=\"center\" height=\"$height\">\n" +
        "<p id = \"cap\">$caption</p>\n" +
        "<p>\n" +
        "<button type = \"button\" onclick = \"changeSlide(1)\">\n" +
        "<span class=\"glyphicon glyphicon-backward\"></span>\n" +
        "</button>\n" +
        "<button type = \"button\" id = \"playPause\" onclick = \"startSlideShow()\">\n" +
        "<span class=\"glyphicon glyphicon-play\"></span>\n" +
        " </button>\n" +
        "<button type = \"button\" onclick = \"changeSlide(0)\">\n" +
        "<span class=\"glyphicon glyphicon-forward\"></span>\n" +
        "</button>\n" +
        "</p>\n" +
        "</body>\n" +
        "</html>";
        //Replace placeholders
        htmlCode = htmlCode.replace("$title",title);
        htmlCode = htmlCode.replace("$currImg", imgPathNonNormal);
        htmlCode = htmlCode.replace("$caption", currCap);
        htmlCode = htmlCode.replace("$height", imgHeight);
        bw.write(htmlCode);
        bw.close();
    }
    
    public void showSlideShow(){
        WebEngine engine = viewer.getEngine();
        String loadStr = "file:///" + htmlPath;
        engine.load(loadStr);
        //System.out.println("Loading " + loadStr);
        setTitle("Slide Show");
        GridPane gp = new GridPane();
        FlowPane fp = new FlowPane();
        viewer.setMinSize(1000, 700);
        viewer.setManaged(true);
        fp.getChildren().add(viewer);
        
        gp.add(fp, 0,0);
        Scene scene = new Scene(gp,1000,700);
        setScene(scene);
        show();
    }
}