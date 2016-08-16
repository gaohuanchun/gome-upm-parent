/**
 * Created by hanpan on 2015/10/27.
 * userList  用户列表模块
 */
$(function(){
//点击高级搜索，出现高级搜索选项
    $("#hight_click").click(hight_search_show)
    
	$(".pageNumber").unbind("click");
    $(".pageNumber").bind("click", function(){
    	var pageNo = $(this).attr("pageNo");
    	queryListForAjax(pageNo);
    });
});
//高级搜索选择显示
flag=true;
function hight_search_show(){
if(flag){
    $("#hight_click em").text("收起");
    $(".search_hight").show();
    flag=false;
}else{
    $("#hight_click em").text("高级搜索");
    $(".search_hight").hide();
    flag=true;
}


}

//异步搜索请求
var queryListForAjax = function(pageNo){
	var content = {};
	content.skuNo=$("#skuNo").val();
	content.type=$("#typeCode").val();
	content.result=$("#dResult").val();
	content.beginTime=$("#beginTime").val();
	content.endTime=$("#endTime").val();
	content.areaCode=$("#areaCode").val();
	content.node=$("#currentNode").val();
	content.action=$("#currentAction").val();
	content.status=$("#dealStatus").val();
	//content.pageNo=$("#pageNo").val();
	content.pageNo=pageNo;
	$.ajax({
		url:contextPath+'/url/getTable',
		type:'POST',
		dataType : 'html',	
		data:{'content':JSON.stringify(content)},
		success:function(data){
			$(".table-area").empty();
			$(".table-area").append(data);
		},
		error:function(){
			alert("操作失败");
			
		}
		
		
	});
}