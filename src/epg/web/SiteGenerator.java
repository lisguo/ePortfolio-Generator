/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.web;

import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.PATH_SITES;
import static epg.StartupConstants.PATH_TEMPLATES;
import static epg.file.SlideShowFileManager.JSON_EXT;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.ImageComponent;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.SlideShowComponent;
import epg.model.VideoComponent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BunnyRailgun
 */
public class SiteGenerator {
    String templatePath = PATH_TEMPLATES;
    int currPage = 1;
    String pagePath;
    PortfolioModel portfolio;
    String htmlPath;
    File htmlFile;
    public SiteGenerator(PortfolioModel portfolio){
        this.portfolio = portfolio;
        pagePath = PATH_PORTFOLIOS + portfolio.getTitle() + SLASH;
    }
    public void generateSite(){
        for(Page p : portfolio.getPages()){
            makeHtml(p);
            makeJS(p);
            makeCSS(p);
        }
    }
    public void makeCSS(Page page){
        try {
            //MOVE TEMPLATE
            int layout = page.getLayout()+1;
            int style = page.getColor()+1;
            Path orig = Paths.get(PATH_TEMPLATES + "layout" + layout + ".css");
            Path to = Paths.get(pagePath + page.getName() + SLASH + "layout" + layout + ".css");
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(orig, to,options);
            orig = Paths.get(PATH_TEMPLATES + "style" + style + ".css");
            to = Paths.get(pagePath + page.getName() + SLASH + "style" + style + ".css");
            Files.copy(orig, to, options);
        } catch (IOException ex) {
            Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void makeJS(Page page){
        FileWriter fw = null;
        try {
            String jsPath = pagePath + page.getName() + SLASH + "javascript.js";
            File jsFile = new File(jsPath);
            jsFile.mkdirs();
            if(jsFile.exists()){
                jsFile.delete();
            }   jsFile.createNewFile();
            fw = new FileWriter(jsFile);
            BufferedWriter bw = new BufferedWriter(fw);
            String jsCode = "var pageNames = [];\n" +
                    "var pageName = '$pageName';\n" +
                    "var bannerFile;\n" +
                    "var layout;\n" +
                    "var color; \n" +
                    "var footer; \n" +
                    "var pageFont;\n" +
                    "var studentName;\n" +
                    "\n" +
                    "var text = [];\n" +
                    "var textType = [];\n" +
                    "\n" +
                    "var image = [];\n" +
                    "var imageCaptions = [];\n" +
                    "var slideshows = [];\n" +
                    "var videos = [];\n" +
                    "var videoCaptions = [];\n" +
                    "\n" +
                    "$(function extract(){\n" +
                    "	var pages = [];\n" +
                    "    $.getJSON('$title.json', function(data) {\n" +
                    "		studentName = data.student_name;\n" +
                    "        $.each(data.pages, function(i,f){\n" +
                    "            pageNames.push(f.page_name);\n" +
                    "            if(pageName == f.page_name){ //CURRENT PAGE\n" +
                    "                layout = f.layout + 1 ;\n" +
                    "                color = f.color + 1;\n" +
                    "                pageFont = f.page_font;\n" +
                    "                bannerFile = \"./img/\" + f.banner_file_name;\n" +
                    "				footer = f.footer;\n" +
                    "                $.each(f.text_components,function(j,g){\n" +
                    "                    text.push(g.text);\n" +
                    "                    textType.push(g.text_type);\n" +
                    "                });\n" +
                    "				$.each(f.image_components,function(j,g){\n" +
                    "					image.push(g.image_file_name);\n" +
                    "					imageCaptions.push(g.image_caption);\n" +
                    "				});\n" +
                    "				$.each(f.slideshow_components, function(j,g){\n" +
                    "					slideshows.push(g.slideshow_title);\n" +
                    "				});\n" +
                    "				$.each(f.video_components, function(j,g){\n" +
                    "					videos.push(g.video_file_name);\n" +
                    "					videoCaptions.push(g.video_caption);\n" +
                    "				});\n" +
                    "            }\n" +
                    "            \n" +
                    "        });\n" +
                    "    });\n" +
                    "	window.setTimeout(makeSite,500);\n" +
                    "});\n" +
                    "function makeSite(){\n" +
                    "    //ADD FONT FAMILY\n" +
                    "    $(\"head\").append(\"<link rel ='stylesheet' href ='layout\" + layout + \".css'>\");\n" +
                    "    $(\"head\").append(\"<link rel ='stylesheet' href ='style\" + color + \".css'>\");\n" +
                    "    $(\"head\").append(\"<link href='https://fonts.googleapis.com/css?family=\" + pageFont + \"' rel='stylesheet' type='text/css'>\")\n" +
                    "    \n" +
                    "    //CREATE TITLE \n" +
                    "    $(\"title\").append(pageName);\n" +
                    "    \n" +
                    "	//Create Banner with name\n" +
                    "	$(\"#banner\").append(\"<a class='studentName'>\"+studentName+\"</a>\")\n" +
                    "	$(\"#banner\").append(\"<img id='bannerImg' src = './img/\"+bannerFile+\">\");\n" +
                    "    //CREATE NAV BAR\n" +
                    "	for( i = 0; i < pageNames.length; i++){\n" +
                    "		if(i == 0){\n" +
                    "			$('#navBar').append(\"<a class='nav' href='index.html'>\" + pageNames[i]+\"</a>\");\n" +
                    "		}\n" +
                    "		else{\n" +
                    "			$(\"#navBar\").append(\"<a class='nav' href='index\" + i+1 + \".html'>\" + pageNames[i] + \"</a>\");\n" +
                    "		}\n" +
                    "	}\n" +
                                        "	\n	//FOOTER\n" +
                    "	$(\"footer\").append(footer);\n" +
                    "	\n" +
                    "	//CREATE TEXT COMPONENTS\n" +
                    "	for(i = 0; i < text.length; i++){\n" +
                    "		if(textType[i] == \"Header\"){\n" +
                    "			$(\"#textComponents\").append(\"<h>\"+text[i]+\"<h>\");\n" +
                    "		}\n" +
                    "		else if(textType[i] == \"Paragraph\"){\n" +
                    "			$(\"#textComponents\").append(\"<p>\"+text[i]+\"<p>\");\n" +
                    "		}\n" +
                    "	}\n" +
                    "	//IMAGE COMPONENTS\n" +
                    "	for(i =0;i<image.length;i++){\n" +
                    "		$(\"#imageComponents\").append(\"<figure id = '\"+image[i] +\"'></figure>\");\n" +
                    "		$(\"#\"+image[i]).append(\"<img id='\"+image[i]+\"' class ='imgComp' src = './img/\"+image[i]+\"' alt='\"+image[i]+\"'\");\n" +
                    "	}\n" +
                    "	//SLIDESHOW COMPONENTS\n" +
                    "	for(i =0; i<slideshows.length;i++){\n" +
                    "		$(\"#slideshowComponents\").append(\"<iframe id ='\"+slideshows[i]+\"' class ='slideshowComp' src ='./slideshows/\"+ slideshows[i] + \"/index.html'></iframe>\");\n" +
                    "	}\n" +
                    "	//VIDEO COMPONENTS\n" +
                    "	for(i =0;i<videos.length;i++){\n" +
                    "		$(\"#videoComponents\").append(\"<figure id = '\"+videos[i] +\"'></figure>\");\n" +
                    "		$(\"#\"+videos[i]).append(\"<video></video>\")\n" +
                    "		$(\"#video\").append(\"<source src='./videos/'\"+videos[i] + \"'type=video/mp4'>\")\n" +
                    "	}\n" +
                    "}";
            //REPLACE
            jsCode = jsCode.replace("$pageName", page.getName());
            jsCode = jsCode.replace("$title", portfolio.getTitle());
            bw.write(jsCode);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void makeHtml(Page page){
        try {
            //COPY JSON TO EACH SITE
            File json = new File(pagePath + portfolio.getTitle() + JSON_EXT);
            File jsonCopyToFile = new File(pagePath + page.getName() + SLASH);
            jsonCopyToFile.mkdirs();
            Path jsonOrig = Paths.get(pagePath + portfolio.getTitle() + JSON_EXT);
            Path jsonCopyTo = Paths.get(pagePath + page.getName() + SLASH + portfolio.getTitle() + JSON_EXT);
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(jsonOrig,jsonCopyTo,options);
            FileWriter fw = null;
            try {
                try {
                    htmlPath = pagePath + page.getName() + SLASH;
                    //MAKE IMAGE DIRECTORY AND ADD IMAGES
                    String imgPath = htmlPath + "img" + SLASH;
                    System.out.println("IMAGE PATH :" +imgPath);
                    for(ImageComponent ic : page.getImageComponents()){
                        try {
                            Path orig = Paths.get(ic.getImagePath() + ic.getImageFileName());
                            File f = new File(imgPath + ic.getImageFileName());
                            f.mkdirs();
                            Path img = Paths.get(imgPath + ic.getImageFileName());
                            //OVERWRITE IF EXISTS
                            
                            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                            Files.copy(orig,img,options);
                            
                            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                        } catch (IOException ex) {
                            Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    //PUT BANNER TOO
                    if(page.getHasBannerImage()){
                        System.out.print("PAGE HAS BANNER IMAGE");
                        Path orig = Paths.get(page.getBannerImgPath() + page.getBannerFileName());
                        Path img = Paths.get(imgPath + page.getBannerFileName());
                        
                        Files.copy(orig,img,options);
                    }
                    //MAKE SLIDESHOW DIRECTORY
                    for(SlideShowComponent ssc : page.getSlideShowComponents()){
                        String ssPath = htmlPath + "slideshows" + SLASH + ssc.getTitle() + JSON_EXT;
                        File origF = new File(PATH_SITES +  ssc.getTitle());
                        File f = new File(ssPath);
                        f.mkdirs();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //MAKE VIDEO DIRECTORY
                String videoPath = htmlPath + "videos" + SLASH;
                for(VideoComponent vc : page.getVideoComponents()){
                    try {
                        Path orig = Paths.get(vc.getVideoPath() + vc.getVideoFileName());
                        File f = new File(videoPath + vc.getVideoFileName());
                        f.mkdirs();
                        Path vid = Paths.get(videoPath + vc.getVideoFileName());
                        
                        System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                        Files.copy(orig,vid,options);
                        
                        System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                    } catch (IOException ex) {
                        Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //MAKE HTML
                if(currPage == 1){
                    htmlFile = new File(htmlPath + "index.html");
                    currPage++;
                }
                else{
                    htmlFile = new File(htmlPath + "index" + currPage + ".html");
                    currPage++;
                }
                htmlFile.mkdirs();
                if(htmlFile.exists()){
                    htmlFile.delete();
                }
                try {
                    htmlFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
                fw = new FileWriter(htmlFile);
                BufferedWriter bw = new BufferedWriter(fw);
                String htmlCode = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "	\n" +
                        "        <title>\n" +
                        "			\n" +
                        "        </title>\n" +
                        "		<meta charset =\"UTF-8\"></meta>\n" +
                        "		<meta name=\"viewport\" content=\"width=device-width, initial-sale=1.0\"></meta>\n" +
                        "		<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"> </script>\n" +
                        "		<script src = \"javascript.js\"></script>\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <!--Navigation Bar -->\n" +
                        "        <ul id = \"navBar\">\n" +
                        "		</ul>\n" +
                        "        <div id=\"banner\">\n" +
                        "        </div>\n" +
                        "        <div id =\"textComponents\">\n" +
                        "        </div>\n" +
                        "        <div id=\"imageComponents\">\n" +
                        "        </div>\n" +
                        "        <div id=\"slideshowComponents\" >\n" +
                        "        </div>\n" +
                        "        <div id=\"videoComponents\">\n" +
                        "        </div>\n" +
                        "    </body>\n	"
                        + "<footer>\n" +
                        "	</footer>\n" +
                        "</html>";
                System.out.println(htmlCode);
                bw.write(htmlCode);
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
                Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
