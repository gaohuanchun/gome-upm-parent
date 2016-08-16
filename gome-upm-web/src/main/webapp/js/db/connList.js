/**
 * Created by caowei on 2016/07/21
 */
$(function(){
	//搜索
	$(".newbtn").unbind("click");
	$(".newbtn").bind("click", function(){
		var content = {};
		var dbType = $("#dbType").val();
		if(dbType != "" && dbType != null){
			content.dbType=dbType;
		}
//		if(dbType != "" && dbType != null){
//			//如果包含%、_，则将其替换为 \%、\_  (%、_在sql中属于特殊字符)
//			if(dbType.indexOf("%") != -1){
//				dbType = dbType.replace(/%/g, "\\%");
//			}else if(dbType.indexOf("_") != -1){
//				dbType = dbType.replace(/_/g, "\\_");
//			}
//			content.dbType=dbType;
//		}
		content.search=true;
		
		$.ajax({
			url:'../db/allConn',
			type:'POST',
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
//				pop_up("系统异常",false);
				alert("服务器异常");
			}
			
		});
	});
	
	//回车搜索事件
//	$("#dbType").bind("keydown",function(e){
//        // 兼容FF和IE和Opera    
//	    var theEvent = e || window.event;    
//	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
//	    if (code == 13) {    
//	        //回车执行查询
//	        $(".newbtn").click();
//	     }    
//	});
});