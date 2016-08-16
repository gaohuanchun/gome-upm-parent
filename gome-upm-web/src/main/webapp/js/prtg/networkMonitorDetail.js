$(function(){

	//跳转到设备列表页
	$('.backBtn').click(function(){
		window.history.back();
		//window.location.href=contextPath+"/prtg/device/all";
	});
	networkMonitorDetail.service.init();
});


var networkMonitorDetail = {
		service : {		
			init :function(){
				networkMonitorDetail.controller.search(1);
				networkMonitorDetail.service.bindPageClick();
				networkMonitorDetail.service.clickDetail();
				networkMonitorDetail.service.addRemark();
			},
			bindPageClick : function(){
			    $(".portTable .list_table").on("click",".pageNumber", function(){
			    	var pageNo = $(this).attr("pageNo");
			    	networkMonitorDetail.controller.search(pageNo);
			    });
			},
			clickDetail : function(){
				$(".portTable .list_table").on("click",".portDetails",function(){
					var deviceId = $(this).attr("deviceId");
					var sensorId = $(this).attr("sensorId");
					window.location.href=contextPath+"/prtg/sensor/detail?sensorId="+sensorId+"&deviceId="+deviceId;//跳转到传感器详情页
				});
			},
			addRemark : function(){
				$('.portTable').on('blur','.inputEvent',function(e){
					e.stopPropagation(); 
					var remark = $(this).val();
					var oldRemark = $(this).parent().attr("remark");
					//alert("remark:"+remark);
					var sensorId = $(this).parent().attr("sensorId");
					$(this).parent().empty().append('<p>'+remark+'</p>');
					if(oldRemark == remark){
						return false;
					}
					networkMonitorDetail.controller.addRemark(sensorId,remark);
				});
			}
		},
		controller : {
			search : function(pageNo){
				var deviceId = $("#hiddenDeviceId").val();
				var pageSize = 10;
				$.ajax({
					url:contextPath+'/prtg/device/detail/table',
					type:'POST',
					dataType : 'html',	
					data:{"deviceId":deviceId,"pageNo":pageNo,"pageSize":pageSize},
					success:function(data){
						var bool = data.indexOf("sessionTimeOut");
						if(bool < 0){
							$(".portTable .list_table").empty();
							$(".portTable .list_table").append(data);
						}else{
							window.location.href=contextPath+"/home";
						}
						
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			},			
			addRemark : function(sensorId,remark){
				$.ajax({
					url:contextPath+'/prtg/addSensorRemark',
					type:'POST',
					dataType : 'json',	
					data:{"sensorId":sensorId,"remark":remark},
					success:function(data){
						if(data.code == 1){
							alert("添加成功");
						}
						
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			}
		}
		
}