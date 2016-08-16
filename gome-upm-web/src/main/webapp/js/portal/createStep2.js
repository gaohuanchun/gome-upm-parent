$(function(){

	
	$("#step2-next").click(function(){
		createPortal.controller.toStep3();
	});
	
	$("#previous-step").click(function(){
		createPortal.controller.previousStep();
	});
	$("#portalAddress").change(function(){
		createPortal.controller.isPortalExists();
	});
});
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

var createPortal = {
  service:{
	 requiredInputBlur : function(){
		  $("#portalAddress").focus(function(){
			  $("#portalAddress").find($(".error_span")).show().text("请输入监控地址");
		  });
	  }
  },	
  controller : {
	   nextStep : function(){
			var content = {};
			content.key = $("#portalKey").val();
			content.desc = $("#portalDesc").val();
			content.app = $("#portalApp").val();
			content.portalAddress = $("#portalAddress").val();
			content.accFre = $("#accFre").val();
			content.returnCode =$("#returnCode").val();
			content.accTimeOut = $("#accTimeOut").val();
			content.timeOutNum = $("#timeOutNum").val();
			content.alarmInter = $("#alarmInter").val();

			$.ajax({
				url:contextPath+'/portal/create/step3',
				type:'POST',
				dataType : 'html',	
				data:{'content':JSON.stringify(content)},
				success:function(data){
					console.info(data);
					$(".wrapperr").empty();
					$(".wrapperr").append(data);
				},
				error:function(){
					alert("操作失败");
					
				}
				
				
			});
	   },
	   isPortalExists:function(){
		   var port = $("#portalAddress").val().trim();
		   var flag = false;
			if(port.length==0){
			  $("#portalAddress").siblings(".error_span").show().text("监控地址不能为空");
			  $("#portalAddress").focus();
			  return flag;
			}else{
				var reg=/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}:\d{1,5}$/;
				var b =reg.test(port);
				if(!b){
					$("#portalAddress").siblings(".error_span").show().text("监控地址输入不正确");
					$("#portalAddress").focus();
					return flag;
				}
				$.ajax({
					url:contextPath+'/portal/getByPort',
					type:'POST',
					dataType : 'json',	
					data:{'port':port},
					async:false,
					success:function(data){
						console.info(data);
						if(data.code == 1){
							if(!data.attach){
								flag = true;
								$("#portalAddress").siblings(".error_span").show().text("监控地址可以使用")
								return flag;
							}else{
								$("#portalAddress").siblings(".error_span").show().text("监控地址已存在");
								$("#portalAddress").focus();
								return flag;
							}

						}
					},
					error:function(){
						$("#portalAddress").siblings($(".error_span")).show().text("验证失败");
						return flag;
					}
			
			
				});
			}

					
					return flag;
	   },
	   toStep3 : function(){
		   $(".info_span.ele_hide").hide();
		   // var flag = true;
			var portalAddress = $("#portalAddress").val();
			var monitorType = $("#monitorType").val();
			var overtimes = $("#overtimes").val();
			var frequency = $("#frequency").val();
			var url = contextPath+"/portal/create/step3?portalAddress="+portalAddress+"&monitorType="+monitorType+"&overtimes="+overtimes+"&frequency="+frequency;
//			if(urlAddress == "" ){
//				$("#urlAddress").siblings($(".error_span")).show().text("请输入监控地址");
//				flag = false;
//			}
//			if(accTimeOut == "" ){
//				$("#accTimeOut").siblings($(".error_span")).show().text("请输入超时时间");
//				flag = false;
//			}
//			if(timeOutNum == "" ){
//				$("#timeOutNum").siblings($(".error_span")).show().text("请输入超时次数");
//				flag = false;
//			}
//			if(alarmInter == "" ){
//				$("#alarmInter").siblings($(".error_span")).show().text("请输入报警间隔");
//				flag = false;
//			}
			var flag =createPortal.controller.isPortalExists();
			if(flag){
				var data ={
					'portalAddress':portalAddress,
					'monitorType':monitorType,
					'overtimes':overtimes,
					'frequency':frequency
				};
				StandardPost(contextPath+'/portal/create/step3',data);
			}
			
	   },
	   previousStep : function(){
			var key = $("#portalKey").val();
			var desc = $("#portalDesc").val();
			var app = $("#portalApp").val();
			var url = contextPath+"/portal/create/step1?key="+key+"&desc="+desc+"&app="+app;
			window.location.href=url;
	   }
  }
};