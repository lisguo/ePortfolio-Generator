var arrIndex = 0;
var image_names = [];
var captions = [];var play;

$(function() {
var slides = [];

   $.getJSON('TITLE.json', function(data) {
	   alert(data);
       $.each(data.slides, function(i, f) {
          image_names.push("./img/" + f.image_file_name);
		  captions.push(f.caption);
		});

   });

});

function startSlideShow(){
	if (document.getElementById('playPause').children[0].className == "glyphicon glyphicon-play"){
		playSlideShow = setInterval(play,3000);
		document.getElementById('playPause').children[0].className = "glyphicon glyphicon-pause";
	}
	else if (document.getElementById('playPause').children[0].className == "glyphicon glyphicon-pause"){
		clearInterval(playSlideShow);
		document.getElementById('playPause').children[0].className = "glyphicon glyphicon-play";
	}
   }
function play(){
	changeSlide(0);
}function changeSlide(sw) {
	var pic;
	if(sw == 0) {
		if(arrIndex == image_names.length -1){
			arrIndex = 0;
		}
		else{
			arrIndex = arrIndex + 1;
		}
		pic = image_names[arrIndex];
		caption = captions[arrIndex];
	}else{
		if(arrIndex == 0){
			arrIndex = image_names.length-1;
		}
		else{
			arrIndex = arrIndex -1;
		}
		pic = image_names[arrIndex];
		caption = captions[arrIndex];
	}
	document.getElementById("slideImg").src = pic;
	document.getElementById("cap").innerHTML = caption;
}