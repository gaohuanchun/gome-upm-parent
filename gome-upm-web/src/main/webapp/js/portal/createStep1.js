$(function(){
	$("#step1-next").click(function(){
		createPortal.controller.nextStep();
	});
	
	$("#portalKey").focus(function(){
		$("#error_span").hide();
	});
	
	$("#portalKey").blur(function(){
		createPortal.controller.varifyProtalKey();
	});
});

var createPortal = {
		   service : {
			   
		   },
		   controller : {
			   nextStep : function(){
				    //var flag = createPortal.controller.varifyUrlKey();
				    var flag = true;
				    if(flag){
						var key = $("#portalKey").val();
						var desc = $("#portalKeydec").val();
						var app = $("#portalApp").val();
						window.location.href=contextPath+"/portal/create/step2?key="+key+"&desc="+desc+"&app="+app;
				    }

			   },
			   //验证
			   varifyProtalKey : function(){
				   var flag = false;
				    var key = $("#portalKey").val();
					if(key == undefined || key == ""){
						$("#error_span").show().text("提示：请输入监控点KEY");
						return false;
					}
					
					var content = {};
					content.key = key;
					$.ajax({
						url:contextPath+'/portal/varifyKey',
						type:'POST',
						dataType : 'json',	
						data:{'key':key},
						beforeSend:function(){$("#error_span").show().text("验证中......");},
						success:function(data){
							$("#error_span").hide();
							console.info(data);
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