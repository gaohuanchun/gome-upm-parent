/**
 * 左侧菜单样式
 *
 * */
$(function(){
    //改变左侧菜单栏
    $(".myApp .treeview").on('click',function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    $('#myApp').on('click',function(){
    	location.href = "appManager.html";
    	return false;
    });
});