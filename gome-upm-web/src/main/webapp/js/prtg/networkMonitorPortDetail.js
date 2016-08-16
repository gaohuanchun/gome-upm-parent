$(function(){
	$('.backBtn').click(function(){
		window.history.back();
		//window.location.href=contextPath+"/prtg/device/all";
	});
	$('.cancelBtn').click(function(){
		$(".sensorDetails").click();
		//window.location.href=contextPath+"/prtg/device/all";
	});
	
	$('.tabSensorHistoryPage').click(function(){
		var date = new Date();
		var edate = date.format("yyyy-MM-dd hh:mm:ss");
		 date.setDate(date.getDate()-1);
		var sdate = date.format("yyyy-MM-dd hh:mm:ss");
		$('#onstart').val(sdate);
		$('#onstop').val(edate);
	});
	
	networkMonitorPortDetail.service.init();
	//self.setInterval("networkMonitorPortDetail.service.timedRefreshChart()", 5000);
});

var networkMonitorPortDetail = {
		service : {
			init : function(){
				networkMonitorPortDetail.service.tabHistoryClick();
				networkMonitorPortDetail.service.page();
				networkMonitorPortDetail.service.timedRefreshChart();
				networkMonitorPortDetail.service.tabHistoryDataDateSet();
				networkMonitorPortDetail.service.startHistoryClick();
			},
			tabHistoryDataDateSet : function(){
				$(":radio[name='inlineRadioOptions']").click(function(){
					var sdate = "";
					var edate = "";
					var val = $(this).val();
					//console.info("val:"+val);
					switch(val){
						case "oneDay" : 
							var date = new Date();
							edate = date.format("yyyy-MM-dd hh:mm:ss");
							date.setDate(date.getDate()-1);
							sdate = date.format("yyyy-MM-dd hh:mm:ss");
							break;
						case "twoDay" : 
							var date = new Date();
							edate = date.format("yyyy-MM-dd hh:mm:ss");
							date.setDate(date.getDate()-2);
							sdate = date.format("yyyy-MM-dd hh:mm:ss");
							break;
						case "sevenDay" : 
							var date = new Date();
							edate = date.format("yyyy-MM-dd hh:mm:ss");
							date.setDate(date.getDate()-7);
							sdate = date.format("yyyy-MM-dd hh:mm:ss");
							break;
						case "fourteenDay" : 
							var date = new Date();
							edate = date.format("yyyy-MM-dd hh:mm:ss");
							date.setDate(date.getDate()-14);
							sdate = date.format("yyyy-MM-dd hh:mm:ss");
							break;
						case "today" : 
							var date = new Date();
							sdate = date.format("yyyy-MM-dd"+" 00:00:00");
							edate = date.format("yyyy-MM-dd"+" 23:59:59");;
							break;
						case "yesterday" : 
							var date = new Date();
							date.setDate(date.getDate()-1);
							sdate = date.format("yyyy-MM-dd"+" 00:00:00");
							edate = date.format("yyyy-MM-dd"+" 23:59:59");;
							break;
						case "lastWeekMon" : 
							var date = new Date();
							date.setDate(date.getDate()-date.getDay());
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							date.setDate(date.getDate()-6);
							sdate = date.format("yyyy-MM-dd"+" 00:00:00");;
							break;
						case "lastWeekSun" : 
							var date = new Date();
							date.setDate(date.getDate()-date.getDay()-1);
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							date.setDate(date.getDate()-6);
							sdate = date.format("yyyy-MM-dd"+" 00:00:00");;
							break;
						case "lastMonth" : 
							var date = new Date();
							date.setDate(0);
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							sdate = date.format("yyyy-MM"+"-01 00:00:00");
							break;
						case "twoMonth" : 
							var date = new Date();
							date.setDate(0);
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							date.setMonth(date.getMonth()-1);
							sdate = date.format("yyyy-MM"+"-01 00:00:00");
							break;
						case "sixMonth" : 
							var date = new Date();
							date.setDate(0);
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							date.setMonth(date.getMonth()-5);
							sdate = date.format("yyyy-MM"+"-01 00:00:00");
							break;
						case "twelveMonth" : 
							var date = new Date();
							date.setDate(0);
							edate = date.format("yyyy-MM-dd"+" 23:59:59");
							date.setMonth(date.getMonth()-11);
							sdate = date.format("yyyy-MM"+"-01 00:00:00");
							break;
						default :
							break;
					}
					
					$("#onstart").val(sdate);
					$("#onstop").val(edate);
				});
			},
			startHistoryClick : function(){
				$("#startActive").click(function(){
					var sdate = $("#onstart").val();
					var edate = $("#onstop").val();
					var avg = $("#selectAvg").val();
					var deviceId = $("#deviceId").val();
					var sensorId = $("#sensorId").val();
					if(sdate == "" || edate == ""){
						alert("请选择启动停止时间");
						return false;
					}
					var beginDate = new Date(sdate.replace(/-/g,"/"));
					var endDate = new Date(edate.replace(/-/g,"/"));
					if(beginDate >= endDate){
						alert("停止时间必须大于启动时间");
						return false;
					}
					//sdate = sdate.replace(new RegExp(" |:","g"),"-");
					//edate = edate.replace(new RegExp(" |:","g"),"-");
					window.location.href=contextPath+"/prtg/sensor/detail/history?sensorId="+sensorId+"&deviceId="+deviceId+"&sdate="+sdate+"&edate="+edate+"&avg="+avg;
				});

			},
			initSceneDataCharts : function(){
				$('#sceneDataContainer').highcharts(sceneDataChart);
			},
			timedRefreshChart : function(){
				var tabDate = $("#tabDate").val();
				if(tabDate == "sceneData"){
					console.info("5s刷新");
					networkMonitorPortDetail.service.createChart();
				}
				
			},
			tabHistoryClick : function(){
				$(".tabSensorHistory").click(function(){
					var sensorId = $("#sensorId").val();
					var sensorType=$("#sensorType").val();
					var ariaController = $(this).attr("aria-controls");
					$("#tabDate").val(ariaController);
			    	console.info("sensorType:"+sensorType+"|tabDate:"+ariaController);
			    	networkMonitorPortDetail.service.createChart();
			    	networkMonitorPortDetail.controller.page(sensorId,sensorType,ariaController,1,10,"tab");
					});
			},
			btnDetailBack : function(){
				$('.btnDetailBack').click(function(){
					var deviceId = $(this).attr("deviceId");
					window.location.href=contextPath+"/prtg/device/detail?deviceId="+deviceId;
				});
			},
			createChart : function(){
					var sensorType = $("#sensorType").val();
					var sensorId = $("#sensorId").val();
					var tabDate = $("#tabDate").val();
					networkMonitorPortDetail.controller.sensorHistoryChartAjax(sensorId, sensorType, tabDate);
			},
			createChartsDetail : function(data){
			//	console.info(data);
				var sensorType = $("#sensorType").val();
				var sensorName = $("#sensorName").val();
				var categories = [];
				var series = [];
				var tingji = {};
				var tongxinliangheji = {};
				var tongxinlianghejiData = [];
				tongxinliangheji.name="通信量合计";
				tongxinliangheji.color="#9c8ade";
				var ruzhantongxinliang = {};
				var ruzhantongxinliangData = [];
				ruzhantongxinliang.name="入站通信量";
				ruzhantongxinliang.color="#9c8ade";
				var chuzhantongxinliang = {};
				var chuzhantongxinliangData = [];
				chuzhantongxinliang.name="出站通信量";
				chuzhantongxinliang.color="#9c8ade";
				var zhiother = {};
				var zhiotherData = [];
				zhiother.name="值";
				zhiother.color="#9c8ade";
				var tongjishijian = {};
				var tongjishijianData = [];
				tongjishijian.name="停机时间";
				tongjishijian.color="#9c8ade";
				for(var foo in data){
					var hisData = data[foo];
					var communication_roll = hisData.communication_roll ;
					
					if(communication_roll != null && communication_roll !="" && communication_roll.indexOf("<") == -1){
						communication_roll = communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						communication_roll = "0";
					}
					
					var in_communication_roll = hisData.in_communication_roll;
					if(in_communication_roll != null && in_communication_roll !="" && in_communication_roll.indexOf("<") == -1){
						in_communication_roll = in_communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						in_communication_roll = "0";
					}
					var out_communication_roll = hisData.out_communication_roll;
					if(out_communication_roll != null && out_communication_roll !="" && out_communication_roll.indexOf("<") == -1){
						out_communication_roll = out_communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						out_communication_roll = "0";
					}
					var cpuMemVal = hisData.cpuMemVal;
					if(cpuMemVal != null && cpuMemVal !="" && cpuMemVal.indexOf("<") == -1){
						cpuMemVal = cpuMemVal.split(" ")[0].replace(/,/g,"");
					}else{
						cpuMemVal = "0";
					}
					var halt_time = hisData.halt_time;
					//console.info("halt_time1:"+halt_time);
					if(halt_time != null && halt_time !="" && halt_time.indexOf("<") == -1){
						halt_time = halt_time.split(" ")[0].replace(/,/g,"");
					}else{
						halt_time = "0";
					}
				//	console.info("halt_time2:"+halt_time);
					categories.push(hisData.datetime);
					tongxinlianghejiData.push(parseFloat(communication_roll));
					ruzhantongxinliangData.push(parseFloat(in_communication_roll));
					chuzhantongxinliangData.push(parseFloat(out_communication_roll));
					zhiotherData.push(parseFloat(cpuMemVal));
					tongjishijianData.push(parseFloat(halt_time));
				}
				tongxinliangheji.data=tongxinlianghejiData;
				ruzhantongxinliang.data=ruzhantongxinliangData;
				chuzhantongxinliang.data=chuzhantongxinliangData;
				zhiother.data=zhiotherData;
				tongjishijian.data=tongjishijianData;
				series.push(tongjishijian);
				if(sensorType == "flow"){
					series.push(tongxinliangheji);
					series.push(ruzhantongxinliang);
					series.push(chuzhantongxinliang);
					chart.yAxis.title.text=' kb/秒';
					chart.tooltip.valueSuffix=' kb/秒';
				}else{
					series.push(zhiother);
					chart.yAxis.title.text=' %';
					chart.tooltip.valueSuffix=' %';
				}
				chart.xAxis.categories=categories;
				chart.series=series;
				chart.title.text=sensorName;
				console.info(JSON.stringify(chart));
				$('#sceneDataContainer').highcharts(chart);
				
			},
			createChartsDetail2 : function(data){
			//	console.info(data);
				var tooltippercent = {valueSuffix: ' %'};
				var tooltipkb = {valueSuffix: ' kb/秒'};
				var sensorType = $("#sensorType").val();
				var sensorName = $("#sensorName").val();
				var categories = [];
				var series = [];
				var tingji = {};
				var tongxinliangheji = {};
				var tongxinlianghejiData = [];
				tongxinliangheji.name="通信量合计";
				tongxinliangheji.color="#9c8ade";
				var ruzhantongxinliang = {};
				var ruzhantongxinliangData = [];
				ruzhantongxinliang.name="入站通信量";
				ruzhantongxinliang.color="#9c8ade";
				var chuzhantongxinliang = {};
				var chuzhantongxinliangData = [];
				chuzhantongxinliang.name="出站通信量";
				chuzhantongxinliang.color="#9c8ade";
				var zhiother = {};
				var zhiotherData = [];
				zhiother.name="值";
				zhiother.color="#9c8ade";
				var tongjishijian = {};
				var tongjishijianData = [];
				tongjishijian.name="停机时间";
				tongjishijian.color="#FF0000";
				for(var foo in data){
					var hisData = data[foo];
					var communication_roll = hisData.communication_roll ;
					
					if(communication_roll != null && communication_roll !="" && communication_roll.indexOf("<") == -1){
						communication_roll = communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						communication_roll = "0";
					}
					
					var in_communication_roll = hisData.in_communication_roll;
					if(in_communication_roll != null && in_communication_roll !="" && in_communication_roll.indexOf("<") == -1){
						in_communication_roll = in_communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						in_communication_roll = "0";
					}
					var out_communication_roll = hisData.out_communication_roll;
					if(out_communication_roll != null && out_communication_roll !="" && out_communication_roll.indexOf("<") == -1){
						out_communication_roll = out_communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						out_communication_roll = "0";
					}
					var cpuMemVal = hisData.cpuMemVal;
					if(cpuMemVal != null && cpuMemVal !="" && cpuMemVal.indexOf("<") == -1){
						cpuMemVal = cpuMemVal.split(" ")[0].replace(/,/g,"");
					}else{
						cpuMemVal = "0";
					}
					var halt_time = hisData.halt_time;
					//console.info("halt_time1:"+halt_time);
					if(halt_time != null && halt_time !="" && halt_time.indexOf("<") == -1){
						halt_time = halt_time.split(" ")[0].replace(/,/g,"");
					}else{
						halt_time = "0";
					}
				//	console.info("halt_time2:"+halt_time);
					categories.push(hisData.datetime);
					tongxinlianghejiData.push(parseFloat(communication_roll));
					ruzhantongxinliangData.push(parseFloat(in_communication_roll));
					chuzhantongxinliangData.push(parseFloat(out_communication_roll));
					zhiotherData.push(parseFloat(cpuMemVal));
					tongjishijianData.push(parseFloat(halt_time));
				}
				tongxinliangheji.data=tongxinlianghejiData;
				tongxinliangheji.tooltip=tooltipkb;
				ruzhantongxinliang.data=ruzhantongxinliangData;
				ruzhantongxinliang.tooltip=tooltipkb;
				chuzhantongxinliang.data=chuzhantongxinliangData;
				chuzhantongxinliang.tooltip=tooltipkb;
				zhiother.data=zhiotherData;
				zhiother.tooltip=tooltippercent;
				tongjishijian.data=tongjishijianData;
				tongjishijian.tooltip=tooltippercent;
				tongjishijian.yAxis=1;
				series.push(tongjishijian);
				if(sensorType == "flow"){
					series.push(tongxinliangheji);
					series.push(ruzhantongxinliang);
					series.push(chuzhantongxinliang);
					//chart.yAxis.title.text=' kb/秒';
				//	chart.tooltip.valueSuffix=' kb/秒';
				}else{
					series.push(zhiother);
					//chart.yAxis.title.text=' %';
					//chart.tooltip.valueSuffix=' %';
				}
				chart2.xAxis.categories=categories;
				chart2.series=series;
				chart2.title.text=sensorName;
				console.info(JSON.stringify(chart2));
				$('#sceneDataContainer').highcharts(chart2);
				
			},
			page : function(){
			    $("#sceneData").on("click",".pageNumber", function(){
			    	var sensorId = $("#sensorId").val();
			    	var sensorType=$("#sensorType").val();
			    	var tabDate = $("#tabDate").val();
			    	var pageNo = $(this).attr("pageNo");
			    	var pageSize = 10;
			    	console.info("sensorType:"+sensorType+"|tabDate:"+tabDate+"|pageNo:"+pageNo+"|pageSize:"+pageSize);
			    	networkMonitorPortDetail.controller.page(sensorId,sensorType,tabDate,pageNo,pageSize,"page");
			    });
			}

		},
		controller : {
			sensorHistoryChartAjax : function(sensorId, sensorType, tabDate){//图表
				$.ajax({
					url:contextPath+'/prtg/getSensorHistoryChart',
					type:'POST',
					dataType : 'json',	
					data:{"sensorId":sensorId,"type":sensorType,"tabDate":tabDate},
					success:function(data){
						//console.info(data);
						if(data.code == 1){
							if(data.attach == '429'){
								var attach = [];
								networkMonitorPortDetail.service.createChartsDetail2(attach);
								alert("服务器繁忙，请稍后重试");
								return false;
							}
							networkMonitorPortDetail.service.createChartsDetail2(data.attach);
						}
					},
					error:function(){
						//alert("操作失败");
					}
					
					
				});
			},
			page : function(sensorId,type,date,pageNo,pageSize,type2){
				    $.ajax({
						//url:contextPath+'/prtg/sensor/history/table',
				    	url:contextPath+'/prtg/sensor/history/table2',
						type:'POST',
						dataType : 'html',	
						data:{"sensorId":sensorId,"type":type,"date":date,"pageNo":pageNo,"pageSize":pageSize},
						success:function(data){
							//console.info(data);
							var bool = data.indexOf("sessionTimeOut") > 0 ? true : false;
							var bool2 = data.indexOf("httpcode429") > 0 ? true : false;
							if(bool2){
								if(type2 == "tab"){
									$(".list_table_history").empty();
									alert("服务器繁忙，请稍后重试");
									return false;
									//$(".list_table_history").append(data);
								}
								if(type2 == "page"){
									alert("服务器繁忙，请稍后重试");
									return false;	
								}

							}
							if(!bool){
								$(".list_table_history").empty();
								$(".list_table_history").append(data);
							}else{
								window.location.href=contextPath+"/home";
							}
							
						},
						error:function(){
							//alert("操作失败");
							
						}
					});
				   
			   }
		}
};


