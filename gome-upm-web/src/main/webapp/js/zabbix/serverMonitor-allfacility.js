$(function(){
	//搜索
	$(".newbtn").unbind("click");
	$(".newbtn").bind("click", function(){
		var content = {};
		content.orderBy = $("#orderBy").val();
		content.orderByCpu = $("#orderByCpu").val();
		var groupName = $("#group").val();
		if(groupName != "" && groupName != null){
			content.groupName=encodeURIComponent(groupName);
		}
		var hostName = $("#host").val();
		if(hostName != "" && hostName != null){
			content.host=encodeURIComponent(hostName);
		}
		content.search=true;
		$.ajax({
			url:'../server/getHostAll',
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
});

$("#group").change(function(obj){
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

var orderByCpu = 1;
function orderByCpuM(){
	var page = $(this).attr("pageNo");
	if(typeof(page) == "undefined"){
		page = null;
	}
	var searchConditions = $("#searchConditions").val();
	if(orderByCpu!=null){
		orderByCpu = Number(orderByCpu)+1;
	}
	var orderBy;
	if(Number(orderByCpu)%2==0){
		orderBy = '1';
	}else{
		orderBy = '2';
	}
	if(searchConditions == ""){
		var content = {};
		content.search=true;
		content.orderByCpu = orderByCpu;
		content.orderBy = orderBy;
		searchConditions = JSON.stringify(content);
	}
	$.ajax({
		url:'../server/getHostAll',
		type:'POST',
		dataType:'html',
		async:false,
		data:{
			content:searchConditions,
			page:page
		},
		success:function(data){
			$(".list_table").empty();
			$(".list_table").append(data);
		},
		error:function(){
			alert("服务器异常");
		}
	});
}
var orderByLoad = 1;
function orderByLoadM(){
	var page = $(this).attr("pageNo");
	if(typeof(page) == "undefined"){
		page = null;
	}
	var searchConditions = $("#searchConditions").val();
	if(orderByLoad!=null){
		orderByLoad = Number(orderByLoad)+1;
	}
	var group = $("#group").children('option:selected').val();
	var host = $("#host").children('option:selected').val();
	var orderBy;
	if(Number(orderByLoad)%2==0){
		orderBy = '5';
	}else{
		orderBy = '6';
	}
	if(searchConditions == ""){
		var content = {};
		content.search=true;
		content.orderByCpu = orderByLoad;
		content.orderBy = orderBy;
		searchConditions = JSON.stringify(content);
	}
	$.ajax({
		url:'../server/getHostAll',
		type:'POST',
		dataType:'html',
		async:false,
		data:{
			content:searchConditions,
			page:page
		},
		success:function(data){
			$(".list_table").empty();
			$(".list_table").append(data);
		},
		error:function(){
			alert("服务器异常");
		}
	});
}
var orderByMemory = 1;
function orderByMemoryM(){
	var page = $(this).attr("pageNo");
	if(typeof(page) == "undefined"){
		page = null;
	}
	var searchConditions = $("#searchConditions").val();
	if(orderByMemory!=null){
		orderByMemory = Number(orderByMemory)+1;
	}
	var group = $("#group").children('option:selected').val();
	var host = $("#host").children('option:selected').val();
	var orderBy;
	if(Number(orderByMemory)%2==0){
		orderBy = '3';
	}else{
		orderBy = '4';
	}
	if(searchConditions == ""){
		var content = {};
		content.search=true;
		content.orderByCpu = orderByMemory;
		content.orderBy = orderBy;
		searchConditions = JSON.stringify(content);
	}
	$.ajax({
		url:'../server/getHostAll',
		type:'POST',
		dataType:'html',
		async:false,
		data:{
			content:searchConditions,
			page:page
		},
		success:function(data){
			$(".list_table").empty();
			$(".list_table").append(data);
		},
		error:function(){
			alert("服务器异常");
		}
	});
}
