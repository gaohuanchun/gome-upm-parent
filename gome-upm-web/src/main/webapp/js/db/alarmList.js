/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".newbtn").unbind("click");
	$(".newbtn").bind("click", function(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if(startTime.length != 0 && endTime == 0){
			alert("请输入结束时间！");
			return;
		} else if(startTime.length == 0 && endTime != 0){
			alert("请输入开始时间！");
			return;
		}
		var content = {};
		var serverAddr = $("#serverAddr").val();
		if(serverAddr.length > 0){
			var ipRegex = /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])$/;
			if(ipRegex.test(serverAddr)){
				content.serverAddr=serverAddr;
			} else {
				alert("IP地址格式有误！");
				return;
			}
			
		}
		
		var dataType = $("#dataType").val();
		var alarmLevel = $("#alarmLevel").val();
//		if(serverAddr != "" && serverAddr != null){
//			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
//			if(serverAddr.indexOf("%") != -1){
//				serverAddr = serverAddr.replace(/%/g, "\\%");
//			}else if(serverAddr.indexOf("_") != -1){
//				serverAddr = serverAddr.replace(/_/g, "\\_");
//			}
//			content.serverAddr=serverAddr;
//		}
		if(startTime.length > 0 && endTime.length > 0){
			content.startTime = startTime;
			content.endTime = endTime;
		}
		content.search=true;
		$.ajax({
			url:'../db/alarm',
			type:'POST',
			dataType:'html',
			data:{
				dataType:dataType,
				alarmLevel:alarmLevel,
				content:JSON.stringify(content)
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
				$("#searchConditions").val(JSON.stringify(content));
			},
			error:function(){
				//pop_up("系统异常",false);
				alert("服务器异常");
			}
			
		});
	});
	
	//回车搜索事件
	$("#serverAddr").bind("keydown",function(e){
        // 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {    
	        //回车执行查询
	        $(".newbtn").click();
	     }    
	});
	
	laydate({
	    elem: '#startTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    //min: laydate.now(), //设定最小日期为当前日期
	    //max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: true,
	    choose: function(date){
	         //end.min = datas; //开始日选好后，重置结束日的最小日期
	         //end.start = datas //将结束日的初始值设定为开始日
	    	//alert("startTime:" + date);
	    }
	});
	
	laydate({
	    elem: '#endTime',
	    format: 'YYYY-MM-DD hh:mm:ss',
	    //min: laydate.now(), //设定最小日期为当前日期
	    //max: '2099-06-16 23:59:59', //最大日期
	    istime: true,
	    istoday: true,
	    choose: function(date){
	         //end.min = datas; //开始日选好后，重置结束日的最小日期
	         //end.start = datas //将结束日的初始值设定为开始日
	    	//alert("endTime:" + date);
	    }
	});
	
});