$(function(){
	
	$("#previous-step1").click(function(){
		createUrl.controller.previousStep();
	});
	
	$("#btn-submit").click(function(){
		createUrl.controller.submit();
	});
});

var createUrl = {
		   service : {
			   getCheckedAlarm : function(){
				   var ids = "";
				   $("input[name='alarmWay']:radio").each(function(){
					   if($(this).is(":checked")){
						   ids += $(this).val()+",";
					   }
				   });
				   if(ids.length==0){
					   alert("请选择报警方式");
					   return "";
				   }
				  return ids.substring(0, ids.length-1);
			   },
		   },
		   controller : {
			   submit : function(){
					var content = {};
					content.key = $("#urlKey").val();
					content.desc = $("#urlDesc").val();
					content.postParameter = $("#postParameter").val();
					content.urlAddress = $("#urlAddress").val();
					content.accFre = $("#accFre").val();
					content.accTimeOut = $("#accTimeOut").val();
					content.timeOutNum = $("#timeOutNum").val();
					content.alarmInter = $("#alarmInter").val();
					content.method = $("#method").val();
					content.resContent = $("#resContent").val();
					content.isContainsCon = $("#isContainsCon").val();
					content.isDefaultCode = $("#isDefaultCode").val();
					content.returnCode = $("#returnCode").val();
					content.alarmWay = createUrl.service.getCheckedAlarm();
//					if(content.key == ""){
//						alert("请返回第一步填写必选项");
//						return false;
//					}
					if(content.alarmWay.length==0){
						   alert("请选择报警方式");
						   return "";
					   }
					if(content.urlAddress == "" || content.accTimeOut == "" || content.timeOutNum == "" || content.alarmInter == ""){
						alert("请返回第二步请输入必填项");
						return false;
					}
					$.ajax({
						url:contextPath+'/url/save',
						type:'POST',
						dataType : 'json',	
						data:{'content':JSON.stringify(content)},
						success:function(data){
							if(data.code == 1){
								//console.info(data.attach);
								window.location.href =contextPath+"/url/get";
							}
//							$(".content-wrapper").empty();
//							$(".content-wrapper").append(data);
						},
						error:function(){
							alert("操作失败");
							
						}
						
						
					});
			   },
			 previousStep : function(){
					var key = $("#urlKey").val();
					var desc = $("#urlDesc").val();
					var app = $("#urlApp").val();
					var urlAddress = $("#urlAddress").val();
					var accFre = $("#accFre").val();
					var postParameter = $("#postParameter").val();
					var accTimeOut = $("#accTimeOut").val();
					var timeOutNum = $("#timeOutNum").val();
					var alarmInter = $("#alarmInter").val();
					var method = $("#method").val();
					var resContent = $("#resContent").val();
					var isContainsCon = $("#isContainsCon").val();
					var isDefaultCode = $("#isDefaultCode").val();
					var returnCode = $("#returnCode").val();
					var data ={
							'postParameter':postParameter,
							'urlAddress':urlAddress,
							'accFre':accFre,
							'accTimeOut':accTimeOut,
							'timeOutNum':timeOutNum,
							'alarmInter':alarmInter,
							'method':method,
							'resContent':resContent,
							'isContainsCon':isContainsCon,
							'isDefaultCode':isDefaultCode,
							'returnCode':returnCode
						};
					StandardPost(contextPath+'/url/create/step2',data);
					//var url = contextPath+"/url/create/step2?postParameter="+key+"&app="+app+"&desc="+desc+"&urlAddress="+urlAddress+"&accFre="+accFre+"&accTimeOut="+accTimeOut+"&timeOutNum="+timeOutNum+"&alarmInter="+alarmInter+"&method="+method+"&resContent="+resContent+"&isContainsCon="+isContainsCon+"&isDefaultCode="+isDefaultCode+"&returnCode="+returnCode;
					//window.location.href=url;
			 }  
		   }
		};

function StandardPost (url,args) 
{
    var form = $("#form1");
    form.attr({"action":url});
    for (arg in args)
    {
        var input = $("<input type='hidden'>");
        input.attr({"name":arg});
        input.val(args[arg]);
        form.append(input);
    }
    $("#submit_id").click();
}