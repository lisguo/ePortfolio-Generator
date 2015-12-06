/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.web;

import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.PATH_SKELETONS;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.ImageComponent;
import epg.model.Page;
import epg.model.PortfolioModel;
import epg.model.TextComponent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.collections.ObservableList;

/**
 *
 * @author BunnyRailgun
 */
public class SiteGenerator {
    String PATH_PAGES;;
    String imagesPath;
    String slideShowPath;
    String videosPath;
    PortfolioModel portfolio;
    ObservableList<Page> pages;
    
    int indexCount = 2;
    int headerCount = 1;
    int paragraphCount = 1;
    int listCount = 1;
    int imageComponentCount = 1;
    int slideShowCount = 1;
    int videoCount = 1;
    
    public SiteGenerator() throws IOException{
    }
    public void start(PortfolioModel portfolio) throws IOException{
        PATH_PAGES = PATH_PORTFOLIOS + portfolio.getTitle() + SLASH;
        this.portfolio = portfolio;
        pages = portfolio.getPages();
        
        for(int i = 0; i < pages.size(); i++){
            Page currPage = pages.get(i);
            String htmlPath;
            String pagePath = PATH_PAGES + currPage.getName() + SLASH;
            System.out.println("Page Path : " + pagePath);
            if(i == 0){
                htmlPath = pagePath + "index.html";
            }
            else{
                htmlPath = pagePath + "index" + i + ".html";
            }
            String imagePath = pagePath + "images" + SLASH;
            String slideShowPath = pagePath + "slideshows" + SLASH;
            String videoPath = pagePath + "videos" + SLASH;
            
            makeHtml(currPage, htmlPath);
            makeCSS(currPage, pagePath + "layout.css");
        }
    }
    public void makeCSS(Page page, String cssPath) throws IOException{
        //MAKE LAYOUT
        File cssFile = new File(cssPath);
        cssFile.mkdirs();
        if(cssFile.exists()){
            cssFile.delete();
        }
        cssFile.createNewFile();
        FileWriter fw = new FileWriter(cssFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String cssCode = "";
        //MAKE LAYOUT
        if(page.getLayout() == 0){
            Path orig = Paths.get(PATH_SKELETONS + "layout1.css");
            Path copyTo = Paths.get(cssPath);
            Files.copy(orig,copyTo);
        }
        else if(page.getLayout() == 1){
            Path orig = Paths.get(PATH_SKELETONS + "layout2.css");
            Path copyTo = Paths.get(cssPath);
            Files.copy(orig,copyTo);
        }
        else if(page.getLayout() == 2){
            Path orig = Paths.get(PATH_SKELETONS + "layout3.css");
            Path copyTo = Paths.get(cssPath);
            Files.copy(orig,copyTo);
        }
        else if(page.getLayout() == 3){
            Path orig = Paths.get(PATH_SKELETONS + "layout3.css");
            Path copyTo = Paths.get(cssPath);
            Files.copy(orig,copyTo);
        }
        else{
            Path orig = Paths.get(PATH_SKELETONS + "layout4.css");
            Path copyTo = Paths.get(cssPath);
            Files.copy(orig,copyTo);
        }
        //ADD TEXT COMPONENTS
        for(TextComponent tc : page.getTextComponents()){
            
        }
    }
    public void makeHtml(Page page, String htmlPath) throws IOException{
        //Make image directory and add images
        for(ImageComponent ic : page.getImageComponents()){
            Path orig = Paths.get(ic.getImagePath() + SLASH + ic.getImageFileName());
            String imgPath = PATH_PAGES + page.getName() + SLASH + "images" + SLASH;
            File f = new File( imgPath + ic.getImageFileName());
            f.mkdirs();
            Path img = Paths.get(imgPath + ic.getImageFileName());
            //OVERWRITE IF EXISTS
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
            Files.copy(orig,img,options);
            System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
        }
        //MAKE HTML
        File htmlFile = new File(htmlPath);
        htmlFile.mkdirs();
        if(htmlFile.exists()){
            htmlFile.delete();
        }
        htmlFile.createNewFile();

        FileWriter fw = new FileWriter(htmlFile);
        BufferedWriter bw = new BufferedWriter(fw);
        String htmlCode = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <!--stylesheets-->\n" +
            "        <link rel =\"stylesheet\" href =\"layout.css\">\n" +
            "        <link rel =\"stylesheet\" href =\"style.css\">\n" +
            "        <link href='https://fonts.googleapis.com/css?family=" +page.getPageFont() + "' rel='stylesheet' type='text/css'>\n" +
            "        <title>\n" +
            "            " + page.getName() +"\n" +
            "        </title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <!--Navigation Bar -->\n" +
            "        <div id=\"navBar\">\n";
        //ADD PAGES
        for(int i = 0; i < portfolio.getPages().size(); i++){
            if(i == 0){
                htmlCode += "    <a class = \"nav\" href=\"index.html\">" + portfolio.getPages().get(i).getName()+"</a>\n";
            }else{
                htmlCode += "    <a class = \"nav\" href=\"index"+indexCount+".html\">" + portfolio.getPages().get(i).getName()+"</a>\n";
                indexCount++;
            }
        }
        htmlCode += "<div id=\"banner\">\n" +
        "            <a class=\"studentName\">"+ portfolio.getStudentName() + "</a>\n" +
        "        </div>\n" +
        "        <img id=\"bannerImg\" src = \" " +page.getBannerImgPath()+page.getBannerFileName()+"\" alt=\"banner\">\n" +
        "        <div id =\"textComponents\">\n" +
        "            <!--Content-->\n";
        addTextComponents(page, htmlCode);
        htmlCode += "    </body>\n" +
                    "</html>";
        System.out.println("WRITING HTML" + "\n" + htmlCode);
        bw.write(htmlCode);
        bw.close();
    }
    public void addTextComponents(Page page, String code){
        ObservableList<TextComponent> tcs = page.getTextComponents();
        for(TextComponent tc : tcs){
            if(tc.getType().equals("header")){
                code +="<h" + headerCount + ">\n  " + tc.getText() + "\n  </h" + headerCount + ">\n";
                headerCount++;
            }
            else if(tc.getType().equals("paragraph")){
                code +="<p id=\"paragraph" + paragraphCount + "\">\n  " + tc.getText() + "\n  /p>\n";
                paragraphCount++;
            }
            else if(tc.getType().equals("list")){
                
            }
        }
        code += "       </div>\n";
    }
}
