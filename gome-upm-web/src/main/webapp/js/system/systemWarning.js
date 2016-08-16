$(function(){
	
	$("#search_id").click(function(){
	    allUrl.controller.search();
	});
	
	allUrl.service.init();

});

var allUrl = {
  service : {
	   init : function(){
		   allUrl.controller.page();
	   }
	   
	   
   },	
   controller : {
	   
	   page : function(){
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
	   },
	   search : function(){
		    	var startTime = $("#startTime").val();
		    	var endTime = $("#endTime").val();
		    	var ssz = $("#parentSelect option:selected").text();
		    	var xmm = $("#select option:selected").text();
		    	var change = 0;
		    	if(startTime==''&&endTime!=''){
					alert("请输入开始时间!");
					$("#startTime").focus();
					return ;
				}
				if(startTime!=''&&endTime==''){
					alert("请输入结束时间!");
					$("#endTime").focus();
					return ;
				}
				if(startTime!=''&&endTime!=''){
					var start=new Date(startTime.replace("-", "/").replace("-", "/"));   
				    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
				    if(end<start){  
				    	alert("开始时间不能晚于结束时间!");
				    	$("#startTime").focus();
						return ; 
				    }  
					
				}
		    	$.ajax({
						url:contextPath+'/system/getSystemTable',
						type:'POST',
						dataType : 'html',	
						data:{"startTime":startTime,"endTime":endTime,"ssz":ssz,"xmm":xmm,"change":change},
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