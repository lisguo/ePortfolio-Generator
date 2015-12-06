/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var html = '';
var title;
var studentName;
var layouts = [];
var colors = [];
var fonts = [];
$(function(){
    var pages = [];
    html + = 
    $.getJSON("%title.json", function(data){
        title = data.title;
        studentName = data.student_name;
        $.each(data.pages, function(i,f){
            
        });
    });
});
