$(function(){
	$('#cpu_use').highcharts(cpuChart);
	$('#memory_use').highcharts(memoryChart);
	$('#minute_load').highcharts(flowChart);
	networkMonitor.controller.memory();
	networkMonitor.controller.cpu();
	networkMonitor.controller.load();
	
});

var categoryLinks = {"10.58.22.252":"../server/goToDetail?hostid=2712","10.58.90.78":"/prtg/device/detail?deviceId=4362"};

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
					yText.url = '../server/goToDetail?hostid='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				cpuChart.categoryLinks=categoryLinks;
				cpuChart.series[0].data=seriesData;
				cpuChart.xAxis.categories=category;
				console.info(cpuChart);
				$('#cpu_use').highcharts(cpuChart);
				console.info("categoryLinks:"+JSON.stringify(categoryLinks));
			},
			dealMemoryChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '../server/goToDetail?hostid='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				memoryChart.categoryLinks=categoryLinks;
				memoryChart.series[0].data=seriesData;
				memoryChart.xAxis.categories=category;
				console.info(memoryChart);
				$('#memory_use').highcharts(memoryChart);
				console.info("categoryLinks:"+JSON.stringify(categoryLinks));
			},
			dealFlowChart : function(data){
				var categoryLinksCpu = {};
				var category = [];
				var seriesData = [];//柱形图
				for(var top in data){
					var yText = {};
					yText.y = parseInt(data[top].lastVal);
					yText.url = '../server/goToDetail?hostid='+data[top].deviceId;
					seriesData.push(yText);
					category.push(data[top].host);
				}
				flowChart.categoryLinks=categoryLinks;
				flowChart.series[0].data=seriesData;
				flowChart.xAxis.categories=category;
				console.info(flowChart);
				$('#minute_load').highcharts(flowChart);
				console.info("categoryLinks:"+JSON.stringify(categoryLinks));
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
                cursor: 'pointer',
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
            cursor: 'pointer',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
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
            },
            series: {
                cursor: 'pointer',
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
            name: '内存使用率',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer',
            color:'#9c8ade',
        }]
};


var flowChart = {
		chart: {
            type: 'column'
        },
        title: {
            text: '1分钟负载TOP5'
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
                '<td style="padding:0"><b>{point.y} </b></td></tr>',
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
                cursor: 'pointer',
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
            name: '1分钟负载',
            data: [0.0, 0.0, 0.0,0.0, 0.0],
            cursor: 'pointer',
            color:'#9c8ade',
        }]
};