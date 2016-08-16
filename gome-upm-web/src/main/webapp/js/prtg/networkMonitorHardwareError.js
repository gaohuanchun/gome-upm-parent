$(function(){
	networkMonitorHardwareError.service.init();
});


var networkMonitorHardwareError = {
		service : {
			init : function(){
				networkMonitorHardwareError.service.clickDeviceDetail();
				networkMonitorHardwareError.service.groupSelectChange();
				networkMonitorHardwareError.service.search();
				networkMonitorHardwareError.controller.search(null,null,null,null,1);
				networkMonitorHardwareError.service.bindPageClick();
			},
			clickDeviceDetail : function(){
				$(".list_table").on("click",".deviceDetail",function(){
					var deviceId = $(this).attr("deviceId");
					window.location.href=contextPath+"/prtg/device/detail?deviceId="+deviceId;
				});
//				$(".list_table").on("click",".sensorDetail",function(){
//					var deviceId = $(this).attr("deviceId");
//					var sensorId = $(this).attr("sensorId");
//					window.location.href=contextPath+"/prtg/sensor/detail?sensorId="+sensorId+"&deviceId="+deviceId;//跳转到传感器详情页
//				});
				
			},
			groupSelectChange : function(){
				$("#groupSelect").change(function(){
					var groupId = $(this).val();
					if(groupId != ""){
						networkMonitorHardwareError.controller.getDevices(groupId);
					}else{
						$("#deviceSelect").val("全部");
						$("#deviceSelect").prop("disabled",true);
					}
					
				});
			},
			bindPageClick : function(){
			    $(".list_table").on("click",".pageNumber", function(){
			    	var pageNo = $(this).attr("pageNo");
			    	var sdate = $("#hiddenSdate").val();
					var edate = $("#hiddenEdate").val();
					var groupId = $("#hiddenGroupId").val();
					var deviceId = $("#hiddenDeviceId").val();
					networkMonitorHardwareError.controller.search(sdate,edate,groupId,deviceId,pageNo);
			    });
			},
			search : function(){
				$("#portSearchBtn").click(function(){
					var sdate = $("#startTime").val();
					var edate = $("#endTime").val();
					var groupId = $("#groupSelect").val();
					var deviceId = $("#deviceSelect").val();
					var beginDate = new Date(sdate.replace(/-/g,"/"));
					var endDate = new Date(edate.replace(/-/g,"/"));
					if(beginDate >= endDate){
						alert("截止时间必须大于开始时间");
						return false;
					}
					networkMonitorHardwareError.controller.search(sdate,edate,groupId,deviceId,1);
				});
			}
			
		},
		controller : {
			getDevices : function(groupId){
				if(groupId == null){
					return;
				}
				$.ajax({
					url:contextPath+'/prtg/device/ajax',
					type:'POST',
					dataType : 'json',	
					data:{"groupId":groupId},
					success:function(data){
						if(data.code == 1){
							//请求成功
							$("#deviceSelect").prop("disabled",false);
							$("#deviceSelect").empty();
							var deviceArr = data.attach;
							$("<option value=''>全部</option>").appendTo("#deviceSelect");
							for(var i=0;i<deviceArr.length;i++){
								$("<option value="+deviceArr[i].objId+">"+deviceArr[i].device+"</option>").appendTo("#deviceSelect");
							}
							
						}
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			},
			search : function(sdate,edate,groupId,deviceId,pageNo){
				var pageSize = 10;
				$.ajax({
					url:contextPath+'/prtg/device/error/table',
					type:'POST',
					dataType : 'html',	
					data:{"groupId":groupId,"deviceId":deviceId,"pageNo":pageNo,"pageSize":pageSize,"sdate":sdate,"edate":edate},
					success:function(data){
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