var chart = {
		chart: {
            backgroundColor:"#FFF"
        },
        title: {
            text: '(004) lsi'
        },
        credits:{
            enabled:false
        },
        xAxis: {
            categories: ['00:00', '00:00', '00:00', '00:00', '00:00', '00:00','00:00', '00:00', '00:00', '00:00', '00:00', '00:00']
        },
        yAxis: {
            title: {
                text: 'kb/秒'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: 'kb/秒'
        },
        series: [{
            name: 'ump',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        }]
    }



var chart2 = {
		chart: {
            backgroundColor:"#FFF"
        },
        title: {
            text: '(004) lsi'
        },
        credits:{
            enabled:false
        },
        xAxis: {
            categories: ['00:00', '00:00', '00:00', '00:00', '00:00', '00:00','00:00', '00:00', '00:00', '00:00', '00:00', '00:00']
        },
        yAxis: [{           
        	labels: {
        		format: '{value} kb/秒',
	            style: {
	                color: '#9c8ade'
	            }
        	},
            title: {
                text: ' kb/秒',	           
                style: {
	                color: '#9c8ade'
	            }
            }
        },{           
        	labels: {
        		format: '{value} %',
	            style: {
	                color: '#FF0000'
	            }
        	},
            title: {
                text: ' %',	           
                style: {
	                color: '#FF0000'
	            }
            },
            opposite: true
        }],
        legend: {
            enabled: false
        },
        tooltip: {
        	 borderColor: '#9c8ade',
        	 shared: true
           // valueSuffix: 'kb/秒'
        },
        series: [{
            name: 'ump',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        }]
    }