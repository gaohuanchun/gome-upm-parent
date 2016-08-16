$(function(){
	// 查询
	$("#btn_search").click(function(){
	    businessLine.controller.search();
	});
	
	//删除
	$("#btn-del").click(function(){
		businessLine.controller.del();
	});
	
	businessLine.service.init();
});

var businessLine = {
    service : {
 	   init : function(){
 		  businessLine.service.checkAndNoCheck();
 		  businessLine.controller.page();
	   },
	   getCheckedUrl : function(){
		   var ids = "";
		   $("input[name='checkbox']:checkbox").each(function(){
			   if($(this).is(":checked")){
				   ids += $(this).val()+",";
			   }
		   });
		  return ids.substring(0, ids.length-1);
	   },
	   checkAndNoCheck : function(){
		   $("#checkAll").click(function(){
			   $("input[name='checkbox']:checkbox").prop("checked",$(this).is(":checked"));
           });
		   $("input[name='checkbox']:checkbox").click(function(){
			   var allBox =  $("input[name='checkbox']:checkbox");
			   $("#checkAll").prop("checked", allBox.length == allBox.filter(":checked").length ? true : false);
		   });
	   }
    },
	controller : {
		search : function(){
			var bcode = $("#bcode").val();
			var bname = $("#bname").val();
	    	$.ajax({
					url:contextPath+'/businessLine/getBusinessLineTable',
					type:'POST',
					dataType : 'html',	
					data:{"bcode":bcode,"bname":bname},
					success:function(data){
						console.info(data);
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
 	    page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var bcode = $("#hiddenBcode").val();
		    	var bname = $("#hiddenBname").val();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
			    $.ajax({
					url:contextPath+'/businessLine/getBusinessLineTable',
					type:'POST',
					dataType : 'html',	
					data:{"bcode":bcode,"bname":bname,"pageNo":pageNo},
					success:function(data){
						console.info(data);
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
		    });
	    },
		save : function(){
			var bcode = $("#bcode_input").val();
			var bname = $("#bname_input").val();
			var bdesc = $("#bdesc_input").val();
			var data_order = $("#order_input").val();
			if(bcode == ""){
				$("#bcode_error_span").show();
				return;
			}
			if(bname == ""){
				$("#bname_error_span").show();
				return;
			}
			$.ajax({
				url:contextPath+'/businessLine/save',
				type:'POST',
				dataType : 'json',	
				data:{"bcode":bcode, "bname":bname, "data_order":data_order, "bdesc":bdesc},
				beforeSend:function(){$("#bcode_error_span").show().text("验证中......");},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						if(data.attach!="操作成功"){
							$("#bcode_error_span").show().text(data.attach);
						}else{
							window.location.href=contextPath+"/businessLine/list";
						}
					}else{
						alert("新增失败!");
					}
				},
				error:function(){
					alert("新增失败!");
				}
			});
		},
	    del : function(){
			   confirm("确定要删除吗，删除后不可恢复");
	           var ids = businessLine.service.getCheckedUrl();
	           if(ids == ""){
	        	   return false;
	           }
				$.ajax({
					url:contextPath+'/businessLine/del',
					type:'POST',
					dataType : 'json',	
					data:{"id":ids},
					success:function(data){
						console.info(data);
						if(data.code == 1){
							alert("删除成功");
							window.location.href=contextPath+"/businessLine/list";
						}
					},
					error:function(){
						alert("删除失败");
					}
				});
		 }
	}	
};