$(function(){
	// 查询
	$("#btn_search").click(function(){
	    allApp.controller.search();
	});
	
	//删除url
	$("#btn-del").click(function(){
		allApp.controller.delUrl();
	});
	
	// 新增保存
	$("#btn-save").click(function(){
		allApp.controller.save();
	});
	allApp.service.init();
});

var allApp = {
	service : {
	   init : function(){
		   allApp.service.checkAndNoCheck();
		   allApp.controller.page();
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
	   checkAndNoCheck : function(){
		   $("#checkAll").click(function(){
			   $("input[name='checkbox']:checkbox").prop("checked",$(this).is(":checked"));
           });
		   $("input[name='checkbox']:checkbox").click(function(){
			   var allBox =  $("input[name='checkbox']:checkbox");
			   $("#checkAll").prop("checked", allBox.length == allBox.filter(":checked").length ? true : false);
		   });
	   }
	},
	controller : {
	   search : function(){
	    	var sts = $("#sts").val();
	    	var appCode = $("#appCode").val();
	    	$.ajax({
					url:contextPath+'/usr/applications/getAppTable',
					type:'POST',
					dataType : 'html',	
					data:{"sts":sts,"appCode":appCode},
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
	   page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var sts = $("#hiddenSts").val();
		    	var appCode = $("#hiddenAppCode").val();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
			    $.ajax({
			    	url:contextPath+'/usr/applications/getAppTable',
					type:'POST',
					dataType : 'html',	
					data:{"sts":sts,"appCode":appCode,"pageNo":pageNo},
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
	   delUrl : function(){
		   confirm("确定要删除吗，删除后不可恢复");
           var ids = allApp.service.getCheckedUrl();
           if(ids == ""){
        	   return false;
           }
			$.ajax({
				url:contextPath+'/usr/applications/del',
				type:'POST',
				dataType : 'json',	
				data:{"id":ids},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						alert("删除成功");
						window.location.href=contextPath+"/usr/applications/list";
					}
				},
				error:function(){
					alert("删除失败");
				}
			});
	   },
	   deleteByOne : function(id, code){
		   confirm("确定要删除吗，删除后不可恢复");
			$.ajax({
				url:contextPath+'/usr/applications/del',
				type:'POST',
				dataType : 'json',	
				data:{"id":id, "code":code},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						alert("删除成功");
						window.location.href=contextPath+"/usr/applications/list";
					}
				},
				error:function(){
					alert("删除失败");
				}
			});
	   },
	   toDownloadAuthFile : function(){
		    var applicationId = $("#applicationCode").val();
		    if(applicationId == ""){
			   $("#application_error").show();
		    }
		    var form = $("<form>");
		    form.attr("style", "display:none");
		    form.attr("method", "post");
		    form.attr("action", contextPath + "/usr/applications/authfile/download/" + applicationId);
		    var exportData = $("<input>");
		    exportData.attr("type", "hidden");
		    exportData.attr("name", "exportData");
		    exportData.attr("value", (new Date()).getMilliseconds());
		    var exclusiveException = $("<input>");
		    exclusiveException.attr("type", "hidden");
		    exclusiveException.attr("name", "exclusiveException");
		    exclusiveException.attr("value", $("#exclusiveException").val());
		    var authType = $("<input>");
		    authType.attr("type", "hidden");
		    authType.attr("name", "authType");
		    authType.attr("value", $("#authType").val());
		    $("#authFiledownLoad").append(form);
		    form.append(exportData);
		    form.append(exclusiveException);
		    form.append(authType);
		    form.submit();
	   },
	   paramInit : function(){
			$.ajax({
				url:contextPath+'/usr/applications/init',
				type:'POST',
				dataType : 'json',	
				success:function(data){
					console.info(data);
					if(data.result == ""){
						$("#isGlobalConfig").css("display","none");
						$("#showGlobalConfig").css("display","none");
						$("#createGlobalConfig").css("display","block");
					}else{
						$("#period").val(data.period);
						$("#mailTo").val(data.mailTo);
						$("#mailCc").val(data.mailCc);
					}
				},
				error:function(){
					
				}
			});
	   },
	   addGlobalConfig : function(){
			var period = $("#period_add").val();
			var mailTo = $("#mailTo_add").val();
			var mailCc = $("#mailCc_input").val();
			if(period == ""){
				$("#period_add_error_span").css("display","block");
				return;
			}
			if(mailTo == ""){
				$("#mailTo_add_error_span").css("display","block");
				return;
			}
			if(mailCc == ""){
				$("#mailCc_add_error_span").css("display","block");
				return;
			}
			
			$.ajax({
				url:contextPath+'/usr/applications/addGlobalConfig',
				type:'POST',
				dataType : 'json',	
				data:{"period":period, "mailTo":mailTo, "mailCc":mailCc},
				beforeSend:function(){$("#bcode_error_span").show().text("验证中......");},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						$("#period_add_error_span").show().text(data.attach);
						window.location.href=contextPath+"/usr/applications/add";
					}else{
						$("#period_add_error_span").show().text("新增失败");
					}
				},
				error:function(){
					alert("新增失败");
				}
			});
			
	   },
	   save : function(){
			var appCode = $("#appCode").val();
			var appDesc = $("#appDesc").val();
			var businessLine = $("#ab").val();
			var isGlobalConfig = $("#isGlobalConfig").is(':checked');
			var isUpdateGlobalConfig = $("#isUpdateGlobalConfig").is(':checked');
			if(appCode == ""){
				$("#appCode_error_span").show();
				return;
			}
			
			if(businessLine == ""){
				$("#businessLine_error_span").show();
				return;
			}
			if(extend && isUpdateGlobal){
				var period = $("#period").val();
				var mailTo = $("#mailTo").val();
				var mailCc = $("#mailCc").val();
				if(period == ""){
					$("#period_error_span").show();
					return false;
				}
				if(mailTo == ""){
					$("#mailTo_error_span").show();
					return false;
				}
				if(mailCc == ""){
					$("#mailCc_error_span").show();
					return false;
				}
			}
			
			$.ajax({
				url:contextPath+'/usr/applications/save',
				type:'POST',
				dataType : 'json',	
				data:{"appCode":appCode, "appDesc":appDesc, "businessLine" : businessLine, "isGlobalConfig":isGlobalConfig,
					  "period":period, "mailTo":mailTo, "mailCc":mailCc, "isUpdateGlobalConfig":isUpdateGlobalConfig},
				beforeSend:function(){$("#appCode_error_span").show().text("验证中......");},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						if(data.attach!="操作成功"){
							$("#appCode_error_span").show().text(data.attach);
						}else{
							window.location.href=contextPath+"/usr/applications/list";
						}
					}else{
						alert("新增失败!");
					}
				},
				error:function(){
					alert("error:新增失败!");
				}
			});
	   }
	}
};