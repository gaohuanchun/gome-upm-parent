/**
 * Created by hanpan on 2016/6/22.
 */
$(function(){

    //首页大事记
    $('.course_nr2 li').hover(function(){
        $(this).find('.shiji').slideDown(600);
    },function(){
        $(this).find('.shiji').slideUp(400);
    });

});