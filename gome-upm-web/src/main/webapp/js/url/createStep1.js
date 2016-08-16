$(function(){
	$("#step1-next").click(function(){
		createUrl.controller.nextStep();
	});
	
	$("#urlKey").focus(function(){
		$("#error_span").hide();
	});
	
	$("#urlKey").blur(function(){
		createUrl.controller.varifyUrlKey();
	});
});

var createUrl = {
		   service : {
			   
		   },
		   controller : {
			   nextStep : function(){
				    //var flag = createUrl.controller.varifyUrlKey();
				    var flag = true;
				    if(flag){
						var key = $("#urlKey").val();
						var desc = $("#urlKeydec").val();
						var urlApp = $("#urlApp").val();
						window.location.href=contextPath+"/url/create/step2?key="+key+"&desc="+desc+"&app="+urlApp;
				    }

			   },
			   //验证
			   varifyUrlKey : function(){
				   var flag = false;
				    var key = $("#urlKey").val();
					if(key == undefined || key == ""){
						$("#error_span").show().text("提示：请输入监控点KEY");
						return false;
					}
					
					var content = {};
					content.key = key;
					$.ajax({
						url:contextPath+'/url/varifyKey',
						type:'POST',
						dataType : 'json',	
						data:{'content':JSON.stringify(content)},
						beforeSend:function(){$("#error_span").show().text("验证中......");},
						success:function(data){
							console.info(data);
							$("#error_span").hide();
							if(data.code == 1){
								flag = true;
							}
							$("#step1-next").prop("disabled");
						},
						error:function(){
							$("#error_span").show().text("异步验证请求失败")
						}
						
						
					});
					return flag;
			   }
		   }
		};