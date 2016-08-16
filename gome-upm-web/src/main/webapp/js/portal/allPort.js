$(function(){
	var normal =$("#normal_id").text();
	var error = $("#error_id").text();
	draw_url_health_index(parseInt(normal),parseInt(error));
	//删除url
	$("#btn-del").click(function(){
	    allPort.controller.delUrl();
	});
	$("#btn_search").click(function(){
	    allPort.controller.search();
	});
	$("#import_submit").click(function(){
	
		importExcel();
	})
	
	$("#defaultReturnCode").on("click",function(){
		allPort.service.defaultReturnCode();
	});
	
	$("#customReturnCode").on("click",function(){
		allPort.service.customReturnCode();
	});
	
	allPort.service.init();
	

});

function quickSearch(evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
			var key = evt.keyCode?evt.keyCode:evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
			if(key == 13){ //判断是否是回车事件。
				//根据需要执行某种操作。
				//根据需要执行某种操作。
				allPort.controller.search();
			}
}
function importExcel(){
	var excel = $("#excel").val();
	if(isExcel(excel)){
		
		$("#import_close").click();
		$.ajaxFileUpload({
	                url: contextPath+'/portal/import',     //需要链接到服务器地址
	                secureuri: false,
	                type:'post',
	                fileElementId: 'excel',              //文件选择框的id属性
	                contentType:'text/html',
	                dataType:'text',
	                success: function (obj) {          //相当于java中try语句块的用法
						alert(obj);
						window.location.href=contextPath+"/portal/get";
	                },
	                error: function (data, status, e) {
	                    alert('server error!');
	                }
	            });
	            alert("导入中请等待...");
	}
}
function isExcel(filepath){
		if(filepath.length==0){
			alert('请选择Excel导入');
			return false;
		}
        var extStart=filepath.lastIndexOf('.');
        var ext=filepath.substring(extStart,filepath.length).toUpperCase();
        if(ext!='.XLSX'&&ext!='.XLS'){
            alert('导入文件不是Excel');
            return false;
        }
        return true;
}
function draw_url_health_index(normal,error){
	$('#url_health_index').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        title: {
            text: ''
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                size : 200,
                dataLabels: {
                    enabled: false,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f}%'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '健康指数',
            data: [
                {
                 	name:'正常',   
                    y:normal,
                    color:'#9c8ade'
                },
                {
                    name: '异常',
                    y: error,
                    sliced: true,
                    selected: true,
                    color:'#CFCFCF'
                }
            ]
        }]
    });
	$("#h2").show();
	$("#div_id").show();
}

