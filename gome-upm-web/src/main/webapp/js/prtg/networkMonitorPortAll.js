$(function(){
	networkMonitorPortAll.service.init();
});


var networkMonitorPortAll = {
		service : {
			init : function(){
				networkMonitorPortAll.service.clickDeviceDetail();
				networkMonitorPortAll.service.groupSelectChange();
				networkMonitorPortAll.service.search();
				networkMonitorPortAll.controller.search(null,null,null,1);
				networkMonitorPortAll.service.bindPageClick();
				networkMonitorPortAll.service.addRemark();
			},
			clickDeviceDetail : function(){
				$(".list_table").on("click",".deviceDetail",function(){
					var deviceId = $(this).attr("deviceId");
					window.location.href=contextPath+"/prtg/device/detail?deviceId="+deviceId;
				});
				$(".list_table").on("click",".sensorDetail",function(){
					var deviceId = $(this).attr("deviceId");
					var sensorId = $(this).attr("sensorId");
					window.location.href=contextPath+"/prtg/sensor/detail?sensorId="+sensorId+"&deviceId="+deviceId;//跳转到传感器详情页
				});
				
			},
			groupSelectChange : function(){
				$("#groupSelect").change(function(){
					var groupId = $(this).val();
					if(groupId != ""){
						networkMonitorPortAll.controller.getDevices(groupId);
					}else{
						$("#deviceSelect").val("全部");
						$("#deviceSelect").prop("disabled",true);
					}
					
				});
			},
			bindPageClick : function(){
			    $(".list_table").on("click",".pageNumber", function(){
			    	var pageNo = $(this).attr("pageNo");
					var groupId = $("#hiddenGroupId").val();
					var deviceId = $("#hiddenDeviceId").val();
					var deviceStatus = $("#hiddenDeviceStatus").val();
			    	networkMonitorPortAll.controller.search(groupId,deviceId,deviceStatus,pageNo);
			    });
			},
			search : function(){
				$("#portSearchBtn").click(function(){
					var groupId = $("#groupSelect").val();
					var deviceId = $("#deviceSelect").val();
					var deviceStatus = $("#deviceStatusSelect").val();
					networkMonitorPortAll.controller.search(groupId,deviceId,deviceStatus,1);
				});
			},
			addRemark : function(){
				$('.list_table').on('blur','.inputEvent',function(e){
					e.stopPropagation(); 
					var remark = $(this).val();
					var oldRemark = $(this).parent().attr("remark");
					//alert("remark:"+remark);
					var sensorId = $(this).parent().attr("sensorId");
					$(this).parent().empty().append('<p>'+remark+'</p>');
					if(oldRemark == remark){
						return false;
					}
					networkMonitorPortAll.controller.addRemark(sensorId,remark);
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
			search : function(groupId,deviceId,deviceStatus,pageNo){
				var pageSize = 10;
				$.ajax({
					url:contextPath+'/prtg/port/allTable',
					type:'POST',
					dataType : 'html',	
					data:{"groupId":groupId,"deviceId":deviceId,"deviceStatus":deviceStatus,"pageNo":pageNo,"pageSize":pageSize},
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
			},
			addRemark : function(sensorId,remark){
				if(remark == ""){
					return;
				}
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
};