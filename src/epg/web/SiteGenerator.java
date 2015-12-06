/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epg.web;

import static epg.StartupConstants.PATH_PORTFOLIOS;
import static epg.StartupConstants.PATH_SITES;
import static epg.StartupConstants.PATH_TEMPLATES;
import static epg.file.SlideShowFileManager.SLASH;
import epg.model.ImageComponent;
import epg.model.Page;
import epg.model.PortfolioModel;
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
        }
    }
    public void makeHtml(Page page){
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
                        CopyOption[] options = new CopyOption[]{
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.COPY_ATTRIBUTES
                        };
                        System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                        Files.copy(orig,img,options);
                        
                        System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                    } catch (IOException ex) {
                        Logger.getLogger(SiteGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //MAKE SLIDESHOW DIRECTORY
                String ssPath = htmlPath + "slideshows" + SLASH;
                Path orig = Paths.get(PATH_SITES);
                File f = new File(ssPath);
                f.mkdirs();
                Path copyTo = Paths.get(ssPath);
                //OVERWRITE IF EXISTS
                CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
                };
                System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
                Files.copy(orig,copyTo,options);
                
                System.out.println("Copied \n" + orig.toString() + "\nto\n" + f.getCanonicalPath());
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
                    //OVERWRITE IF EXISTS
                    CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                    };
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
            "		<meta charset =\"UTF-8\">\n" +
            "		<meta name=\"viewport\" content=\"width=device-width, initial-sale=1.0\">\n" +
            "		<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"\"> </script>\n" +
            "		<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>\n" +
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
            "    </body>\n" +
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
    }
}
