$(function(){
	$('#cpu_use').highcharts(cpuChart);
	$('#memory_use').highcharts(memoryChart);
	$('#system_load').highcharts(loadChart);
	$('#Instance_memory_use').highcharts(iMemoryChart);
	$('#io_use').highcharts(ioChart);
	$('#disk_use').highcharts(diskChart);
	networkMonitor.controller.memory();
	networkMonitor.controller.cpu();
	networkMonitor.controller.load();
	networkMonitor.controller.io();
	networkMonitor.controller.disk();

});

var categoryLinks = {"10.58.22.252":"/prtg/device/detail?deviceId=2712","10.58.90.78":"/prtg/device/detail?deviceId=4362"};

var networkMonitor = {
		service : {
			dealDeviceHealthy : function(error,total){
				$(".health_status_count span:first-child a").text(error);
				$(".health_status_count span:last-child a").text(error);
			},
			dealCpuChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '/prtg/sensor/detail?sensorId='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				cpuChart.categoryLinks=categoryLinks;
				cpuChart.series[0].data=seriesData;
				cpuChart.xAxis.categories=category;
				console.info(cpuChart);
				$('#cpu_use').highcharts(cpuChart);
			},
			dealMemoryChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '/prtg/sensor/detail?sensorId='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				memoryChart.categoryLinks=categoryLinks;
				memoryChart.series[0].data=seriesData;
				memoryChart.xAxis.categories=category;
				console.info(memoryChart);
				$('#memory_use').highcharts(memoryChart);
			},
			dealFlowChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '/prtg/sensor/detail?sensorId='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				loadChart.categoryLinks=categoryLinks;
				loadChart.series[0].data=seriesData;
				loadChart.xAxis.categories=category;
				console.info(loadChart);
				$('#system_load').highcharts(loadChart);
			},
			dealIOChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '/prtg/sensor/detail?sensorId='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				ioChart.categoryLinks=categoryLinks;
				ioChart.series[0].data=seriesData;
				ioChart.xAxis.categories=category;
				console.info(ioChart);
				$('#io_use').highcharts(ioChart);
			},
			dealDiskChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '/prtg/sensor/detail?sensorId='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				diskChart.categoryLinks=categoryLinks;
				diskChart.series[0].data=seriesData;
				diskChart.xAxis.categories=category;
				console.info(diskChart);
				$('#disk_use').highcharts(diskChart);
			}
		},
		controller : {
			memory : function(){
				$.ajax({
					url:'../server/getMemory',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealMemoryChart(data.attach.memory);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			},
			cpu : function(){
				$.ajax({
					url:'../server/getCpu',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealCpuChart(data.attach.cpu);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			},
			load : function(){
				$.ajax({
					url:'../server/getLoad',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealFlowChart(data.attach.load);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			},
			io : function(){
				$.ajax({
					url:'../server/getIO',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealIOChart(data.attach.io);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			},
			disk : function(){
				$.ajax({
					url:'../server/getDisk',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealDiskChart(data.attach.disk);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			},
			imemory : function(){
				$.ajax({
					url:'../server/getLoad',
					type:'POST',
					dataType : 'json',	
					data:{},
					success:function(data){
						if(data.code == 1){
							//请求成功
							console.info(data.attach);
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealFlowChart(data.attach.load);
						}
					},
					error:function(){
						alert("操作失败");
					}


				});
			}
		}
};



var categoryCpuLinks = {
};

var categoryMemLinks = {
};

var categoryFlowLinks = {
};



var cpuChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: 'CPU使用率TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 },
			            	 formatter: function () {
			            		 return '<a href="' + categoryLinks[this.value] + '">' +
			            		 this.value + '</a>';
			            	 }

			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			},
			series: {
				
				point: {
					events: {
						click: function () {
							location.href = this.options.url;
						}
					}
				}
			}
		},
		series: [{
			name: 'cpu使用率',
			color:'#9c8ade',
			
			data: [0.0, 0.0, 0.0,0.0, 0.0]
		}]
};
var iMemoryChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: '实例内存使用超90%TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 }
			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: '实例内存使用率',
			color:'#9c8ade',
			
			data: [49.9, 71.5, 89.4,93.2, 99.9]
		}]
};
var ioChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: 'IO使用率TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 }
			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: 'IO使用率',
			color:'#9c8ade',
			
			data: [49.9, 71.5, 89.4,93.2, 99.9]
		}]
};
var diskChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: '磁盘使用率使用超90%TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30',
			             '10.22.13.30'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 }
			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: '磁盘使用率',
			color:'#9c8ade',
			
			data: [49.9, 71.5, 89.4,93.2, 99.9]
		}]
};

var memoryChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: '内存使用率TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 }
			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: '内存使用率',
			color:'#9c8ade',
			
			data: [0.0, 0.0, 0.0,0.0, 0.0]
		}]
};


var loadChart = {
		chart: {
			type: 'column'
		},
		title: {
			text: '系统负载TOP5'
		},
		credits:{
			enabled:false // 禁用版权信息
		},
		xAxis: {
			categories: [
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0',
			             '0.0.0.0'
			             ],
			             labels: {
			            	 rotation: -120, 
			            	 style: { 
			            		 fontSize: '13px', 
			            		 fontFamily: 'Verdana, sans-serif',
			            		 writingMode:'tb-rl'    //文字竖排样式
			            	 }
			             }
		},
		yAxis: {
			min: 0,
			title: {
				text: ''
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: '系统负载',
			color:'#9c8ade',
			
			data: [0.0, 0.0, 0.0,0.0, 0.0]
		}]
};