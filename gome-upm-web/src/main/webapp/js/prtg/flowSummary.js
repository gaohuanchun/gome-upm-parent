$(function(){
	flowSummary.service.init();
});

var flowSumarySensorIds = "2692,2915,3060,11140,11139,11153,5693,5692,5689,10522";

var flowSummary = {
		service : {
			init : function(){
				//页面初始化charts
				flowSummary.controller.createChart(flowSumarySensorIds,"first");
				flowSummary.service.clickSwitchCharts();
				self.setInterval("flowSummary.service.timedRefreshCharts()",60000)
			},
			clickSwitchCharts : function(){
				$('#rightBtn').on('click',function(){
					$('.leftGraph').hide();
					$('.rightGraph').show();
					flowSummary.controller.createChart(flowSumarySensorIds,"second");
				});
				$('#leftBtn').on('click',function(){
					$('.leftGraph').show();
					$('.rightGraph').hide();
					flowSummary.controller.createChart(flowSumarySensorIds,"first");
				});
			},
			timedRefreshCharts : function(){
				var bool = $("#leftGraph").is(":hidden");
				if(bool){
					flowSummary.controller.createChart(flowSumarySensorIds,"second");
				}else{
					flowSummary.controller.createChart(flowSumarySensorIds,"first");
				}
			},
			commonDealChart : function(data, chart){
				if(data == null || data.length == 0){
					return chart;
				}
				var categories = [];
				var series = [];
				var seriesData = {};
				var seriesDataData = [];
				seriesData.name="通信量合计";
				seriesData.color="#9c8ade";
				for(var i in data){
					var hisData = data[i];
					var dataTime = hisData.datetime;
					categories.push(dataTime.substring(dataTime.indexOf(" ")));
					var communication_roll = hisData.communication_roll ;//合计
					if(communication_roll != null && communication_roll !="" && communication_roll.indexOf("<") == -1){
						communication_roll = communication_roll.split(" ")[0].replace(/,/g,"");
					}else{
						communication_roll = "0";
					}
					//seriesDataData.push(parseFloat((communication_roll/1024).toFixed(1)));
					seriesDataData.push(parseFloat(communication_roll));
				}
				seriesData.data=seriesDataData;
				series.push(seriesData);
				chart.xAxis.categories=categories;
				chart.series=series;
				console.info(chart);
				return chart;
			},
			commonDealChart2 : function(data, chart){
				if(data == null || data.length == 0){
					return chart;
				}
				var categories = [];
				var seriesDataData = [];
				data.reverse();
				for(var i in data){
					var hisData = data[i];
					var dataTime = hisData.dateTime;
					var subs1 = dataTime.substring(dataTime.lastIndexOf(" ")+1,dataTime.lastIndexOf(":"));
					//categories.push(dataTime);
					//console.info(dataTime.substring(dataTime.indexOf(" ")));
					categories.push(subs1);
					var communicationRoll = hisData.communicationRoll ;//合计
					if(communicationRoll != null && communicationRoll !="" && communicationRoll.indexOf("<") == -1){
						communicationRoll = communicationRoll.split(" ")[0].replace(/,/g,"");
					}else{
						communicationRoll = "0";
					}
					//seriesDataData.push(parseFloat((communicationRoll/1024).toFixed(1)));
					seriesDataData.push(parseFloat(communicationRoll));
				}
				chart.xAxis.categories=categories;
				chart.series[0].data=seriesDataData;
				//console.info(chart);
				return chart;
			},
			createM5Chart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, m5Chart);
				$('#m5_flow').highcharts(demoChart);
			},
			createPbsChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, pbsChart);
				$('#pbs_flow').highcharts(demoChart);
			},
			createM5PbsChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, m5PbsChart);
				$('#m5_pbs').highcharts(demoChart);
			},
			createOfficePbsChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, officePbsChart);
				$('#office_area_pbs').highcharts(demoChart);
			},
			createZaPbsChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, zaPbsChart);
				$('#za_pbs').highcharts(demoChart);
			},
			createDcPbsChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, dcPbsChart);
				$('#dc_pbs').highcharts(demoChart);
			},
			createBcmChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, bcmChart);
				$('#bcm_line').highcharts(demoChart);
			},
			createUnionPayChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, unionPayChart);
				$('#unionPay_line').highcharts(demoChart);
			},
			createDbHeartBeatChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, dbHeartBeatChart);
				$('#database_heartbeat').highcharts(demoChart);
			},
			createNoamChart : function(data){
				var demoChart = flowSummary.service.commonDealChart2(data, noamChart);
				$('#noam_line').highcharts(demoChart);
			}
			
		},
		controller : {
			createChart : function(sensorIds,type){
			    var arr = sensorIds.split(",");
			    console.info(arr);
				$.ajax({
					url:contextPath+'/prtg/getFlowSummaryChart',
					type:'POST',
					dataType : 'json',	
					data:{"sensorIds":sensorIds},
					success:function(data){
						if(data.code == 1){
							//请求成功
							//console.info(data.attach);
							//var attach = JSON.parse(data.attach);2692,2915,3060,11140,11139,11153,5693,5692,5689,2689
							var attach = data.attach;
							if(type == "first"){
								flowSummary.service.createM5Chart(attach.chart2692);
								flowSummary.service.createPbsChart(attach.chart2915);
								flowSummary.service.createM5PbsChart(attach.chart3060);
								flowSummary.service.createOfficePbsChart(attach.chart11140);
								flowSummary.service.createZaPbsChart(attach.chart11139);
								flowSummary.service.createDcPbsChart(attach.chart11153);
							}else if(type == "second"){
								flowSummary.service.createBcmChart(attach.chart5693);
								flowSummary.service.createUnionPayChart(attach.chart5692);
								flowSummary.service.createNoamChart(attach.chart5689);
								flowSummary.service.createDbHeartBeatChart(attach.chart10522);
								
							}

						}
					},
					error:function(){
						//alert("操作失败");
					}
					
					
				});
			}
		}
};


var m5Chart = {
        title: {
            text: 'M5机房公共流量'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: 'M5机房公共流量',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };


var pbsChart = {
        title: {
            text: 'PBS机房公网流量'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: 'PBS机房公网流量',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };


var m5PbsChart = {
        title: {
            text: 'M5-PBS机房万兆裸纤'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: 'M5-PBS机房万兆裸纤',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };


var officePbsChart = {
        title: {
            text: '办公区-PBS机房千兆裸纤'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '办公区-PBS机房千兆裸纤',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };

var zaPbsChart = {
        title: {
            text: '左岸公社-PBS机房专线'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '左岸公社-PBS机房专线',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };

var dcPbsChart = {
        title: {
            text: 'DC-PBS机房专线'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: 'DC-PBS机房专线',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };

var bcmChart = {
        title: {
            text: '交行专线'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '交行专线',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };


var unionPayChart = {
        title: {
            text: '银联专线'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '银联专线',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };



var dbHeartBeatChart = {
        title: {
            text: '数据库心跳'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '数据库心跳',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };


var noamChart = {
        title: {
            text: '诺安专线'
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
                '<td style="padding:0"><b>{point.y} KB</b></td></tr>',
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
            name: '诺安专线',
            color:'#9c8ade',
            data: [0.0, 0.0, 0.0,0.0, 0.0]
        }]
    };