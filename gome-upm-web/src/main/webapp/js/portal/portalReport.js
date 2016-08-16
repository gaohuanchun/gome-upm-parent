$(function(){
   portalHistory.service.init();
   $("#port_history_btn").click(function(){
	   portalHistory.controller.searchUrlHistory();
	});
   
   $("#alarm_search_btn").click(function(){
	  portalHistory.controller.searchAlarmHistory();
	});
});



var portalHistory = {
		service : {
			init : function(){
				//portalHistory.service.timedChart(5*60*1000);
				portalHistory.service.setUpTime();
				portalHistory.controller.pageAlarmHistory();
				portalHistory.controller.pageUrlHistory();
			},
			createCharts : function(){
				portalHistory.controller.createCharts();
			},
			timedChart : function(millisec){
//				var frc = $("#timedChart").val();
//				if(frc == ""){
//					millisec = 5000;
//				}else{
//					millisec = frc*60*1000;
//				}
				portalHistory.service.createCharts();
				self.setInterval("portalHistory.service.createCharts()", millisec)
				
			},
			endTime : function(num){
				//var nowDate = new Date().format("yyyy-MM-dd hh:mm:ss");
				var myDate = new Date();
				myDate.setDate(myDate.getDate()+num);
				var nowDate = myDate.format("yyyy-MM-dd")+" 23:59:59"; 
				return nowDate; 
			},
			dealedTime : function (num){
				var myDate = new Date();
				myDate.setDate(myDate.getDate()+num);
				var formatDate = myDate.format("yyyy-MM-dd") + " 00:00:00"; 
				return formatDate;
			},
			setUpTime : function(){
				$(".setUpTime").each(function(){
					$(this).click(function(){
						var time = $(this).attr("time");
						var endTime = portalHistory.service.endTime(0);
						var startTime = portalHistory.service.dealedTime(-parseInt(time));
						if(time == "1"){
							endTime = portalHistory.service.endTime(-1);
						}
						if($("#home").is(":hidden")){
							$("#alarmStartTime").val(startTime);
							$("#alarmEndTime").val(endTime);
						}else{
							$("#urlStartTime").val(startTime);
							$("#urlEndTime").val(endTime);	
						}
					});

				});
			}
		},
		controller : {
			searchUrlHistory : function(){
				var urlId = $("#portId").val();
				var urlStartTime = $("#portStartTime").val();
				var endTime = $("#portEndTime").val();
				if(urlStartTime==''&&endTime!=''){
					alert("请输入开始时间!");
					$("#portStartTime").focus();
					$("#portStartTime").click(function(){
						alert(234);
					});
					return ;
				}
				if(urlStartTime!=''&&endTime==''){
					alert("请输入结束时间!");
					$("#portEndTime").focus();
					return ;
				}
				if(urlStartTime!=''&&endTime!=''){
					var start=new Date(urlStartTime.replace("-", "/").replace("-", "/"));   
				    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
				    if(end<start){  
				    	alert("开始时间不能晚于结束时间!");
				    	$("#portStartTime").focus();
						return ; 
				    }  
					
				}
				var urlSelectSurvival = $("#urlSelectSurvival").val();
				$.ajax({
					url:contextPath+'/portal/portHistoryTable',
					type:'get',
					dataType : 'html',	
					data:{'id':urlId,"startTime":urlStartTime,"endTime":endTime,"surival":urlSelectSurvival},
					success:function(data){
						
						$("#url_list_table").empty();
						$("#url_list_table").append(data);
						

					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			},
			searchAlarmHistory : function(){
				var urlId = $("#urlId").val();
				var startTime = $("#alarmStartTime").val();
				var endTime = $("#alarmEndTime").val();
				var alarmContent = $("#alarmContext").val();
				var alarmLevel = $("#alarmLevel").val();
				$.ajax({
					url:contextPath+'/url/alarmHistoryTable',
					type:'POST',
					dataType : 'html',	
					data:{"startTime":startTime,"endTime":endTime,"content":alarmContent,"id":urlId},
					success:function(data){
						$("#alarm_list_table").empty();
						$("#alarm_list_table").append(data);
					},
					error:function(){
						alert("操作失败");
					}
					
					
				});
			},
			createCharts : function(){
				var portId = $("#portId").val();
				$.ajax({
					url:contextPath+'/portal/refreshChart',
					type:'POST',
					dataType : 'json',	
					data:{'id':portId},
					success:function(data){
						console.info(data);
						if(data.code == 1){
							chart.xAxis.categories=data.attach.xAxis;
							chart.series=data.attach.data;
							//chart.title.text=data.attach.title;
							$('#portContainer').highcharts(chart);
							//alert("refresh success");
						}
					},
					error:function(){
						//alert("操作失败");
					}
					
					
				});
			},
			pageUrlHistory : function(){
				    $("#url_list_table").on("click",".pageNumber", function(){
				    	var urlId = $("#portId").val();
				    	var startTime = $("#hiddenUrlStartTime").val();
				    	var endTime = $("#hiddenUrlEndTime").val();
				    	var survival = $("#hiddenUrlSurvival").val();
				    	var pageNo = $(this).attr("pageNo");
				    	//console.info(survival);
				    	//var pageSize = 10;
				    	//queryListForPageDetail(pageNo);
						   $.ajax({
								url:contextPath+'/portal/portHistoryTable',
								type:'POST',
								dataType : 'html',	
								data:{'id':urlId,"startTime":startTime,"endTime":endTime,"surival":survival,"pageNo":pageNo},
								success:function(data){
									//console.info(data);
									var bool = data.indexOf("sessionTimeOut");
									if(bool < 0){
										$("#url_list_table").empty();
										$("#url_list_table").append(data);
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
				pageAlarmHistory : function(){
				    $("#alarm_list_table").on("click",".pageNumber", function(){
				    	var urlId = $("#urlId").val();
				    	var startTime = $("#hiddenAlarmStartTime").val();
				    	var endTime = $("#hiddenAlarmEndTime").val();
				    	var alarmContent = $("#hiddenAlarmContent").val();
				    	var pageNo = $(this).attr("pageNo");
				    	var pageSize = 10;
				    	$.ajax({
								url:contextPath+'/url/alarmHistoryTable',
								type:'POST',
								dataType : 'html',	
								data:{"startTime":startTime,"endTime":endTime,"content":alarmContent,"pageNo":pageNo,"id":urlId},
								success:function(data){
									console.info(data);
									var bool = data.indexOf("sessionTimeOut");
									if(bool < 0){
										$("#alarm_list_table").empty();
										$("#alarm_list_table").append(data);
									}else{
										window.location.href=contextPath+"/home";
									}
									
								},
								error:function(){
									alert("操作失败");
									
								}
								
								
							});
				    });
			   }
		}
};





var chart = {
        title: {
            text: '端口存活监控实时展现(该图标每5分钟刷新一次)'
        },
        credits:{
            enabled:false
        },
        xAxis: {
            categories: ['14:31', '14:45', '15:00', '15:15', '15:30', '15:45','16:00', '16:15', '16:30', '16:45']
        },
        yAxis: {
            title: {
                text: '访问耗时 (ms)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: 'ms'
        },
        series: [{
            name: 'ump',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3]
        }]
};


