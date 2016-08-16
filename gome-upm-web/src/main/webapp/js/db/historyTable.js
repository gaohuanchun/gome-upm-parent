/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//分页搜索
	$(".pageNumber").unbind("click");
	$(".pageNumber").bind("click", function(){
		var page = $(this).attr("pageNo");
		if(typeof(page) == "undefined"){
			page = null;
		}
		var searchConditions = $("#searchConditions").val();
		var dataType = $("#dataType").val();
		var pid = $("#pid").val();
		if(searchConditions == ""){
			var content = {};
			content.search=true;
			searchConditions = JSON.stringify(content);
		}
		
		$.ajax({
			url:'../db/history',
			type:'POST',
			dataType:'html',
			async:false,
			data:{
				dataType:dataType,
				pid:pid,
				content:searchConditions,
				page:page
			},
			success:function(data){
				$(".list_table").empty();
				$(".list_table").append(data);
					
			},
			error:function(){
//				pop_up("系统异常",false);
				alert("服务器异常");
			}
			
		});
	});
});
