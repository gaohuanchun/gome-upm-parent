$(function(){
	networkMonitorAllfacility.service.init();
});


var networkMonitorAllfacility = {
		service : {
			init : function(){
				networkMonitorAllfacility.service.clickDeviceDetail();
				networkMonitorAllfacility.service.groupSelectChange();
				networkMonitorAllfacility.service.search();
				networkMonitorAllfacility.controller.search(null,null,null,1);
				networkMonitorAllfacility.service.bindPageClick();
			},
			clickDeviceDetail : function(){
				$(".list_table").on("click",".deviceDetail",function(){
					var deviceId = $(this).attr("deviceId");
					window.location.href=contextPath+"/prtg/device/detail?deviceId="+deviceId;
				});
			},
			groupSelectChange : function(){
				$("#groupSelect").change(function(){
					var groupId = $(this).val();
					if(groupId != ""){
						networkMonitorAllfacility.controller.getDevices(groupId);
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
			    	networkMonitorAllfacility.controller.search(groupId,deviceId,deviceStatus,pageNo);
			    });
			},
			search : function(){
				$("#portSearchBtn").click(function(){
					var groupId = $("#groupSelect").val();
					var deviceId = $("#deviceSelect").val();
					var deviceStatus = $("#deviceStatusSelect").val();
					networkMonitorAllfacility.controller.search(groupId,deviceId,deviceStatus,1);
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
					url:contextPath+'/prtg/device/allTable',
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
			}
		
		}
};
