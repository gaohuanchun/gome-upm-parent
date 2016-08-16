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
		if(startTime.length > 0 && endTime.length > 0){
			content.startTime = startTime;
			content.endTime = endTime;
		}
		var type = $("#type").val();
		if(type != "" && type != null){
			content.type=type;
		}
		var groupName = $("#groupName").val();
		if(groupName != "" && groupName != null){
			content.groupName=encodeURIComponent(groupName);
		}
		var host = $("#host").val();
		if(host != "" && host != null){
			content.host=encodeURIComponent(host);
		}
		var status = $("#status").val();
		if(status != "" && status != null){
			content.status=status;
		}
		content.search=true;
		$.ajax({
			url:'../server/list',
			type:'POST',
			async:true,
			dataType:'html',
			data:{
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

$("#groupName").change(function(obj){
	var group = $(this).children('option:selected').val();
	$.ajax({
		url:'../server/getHost',
		type:'POST',
		dataType : 'json',	
		data:{group:group},
		success:function(data){
			if(data.code == 1){
				$("#host").empty();
				$("#host").append("<option value=''>请选择</option>");
				for(var i=0;i<data.attach.length;i++){
					var optionV = decodeURIComponent(data.attach[i]);
					$("#host").append("<option value='"+optionV+"'>"+data.attach[i]+"</option>");
				}
			}
		},
		error:function(){
			alert("操作失败");
		}
	});
});
