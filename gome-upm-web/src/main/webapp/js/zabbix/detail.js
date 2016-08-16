$(function(){
	var hostid = $('#hostid').val();
	$('#cpu_use').highcharts(cpuChart);
	$('#memory').highcharts(memoryChart);
	$('#system_load').highcharts(loadChart);
	$('#IO_read_speed').highcharts(readChart);
	$('#IO_write_speed').highcharts(writeChart);
	$('#disk_use').highcharts(dealDiskUseChart);
	$('#disk').highcharts(diskChart);
	networkMonitor.controller.memory(hostid);
	networkMonitor.controller.cpu(hostid);
	networkMonitor.controller.load(hostid);
	networkMonitor.controller.diasUse(hostid);
	networkMonitor.controller.diasTotal(hostid);
	networkMonitor.controller.IOReadSpeed(hostid);
	networkMonitor.controller.IOWriteSpeed(hostid);
	queryTrigger(hostid);
});
function queryTrigger(hostid){
	$.ajax({
		url:'../server/queryTrigger',
		type:'POST',
		dataType:'html',
		data:{
			hostid:hostid
		},
		success:function(data){
			$(".table-area").empty();
			$(".table-area").append(data);
			$('.hoverTrigger').each(function(i,k){
				$(k).hover(function(){
					$(k).parent().next().children('.itemsDetails').find("#trigger").empty();
					var itemId = $(k).siblings("#itemid").text();
					$.ajax({
					url:'../server/getTriggerByItemId',
					type:'POST',
					dataType : 'json',	
					data:{itemId:itemId},
					success:function(data){
						if(data.code == 1){
							var trigger = '';
							for(var i=0;i<data.attach.length;i++){
								trigger = trigger+data.attach[i];
							}
							$(k).parent().next().children('.itemsDetails').find("#trigger").append(trigger);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
				$(k).parent().next().children('.itemsDetails').show();
				},function(){
					$(k).parent().next().children('.itemsDetails').hide();
					$(k).parent().next().children('.itemsDetails').find("#trigger").empty();
				});
			});
		},
		error:function(){
			$("#tishi").text("系统异常");
			$("#mod-dialog").show();
	  		$("#mod-dialog-bg").show();
	  		$("#altbtn").unbind("click");
	  		$("#altbtn").bind("click",function(){
				 $("#mod-dialog").hide();
				 $("#mod-dialog-bg").hide();
			});
		}
		
	});
}
var categoryLinks = {};

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
				if(data==null || data==''){
					seriesData=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
				}
				for(var top in data){
					seriesData.push(parseFloat(data[top].value));
					category.push(data[top].clock);
				}
				cpuChart.categoryLinks=categoryLinks;
				cpuChart.series[0].data=seriesData;
				cpuChart.xAxis.categories=category;
				//console.info(JSON.stringify(cpuChart));
				$('#cpu_use').highcharts(cpuChart);
			},
			dealMemoryChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				if(data==null || data==''){
					seriesData=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
				}
				var t = new Date().getTime();
				for(var timeD in seriesData){
					d=new Date(t-1000*60*60+1000*timeD*60);
					var m = d.getMinutes();
					if(m>0 && m<10){
						m = "0"+m;
					}
					category.push(d.getHours()+":"+m);
				}
				for(var top in data){
					seriesData.push(parseFloat(data[top].value));
					category.push(data[top].clock);
				}
				memoryChart.categoryLinks=categoryLinks;
				memoryChart.series[0].data=seriesData;
				memoryChart.xAxis.categories=category;
				//console.info(JSON.stringify(memoryChart));
				$('#memory').highcharts(memoryChart);
			},
			dealFlowChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				if(data==null || data==''){
					seriesData=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
				}
				var t = new Date().getTime();
				for(var timeD in seriesData){
					d=new Date(t-1000*60*60+1000*timeD*60);
					var m = d.getMinutes();
					if(m>0 && m<10){
						m = "0"+m;
					}
					category.push(d.getHours()+":"+m);
				}
				for(var top in data){
					seriesData.push(parseFloat(data[top].value));
					category.push(data[top].clock);
				}
				loadChart.categoryLinks=categoryLinks;
				loadChart.series[0].data=seriesData;
				loadChart.xAxis.categories=category;
				//console.info(JSON.stringify(loadChart));
				$('#system_load').highcharts(loadChart);
			},
			dealDiskUseChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value));
						category.push(detailArr[detail].clock);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				dealDiskUseChart.categoryLinks=categoryLinks;
				dealDiskUseChart.series = series;
				dealDiskUseChart.xAxis.categories=categoryAll[0];
				//console.info(JSON.stringify(dealDiskUseChart));
				$('#disk_use').highcharts(dealDiskUseChart);
			},
			dealDiskTotalChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value));
						category.push(detailArr[detail].clock);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				diskChart.categoryLinks=categoryLinks;
				diskChart.series = series;
				diskChart.xAxis.categories=categoryAll[0];
				//console.info(JSON.stringify(diskChart));
				$('#disk').highcharts(diskChart);
			},
			ioReadChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value));
						category.push(detailArr[detail].clock);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				readChart.categoryLinks=categoryLinks;
				readChart.series = series;
				readChart.xAxis.categories=categoryAll[0];
				//console.info(JSON.stringify(readChart));
				$('#IO_read_speed').highcharts(readChart);
			},
			ioWriteChart : function(data){
				var categoryLinksCpu = {};
				var categoryAll = [];
				var series = [];
				for(var serverItem in data){
					var category = [];
					var dataV = [];
					var seriesData = {};//柱形图
					var detailArr = data[serverItem].itemDetailList;
					if(detailArr==null || detailArr==''){
						dataV=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
					}
					for(var detail in detailArr){
						dataV.push(parseFloat(detailArr[detail].value));
						category.push(detailArr[detail].clock);
					}
					var t = new Date().getTime();
					for(var timeD in dataV){
						d=new Date(t-1000*60*60+1000*timeD*60);
						var m = d.getMinutes();
						if(m>0 && m<10){
							m = "0"+m;
						}
						category.push(d.getHours()+":"+m);
					}
					seriesData.data=dataV;
					seriesData.name = data[serverItem].name;
					categoryAll.push(category);
					series.push(seriesData);
				}
				writeChart.categoryLinks=categoryLinks;
				writeChart.series = series;
				writeChart.xAxis.categories=categoryAll[0];
				//console.info(JSON.stringify(writeChart));
				$('#IO_write_speed').highcharts(writeChart);
			}
		},
		controller : {
			memory : function(hostid){
				$.ajax({
					url:'../server/getMemoryDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealMemoryChart(data.attach.itemDetailList);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			cpu : function(hostid){
				$.ajax({
					url:'../server/getCpuDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealCpuChart(data.attach.itemDetailList);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			load : function(hostid){
				$.ajax({
					url:'../server/getLoadDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealFlowChart(data.attach.itemDetailList);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			diasUse : function(hostid){
				$.ajax({
					url:'../server/getDiskUseDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealDiskUseChart(data.attach);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			diasTotal : function(hostid){
				$.ajax({
					url:'../server/getDiskTotalDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.dealDiskTotalChart(data.attach);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			IOReadSpeed : function(hostid){
				$.ajax({
					url:'../server/getIOReadDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.ioReadChart(data.attach);
						}
					},
					error:function(){
						alert("操作失败");
					}
				});
			},
			IOWriteSpeed : function(hostid){
				$.ajax({
					url:'../server/getIOWriteDetail',
					type:'POST',
					dataType : 'json',	
					data:{
						hostid:hostid
					},
					success:function(data){
						if(data.code == 1){
							//请求成功
							networkMonitor.service.dealDeviceHealthy(data.attach.errorCount,data.attach.totalCount);
							networkMonitor.service.ioWriteChart(data.attach);
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
		title: {
            text: 'CPU使用率(%)'
        },
        credits:{
			enabled:false
		},
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: '%'
        },
        series: [{
            name: 'CPU使用率',
            color:'#9c8ade',
            data: []
        }]
};
var memoryChart = {
		 title: {
	            text: '内存使用率(%)'
	        },
	        credits:{
				enabled:false
			},
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            valueSuffix: '%'
	        },
	        series: [{
	            name: '内存使用率',
	            color:'#9c8ade',
	            data: []
	        }]
};


var loadChart = {
		 title: {
	            text: '1分钟系统负载'
	        },
	        credits:{
				enabled:false
			},
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            valueSuffix: 'ms'
	        },
	        series: [{
	            name: '1分钟系统负载',
	            color:'#9c8ade',
	            data: []
	        }]
};
var diskChart = {
		 title: {
	            text: '磁盘总量(G)'
	        },
	        credits:{
				enabled:false
			},
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            valueSuffix: 'GB'
	        },
	        series: [{
	            name: 'ump',
	            color:'#9c8ade',
	            data: []
	        }]
};
var dealDiskUseChart = {
		title: {
            text: '磁盘使用率(%)'
        },
        credits:{
			enabled:false
		},
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: '%'
        },
        series: [{
            name: 'ump',
            color:'#9c8ade',
            data: []
        }]
};
var readChart = {
		 title: {
	            text: 'IO读速度(Blk)'
	        },
	        credits:{
				enabled:false
			},
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            valueSuffix: 'Blk'
	        },
	        series: [{
	            name: 'ump',
	            color:'#9c8ade',
	            data: []
	        }]
};
var writeChart = {
		title: {
            text: 'IO写速度(Blk)'
        },
        credits:{
			enabled:false
		},
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            valueSuffix: 'Blk'
        },
        series: [{
            name: 'ump',
            color:'#9c8ade',
            data: []
        }]
};