$(function(){
	networkMonitorHistoryData.service.init();
	$("#historyDataa").click(function(){
		window.location.reload();
	});
	
});


var networkMonitorHistoryData = {
		service : {
			init : function(){
				networkMonitorHistoryData.service.initHistoryTable();
				networkMonitorHistoryData.service.page();
				networkMonitorHistoryData.controller.sensorHistoryChartAjax();
			},
			initHistoryTable : function(){
				networkMonitorHistoryData.controller.page(1);
			},
			createChartsDetail : function(data){
				console.info(data);
				var sensorType=$("#sensorType").val();
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
				$('#hostoryDataGraph').highcharts(chart);
				
				
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
				$('#hostoryDataGraph').highcharts(chart2);
				
			},
			page : function(){
			    $("#hostoryDataTable").on("click",".pageNumber", function(){
			    	var pageNo = $(this).attr("pageNo");
			    	networkMonitorHistoryData.controller.page(pageNo);
			    });
			},
			setSensoVal : function(commuAVG,commuTotal,cpuMemVal,haultTime,normalTime,normalTimePercent,haultTimePercent){
				$("#commuAVG").text(commuAVG+" KB/秒");
				$("#commuTotal").text(commuTotal+" KB");
				$("#cpuMemVal").text(cpuMemVal+" %");
				$("#haultTime").text("【"+haultTime+"】");
				$("#normalTime").text("【"+normalTime+"】");
				$("#normalTimePercent").text(normalTimePercent+" %");
				$("#haultTimePercent").text(haultTimePercent+" %");
			}
			
		},
		controller : {			
			page : function(pageNo){
		    	var sensorType=$("#sensorType").val();
		    	var sensorId = $("#sensorId").val();
		    	var sdate = $("#startDate").val();
				var edate = $("#endDate").val();
				var avg = $("#avgDate").val();
				var pageSize = 10;
				
			    $.ajax({
					url:contextPath+'/prtg/sensor/start/history/table',
					type:'POST',
					dataType : 'html',	
					data:{"sensorId":sensorId,"type":sensorType,"sdate":sdate,"edate":edate,"avg":avg,"pageNo":pageNo,"pageSize":pageSize},
					success:function(data){
						//console.info(data);
						var bool = data.indexOf("sessionTimeOut") > 0 ? true : false;
						var bool2 = data.indexOf("httpcode429") > 0 ? true : false;
						if(bool2){
							$("#hostoryDataTable").empty();
							alert("服务器繁忙，请稍后重试");
							return false;
						}
						if(!bool){
							$("#hostoryDataTable").empty();
							$("#hostoryDataTable").append(data);
						}else{
							window.location.href=contextPath+"/home";
						}
						
					},
					error:function(){
						//alert("操作失败");
						
					}
				});
			   
		   },
		   sensorHistoryChartAjax : function(){//图表
		    	var sensorType=$("#sensorType").val();
		    	var sensorId = $("#sensorId").val();
		    	var sdate = $("#startDate").val();
				var edate = $("#endDate").val();
				var avgChartDate = $("#avgChartDate").val();
				$.ajax({
					url:contextPath+'/prtg/getStartSensorHistoryChart',
					type:'POST',
					dataType : 'json',	
					data:{"sensorId":sensorId,"type":sensorType,"sdate":sdate,"edate":edate,"avgChart":avgChartDate},
					success:function(data){
						//console.info(data);
						if(data.code == 1){
							if(data.attach.list == '429'){
								var list = [];
								networkMonitorHistoryData.service.createChartsDetail2(list);
								alert("服务器繁忙，请稍后重试");
								return false;
							}
							networkMonitorHistoryData.service.createChartsDetail2(data.attach.list);
							networkMonitorHistoryData.service.setSensoVal(data.attach.commuAVG,data.attach.commuTotal,data.attach.cpuMemVal,data.attach.haultTime,data.attach.normalTime,data.attach.normalTimePercent,data.attach.haultTimePercent);
						}
					},
					error:function(){
						//alert("操作失败");
					}
					
					
				});
			},
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
            text: '()'
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
        	 shared: true
           // valueSuffix: 'kb/秒'
        },
        series: [{
            name: 'ump',
			color:'#9c8ade',
            data: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
        }]
    }
