$(function(){
	
	$("#info_search_span").click(function(){
	    allUrl.controller.search();
	});
	
	allUrl.service.init();

});

var allUrl = {
  service : {
	   init : function(){
		   //allUrl.controller.page();
		   //allUrl.controller.search();
	   },
	   
	   
   },	
   controller : {
	   
	   /*page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var startTime = $("#hiddenStartTime").val();
		    	var endTime = $("#hiddenEndTime").val();
		    	var ssz = $("#parentSelect option:selected").text();
		    	var xmm = $("#select option:selected").text();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
		    	var change = 0;
		    	//queryListForPageDetail(pageNo);
				   $.ajax({
						url:contextPath+'/system/getSystemTable',
						type:'POST',
						dataType : 'html',	
						data:{"startTime":startTime,"endTime":endTime,"ssz":ssz,"xmm":xmm,"pageNo":pageNo,"pageSize":pageSize,"change":change},
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
	   },*/
	   search : function(){
			   var ssz = $("#parentSelect option:selected").text();
			   var xmm = $("#select option:selected").text();
		    	
		    	if(ssz=='请选择'&&xmm=='请选择'){
					alert("请指定要查询的部门和应用!");
					return ;
				}
				
				if(xmm=='请选择'){
					alert("请指定要查询的应用名!");
					return ;
				}
		    	$.ajax({
		    		
						url:contextPath+'/system/getSystemApp',
						type:'POST',
						dataType : 'html',	
						data:{"ssz":ssz,"xmm":xmm},
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