/**
 * Created by hanpan on 2016/6/24.
 */
$(function(){

 $(".list_table table td span.span_ip").mouseenter(function(){
     var left=$(this).offset().left+ 10+"px" ;//left
     var top=$(this).offset().top+6+"px";
     $(this).siblings(".ip_container").show().css({"position":"absolute","left":left,"top":top});
     $(this).parents('tr').siblings().find('.ip_container').hide();
 });

   $(".list_table table td .ip_container p").each(function(){
       $(this).click(function(){
           window.location.href="../pages/baseTableForIP.html";
       });
   })


});