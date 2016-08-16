$(function(){
	//删除url
	$("#btn-del").click(function(){
	    allUrl.controller.delUrl();
	});
	$("#search_id").click(function(){
	    allUrl.controller.search();
	});
	
	$("#defaultReturnCode").on("click",function(){
		allUrl.service.defaultReturnCode();
	});
	
	$("#customReturnCode").on("click",function(){
		allUrl.service.customReturnCode();
	});
	
	allUrl.service.init();

});

var allUrl = {
  service : {
	   init : function(){
		   allUrl.service.checkAndNoCheck();
		   allUrl.controller.useOrNoUse();
		   allUrl.service.alarmCheckedVari();
		   allUrl.controller.page();
		   allUrl.controller.editUrl();
		   allUrl.controller.editUrlSubmit();
	   },
	   getCheckedUrl : function(){
		   var ids = "";
		   $("input[name='checkbox']:checkbox").each(function(){
			   if($(this).is(":checked")){
				   ids += $(this).val()+",";
			   }
		   });
		  return ids.substring(0, ids.length-1);
	   },
	  defaultReturnCode : function(){
			  $("#urlBackCode").val("200");
			  $('#urlBackCode').attr("disabled","disabled");
		  },
	  customReturnCode : function(){
			  $("#urlBackCode").val("200"); 
			  $('#urlBackCode').attr("disabled",false);
		  },
	   checkAndNoCheck : function(){
		   $("#checkAll").click(function(){
			   //$("input[name='checkbox']:checkbox").prop("checked",this.checked);
			   $("input[name='checkbox']:checkbox").prop("checked",$(this).is(":checked"));
           });
		   
		   $("input[name='checkbox']:checkbox").click(function(){
			   var allBox =  $("input[name='checkbox']:checkbox");
			   $("#checkAll").prop("checked", allBox.length == allBox.filter(":checked").length ? true : false);
		   });
	   },
	   getCheckedAlarm : function(){
		   var ids = "";
		   $("input[name='alarmWay']:checkbox").each(function(){
			   if($(this).is(":checked")){
				   ids += $(this).val()+",";
			   }
		   });
		  return ids.substring(0, ids.length-1);
	   },
	   alarmCheckedVari : function(){
		   $(".list_table").on("click","#inlineCheckbox1",function(){
			   $("#inlineCheckbox2").prop("checked",false);
			});
		   
		   $(".list_table").on("click","#inlineCheckbox2",function(){
			   $("#inlineCheckbox1").prop("checked",false);
			});

	   }
   },	
   controller : {
	   createStep1 : function(){
			$.ajax({
				url:contextPath+'/url/create/step1',
				type:'POST',
				dataType : 'html',	
				data:{},
				success:function(data){
					console.info(data);
					$(".content-wrapper").empty();
					$(".content-wrapper").append(data);
				},
				error:function(){
					alert("操作失败");
					
				}
				
				
			});
	   },
	   delUrl : function(){
		   confirm("确定要删除吗，删除后不可恢复");
           var ids = allUrl.service.getCheckedUrl();
           if(ids == ""){
        	   return false;
           }
			$.ajax({
				url:contextPath+'/url/del',
				type:'POST',
				dataType : 'json',	
				data:{"id":ids},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						alert("删除成功");
						window.location.href=contextPath+"/url/get";
					}
				},
				error:function(){
					alert("删除失败");
					
				}
				
				
			});
	   },
	   useOrNoUse : function(){
		   
		   $(".list_table").on("click",".statusChange",function(){
			   var urlId = $(this).attr("urlId");
			   var status = $(this).attr("urlStatus");
			   $.ajax({
					url:contextPath+'/url/changeStatus',
					type:'POST',
					dataType : 'json',	
					data:{"id":urlId,"status":status},
					success:function(data){
						console.info(data);
						if(data.code == 1){
							alert("操作成功");
							window.location.href=contextPath+"/url/get";
						}
					},
					error:function(){
						alert("操作失败");
						
					}
					
					
				});
		   });
	   },
	   page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var startTime = $("#hiddenStartTime").val();
		    	var endTime = $("#hiddenEndTime").val();
		    	var survival = $("#hiddenSurvival").val();
		    	var urlAddress = $("#hiddenUrlAddress").val();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
		    	//queryListForPageDetail(pageNo);
				   $.ajax({
						url:contextPath+'/url/getUrlTable',
						type:'POST',
						dataType : 'html',	
						data:{"startTime":startTime,"endTime":endTime,"survival":survival,"urlAddress":urlAddress,"pageNo":pageNo,"isWarning":1},
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
		    	var urlAddress = $("#urlAddress").val();
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
						url:contextPath+'/url/getUrlTable',
						type:'POST',
						dataType : 'html',	
						data:{"startTime":startTime,"endTime":endTime,"urlAddress":urlAddress,"survival":0,"isWarning":0},
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
		   
	   },
	   editUrl : function(){
		   $(".list_table").on("click",".editUrl",function(){
			   var urlId = $(this).attr("urlId");
			   $.ajax({
					url:contextPath+'/url/getById',
					type:'POST',
					dataType : 'json',	
					data:{"id":urlId},
					success:function(data){
						if(data.code == 1){
							console.info(data);
							$("#hiddenUrlId").val(data.attach.id);
							$("#address").val(data.attach.url).attr("disabled",true);
							$("#urlFrc").val(data.attach.frequency);
							$("#timeout").val(data.attach.timeout);
							$("#time_number").val(data.attach.overtimes);
							$("#warn_time").val(data.attach.interval);
							$("#inputContent").val(data.attach.matchContent);
							$("input[name='method'][value="+data.attach.requestMethod+"]").attr("checked",true);
							$("input[name='isContainsContent'][value="+data.attach.matchMethod+"]").attr("checked",true);
							$("#urlBackCode").val(data.attach.returnCode);
							var urlBackCodeValue = 1;
							if(data.attach.returnCode != "200"){
								urlBackCodeValue = 2;
								$("#urlBackCode").attr("disabled", false);
							}
							$("input[name='urlBackCode'][value="+urlBackCodeValue+"]").attr("checked",true);
							$("input[name='alarmWay']:checkbox[value="+data.attach.alarmMethod+"]").attr("checked",true);
							
						}
						
					},
					error:function(){
						//alert("操作失败");
						
					}
					
					
				});
		   });
		   
		   
	   },
	   editUrlSubmit : function(){
		   $(".list_table").on("click","#modal_btn_submit",function(){
			    var content = {};
				content.id = $("#hiddenUrlId").val();;
				content.urlAddress = $("#address").val();
				content.accFre = $("#urlFrc").val();
				content.accTimeOut = $("#timeout").val();
				content.timeOutNum = $("#time_number").val();
				content.alarmInter = $("#warn_time").val();
				content.method = $("input[name='method']:checked").val();
				content.resContent = $("#inputContent").val();
				content.isContainsCon = $("input[name='isContainsContent']:checked").val();
				content.isDefaultCode = $("input[name='isDefaultCode']:checked").val();
				content.returnCode = $("#urlBackCode").val();
				if($("#inlineCheckbox1").is(":checked")){
					var checkedLength = $("input[name='alarmWay']:checked").length; 
					if(checkedLength > 1){
						alert("报警方式中不报警不能和其他同时选中");
						return false;
					}
				}
				content.alarmWay = allUrl.service.getCheckedAlarm();
				console.info(content.alarmWay);
//				if(content.key == ""){
//					alert("请返回第一步填写必选项");
//					return false;
//				}
				
				if(content.urlAddress == "" || content.accTimeOut == "" || content.timeOutNum == "" || content.alarmInter == ""){
					alert("请输入必填项");
					return false;
				}
				$.ajax({
					url:contextPath+'/url/saveUpdate',
					type:'POST',
					dataType : 'json',	
					data:{'content':JSON.stringify(content)},
					success:function(data){
						if(data.code == 1){
							//console.info(data.attach);
							window.location.href =contextPath+"/url/get";
						}
//						$(".content-wrapper").empty();
//						$(".content-wrapper").append(data);
					},
					error:function(){
						alert("操作失败");
						
					}
					
					
				});
		   });
		   
		   
	   }
   }
};