var charta = {
    
	chart : {
		renderTo : 'container',
		type : 'line'
	},
	credits: {
	     enabled: false
	},
    navigation: {
        buttonOptions: {
            enabled: true
        }
    },
    exporting: {
        type: 'image/jpeg',
        enabled:true,
        filename:'heheh',
        url:'http://127.0.0.1:8080/export/'
    },
	title : {
		text : 'Monthly Average Temperature',
		x : -20
	},
	subtitle : {
		text : 'Source: WorldClimate.com',
		x : -20
	},
	xAxis : {
		categories : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
				'Sep', 'Oct', 'Nov', 'Dec' ]
	},
	yAxis : {
		title : {
			text : 'Temperature (°C)'
		},
		plotLines : [ {
			value : 0,
			width : 1,
			color : '#808080'
		} ]
	},
	tooltip : {
		valueSuffix : '°C'
	},
	legend : {
		layout : 'vertical',
		align : 'right',
		verticalAlign : 'middle',
		borderWidth : 0
	},
	series : [
			{
				name : 'Tokyo',
				data : [ 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3,
						18.3, 13.9, 9.6 ]
			},
			{
				name : 'New York',
				data : [ -0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1,
						14.1, 8.6, 2.5 ]
			},
			{
				name : 'Berlin',
				data : [ -0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3,
						9.0, 3.9, 1.0 ]
			},
			{
				name : 'London',
				data : [ 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2,
						10.3, 6.6, 4.8 ]
			} ]

};

function createCharts() {
	$.ajax({
		url : contextPath + "/demo/ajax/demo01",
		type : "get",
		dataType : "json",
		success : function(data) {

			if (data.code == 1) {
				var sdata = data.attach.seria;
				var cate = data.attach.categories;
				charta.xAxis.categories = cate;
				charta.series = sdata;
				hc = new Highcharts.Chart(charta);
			}
		},
		error : function() {
			alert("操作失败");
		}
	});
}

$(function() {
	createCharts();
	//setInterval("createCharts()", 5000);
});
