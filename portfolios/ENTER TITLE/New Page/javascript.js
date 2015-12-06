var pageNames = [];
var pageName = 'New Page';
var bannerFile;
var layout;
var color; 
var footer; 
var pageFont;
var studentName;

var text = [];
var textType = [];

var image = [];
var imageCaptions = [];
var slideshows = [];
var videos = [];
var videoCaptions = [];
$(function(){
	var pages = [];
    $.getJSON('ENTER TITLE.json', function(data) {
		studentName = data.student_name;
        $.each(data.pages, function(i,f){
            pageNames.push(f.page_name);
            if(pageName == f.page_name){ //CURRENT PAGE
                layout = f.layout + 1 ;
                color = f.color + 1;
                pageFont = f.page_font;
                bannerFile = "./img/" + f.banner_file_name;
				footer = f.footer;
                $.each(f.text_components,function(j,g){
                    text.push(g.text);
                    textType.push(g.text_type);
                });
				$.each(f.image_components,function(j,g){
					image.push(g.image_file_name);
					imageCaptions.push(g.image_caption);
				});
				$.each(f.slideshow_components, function(j,g){
					slideshows.push(g.slideshow_title);
				});
				$.each(f.video_components, function(j,g){
					videos.push(g.video_file_name);
					videoCaptions.push(g.video_caption);
				});
            }
            
        });
    });
	
    //ADD FONT FAMILY
	alert();
    $("head").append("<link rel ='stylesheet' href ='layout" + layout + ".css'>");
    $("head").append("<link rel ='stylesheet' href ='style" + color + ".css'>");
    $("head").append("<link href='https://fonts.googleapis.com/css?family=" + pageFont + "' rel='stylesheet' type='text/css'>")
    
    //CREATE TITLE 
    $("title").append(pageName);
    
	//Create Banner with name
	$("#banner").append("<a class='studentName'>"+studentName+"</a>")
	$("#banner").append("<img id='bannerImg' src = './img/"+bannerFile+">");
    //CREATE NAV BAR
	for( i = 0; i < pageNames.length; i++){
		if(i == 0){
			alert();
			$('#navBar').append("<li><a class='nav' href='index.html'>" + pageNames[i]+"</a></li>");
		}
		else{
			alert();
			$("#navBar").append("<li><a class='nav' href='index" + i+1 + ".html'>" + pageNames[i] + "</a></li>");
		}
	}
	
	//CREATE TEXT COMPONENTS
	for(i = 0; i < text.length; i++){
		if(textType[i] == "Header"){
			$("#textComponents").append("<h>"+text[i]+"<h>");
		}
		else if(textType[i] == "Paragraph"){
			$("#textComponents").append("<p>"+text[i]+"<p>");
		}
	}
	//IMAGE COMPONENTS
	for(i =0;i<image.length;i++){
		$("#imageComponents").append("<figure id = '"+image[i] +"'></figure>");
		$("#"+image[i]).append("<img id='"+image[i]+"' class ='imgComp' src = './img/"+image[i]+"' alt='"+image[i]+"'");
	}
	//SLIDESHOW COMPONENTS
	for(i =0; i<slideshows.length;i++){
		$("#slideshowComponents").append("<iframe id ='"+slideshows[i]+"' class ='slideshowComp' src ='./slideshows/ "+ slideshows[i] + "/index.html'></iframe>");
	}
	//VIDEO COMPONENTS
	for(i =0;i<videos.length;i++){
		$("#videoComponents").append("<figure id = '"+videos[i] +"'></figure>");
		$("#"+videos[i]).append("<video></video>")
		$("#video").append("<source src='./videos/'"+videos[i] + "'type=video/mp4'>")
	}
});