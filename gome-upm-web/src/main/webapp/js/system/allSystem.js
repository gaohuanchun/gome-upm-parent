$(function(){
	//删除url
	
	$("#btn_search").click(function(){
	    allSystem.controller.search();
	});
	
	
	
	allSystem.service.init();

});

function quickSearch(evt) {
	evt = (evt) ? evt : ((window.event) ? window.event : "") // 兼容IE和Firefox获得keyBoardEvent对象
	var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
	if (key == 13) { // 判断是否是回车事件。
		// 根据需要执行某种操作。
		// 根据需要执行某种操作。
		allSystem.controller.search();
	}
}

var allSystem = {
  service : {
	   init : function(){
		   allSystem.controller.page();
	   }
	   
	   
   },	
   controller : {
	   
	   page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var text = $("#search_name").val();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
		    	//queryListForPageDetail(pageNo);
				   $.ajax({
						url:contextPath+'/system/getSystemTable',
						type:'POST',
						dataType : 'html',	
						data:{"pageNo":pageNo,"pageSize":pageSize,"text":text},
						success:function(data){
							console.info(data);
							var bool = data.indexOf("sessionTimeOut");
							if(bool < 0){
								$(".list_table").empty();
								$(".list_table").append(data);
							}else{
								window.location.href=contextPath+"/home";
							}
							
						},
						error:function(){
							alert("操作失败");
							
						}
						
						
					});
		    });
	   },
	   search : function(){
			   	var text = $("#search_name").val();
		    	//alert(text);
		    	$.ajax({
						url:contextPath+'/system/getSystemTable',
						type:'POST',
						dataType : 'html',	
						data:{"text":text},
						success:function(data){
							console.info(data);
							var bool = data.indexOf("sessionTimeOut");
							if(bool < 0){
								$(".list_table").empty();
								$(".list_table").append(data);
							}else{
								window.location.href=contextPath+"/home";
							}
							
						},
						error:function(){
							alert("操作失败");
							
						}
						
						
					});
		   
	   }
	   
	   
   }
};