var allPort = {
  service : {
	   init : function(){
		   allPort.service.checkAndNoCheck();
		   allPort.controller.useOrNoUse();
		   allPort.service.alarmCheckedVari();
		   allPort.controller.page();
		   allPort.controller.editUrl();
		   allPort.controller.editUrlSubmit();
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
	  defaultReturnCode : function(){
			  $("#urlBackCode").val("200");
			  $('#urlBackCode').attr("disabled","disabled");
		  },
	  customReturnCode : function(){
			  $("#urlBackCode").val("200"); 
			  $('#urlBackCode').attr("disabled",false);
		  },
	   checkAndNoCheck : function(){
		   $("#checkAll").click(function(){
			   //$("input[name='checkbox']:checkbox").prop("checked",this.checked);
			   $("input[name='checkbox']:checkbox").prop("checked",$(this).is(":checked"));
           });
		   
		   $("input[name='checkbox']:checkbox").click(function(){
			   var allBox =  $("input[name='checkbox']:checkbox");
			   $("#checkAll").prop("checked", allBox.length == allBox.filter(":checked").length ? true : false);
		   });
	   },
	   getCheckedAlarm : function(){
		   var ids = "";
		   $("input[name='alarmWay']:radio").each(function(){
			   if($(this).is(":checked")){
				   ids += $(this).val()+",";
			   }
		   });
		  return ids.substring(0, ids.length-1);
	   },
	   alarmCheckedVari : function(){
		   $(".list_table").on("click","#inlineCheckbox1",function(){
			   $("#inlineCheckbox2").prop("checked",false);
			});
		   
		   $(".list_table").on("click","#inlineCheckbox2",function(){
			   $("#inlineCheckbox1").prop("checked",false);
			});

	   }
   },	
   controller : {
	   createStep1 : function(){
			$.ajax({
				url:contextPath+'/url/create/step1',
				type:'POST',
				dataType : 'html',	
				data:{},
				success:function(data){
					console.info(data);
					$(".content-wrapper").empty();
					$(".content-wrapper").append(data);
				},
				error:function(){
					alert("操作失败");
					
				}
				
				
			});
	   },
	   delUrl : function(){
		   
           var ids = allPort.service.getCheckedUrl();
           if(ids == ""){
        	   alert("请选择数据")
        	   return false;
           }
           if(confirm("确定要删除吗，删除后不可恢复")){
			$.ajax({
				url:contextPath+'/portal/del',
				type:'POST',
				dataType : 'json',	
				data:{"id":ids},
				success:function(data){
					console.info(data);
					if(data.code == 1){
						alert("删除成功");
						window.location.href=contextPath+"/portal/get";
					}
				},
				error:function(){
					alert("删除失败");
					
				}
				
				
			});
           }
	   },
	   useOrNoUse : function(){
		   
		   $(".list_table").on("click","a[name='status']",function(){
			   var urlId = $(this).attr("urlId");
			   var status = $(this).attr("urlStatus");
			   $.ajax({
					url:contextPath+'/portal/changeStatus',
					type:'POST',
					dataType : 'json',	
					data:{"id":urlId,"status":status},
					success:function(data){
						console.info(data);
						if(data.code == 1){
							alert("操作成功");
							window.location.href=contextPath+"/portal/get";
						}
					},
					error:function(){
						alert("操作失败");
						
					}
					
					
				});
		   });
	   },
	   page : function(){
		    $(".list_table").on("click",".pageNumber", function(){
		    	var urlAddress = $("#search_name").val();
		    	var pageNo = $(this).attr("pageNo");
		    	var pageSize = 10;
		    	//queryListForPageDetail(pageNo);
				   $.ajax({
						url:contextPath+'/portal/getPortTable',
						type:'POST',
						dataType : 'html',	
						data:{"urlAddress":urlAddress,"pageNo":pageNo},
						success:function(data){
							console.info(data);
							var bool = data.indexOf("sessionTimeOut");
							if(bool < 0){
								$(".list_table").empty();
								$(".list_table").append(data);
								allPort.service.checkAndNoCheck();
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
	   search : function(){
		   
		    	var port = $("#search_name").val();
		    	$.ajax({
						url:contextPath+'/portal/getPortTable',
						type:'POST',
						dataType : 'html',	
						data:{"port":port},
						success:function(data){
							console.info(data);
							var bool = data.indexOf("sessionTimeOut");
							if(bool < 0){
								$(".list_table").empty();
								$(".list_table").append(data);
								allPort.service.checkAndNoCheck();
							}else{
								window.location.href=contextPath+"/home";
							}
							
						},
						error:function(){
							alert("操作失败");
							
						}
						
						
					});
		   
	   },
	   editUrl : function(){
		   $(".list_table").on("click","a[name='edit_table']",function(){
			   var urlId = $(this).attr("urlId");
			   $.ajax({
					url:contextPath+'/portal/getById',
					type:'POST',
					dataType : 'json',	
					data:{"id":urlId},
					success:function(data){
						if(data.code == 1){
							console.info(data);
							$("#hiddenUrlId").val(data.attach.id);
							$("#address").val(data.attach.port).attr("disabled",true);
							$("#monitorType").val(data.attach.monitorType);
							$("#urlFrc").val(data.attach.frequency);
							$("#time_number").val(data.attach.overtimes);
							
							$("input[name='alarmWay']:radio[value="+data.attach.alarmMethod+"]").prop("checked",true);
								
							
							
						}
						
					},
					error:function(){
						//alert("操作失败");
						
					}
					
					
				});
		   });
		   
		   
	   },
	   editUrlSubmit : function(){
		   $(".list_table").on("click","#modal_btn_submit",function(){
				var id = $("#hiddenUrlId").val();;
				var urlAddress = $("#address").val();
				var accFre = $("#urlFrc").val();
				var monitorType =$("#monitorType").val();
				var timeOutNum = $("#time_number").val();
				if($("#inlineCheckbox1").is(":checked")){
					var checkedLength = $("input[name='alarmWay']:checked").length; 
					if(checkedLength > 1){
						alert("报警方式中不报警不能和其他同时选中");
						return false;
					}
				}
				var alarmWay = allPort.service.getCheckedAlarm();
//				if(content.key == ""){
//					alert("请返回第一步填写必选项");
//					return false;
//				}
	
				$.ajax({
					url:contextPath+'/portal/saveUpdate',
					type:'POST',
					dataType : 'json',	
					data:{'id':id,
						'port':urlAddress,
						'frequency':accFre,
						'overtimes':timeOutNum,
						'alarmMethod':alarmWay,
						'monitorType':monitorType
					},
					success:function(data){
						if(data.code == 1){
							//console.info(data.attach);
							alert("修改成功");
							window.location.href =contextPath+"/portal/get";
						}
//						$(".content-wrapper").empty();
//						$(".content-wrapper").append(data);
					},
					error:function(){
						alert("操作失败");
						
					}
					
					
				});
		   });
		   
		   
	   }
   }
};