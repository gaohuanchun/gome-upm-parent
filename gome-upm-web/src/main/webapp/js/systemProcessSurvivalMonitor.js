/**
 * Created by hanpan on 2016/6/20.
 */

//绘制饼图


$(function(){
    //服务占比
    //drowPie();
    //所属机房
   // drowPiecomputerRoom();
    //应用容器及数量
    //drowPiecontainer();

    //绘制新增应用数量折线图
    drowspline_container();

    //$("#info_search_span").click(info_table_data);

});

/****
 * 备注，静态页面的时候，分开写3个，正式调用的时候，可以用一个方法，然后根据需要不同的参数，传对应的参数
 * 目前观察需要传递的参数有： title(标题) ,renderTo（对应容器id 必须）,servers（饼图数据）
 *
 *
 *
 * ****/
//服务占比
/*
function drowPie(){
    chart = new Highcharts.Chart({
        chart: {
            renderTo:"server_acc",
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            backgroundColor:"#ECF0F5"
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        title: {
            text: '服务占比',
            x: -20, //center
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
            }
        },
        subtitle: {
            text: '总数量100台',
            x: -20,
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
                /!*marginLeft:'100px'*!/
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#333',
                    /!*format: '<b>{point.name}</b>: {point.percentage:.1f} %'*!/
                    /!*formatter:function(){
                     return '<b>' + this.series.name + '</b>: y=' + this.y + ', number='+this.point.number;
                     }*!/

                    formatter:function(){
                        return'<b style="margin-bottom:20px;font-size:14px;">'+this.point.name+'</b><br/>'+
                            '<b style="margin-bottom:20px;font-size:14px;">'+'数量:'+this.point.number+'</b><br/>'

                    }
                }
            }
        },
        style: {
            fontSize: '14px',
            fontFamily: 'Microsoft YaHei',
            fontWeight:'normal'
        },
        series: [{
            type: 'pie',
            data: [
                {y:40,'name':'虚拟机',number:60},
                {y:60,'name':'物理机',number:20},

            ]
        }]
    });

}
//所属机房
function drowPiecomputerRoom(){
    chart = new Highcharts.Chart({
        chart: {
            renderTo:"computerRoom",
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            backgroundColor:"#ECF0F5"
        },
        title: {
            text: '所属机房',
            x: -20, //center
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
            }
        },
        subtitle: {
            text: '总数量100台',
            x: -20,
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
                /!*marginLeft:'100px'*!/
            }
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#333',
                    formatter:function(){
                        return'<b style="margin-bottom:20px;font-size:14px;">'+this.point.name+'</b><br/>'+
                            '<b style="margin-bottom:20px;font-size:14px;">'+'数量:'+this.point.number+'</b><br/>'

                    }
                }
            }
        },
        style: {
            fontSize: '14px',
            fontFamily: 'Microsoft YaHei',
            fontWeight:'normal'
        },
        series: [{
            type: 'pie',
            data: [
                {y:29.7,'name':'鹏博士',number:60},
                {y:71.5,'name':'M5',number:20},

            ]
        }]
    });

}
//应用容器及数量
function drowPiecontainer(){
    chart = new Highcharts.Chart({
        chart: {
            renderTo:"container",
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            backgroundColor:"#ECF0F5"
        },
        title: {
            text: '应用容器及数量',
            x:-20,
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
            }
        },
        subtitle: {
            text: '总数量100台',
            x: -20,
            style: {
                fontSize: '18px',
                fontFamily: 'Microsoft YaHei',
                fontWeight:'normal'
                /!*marginLeft:'100px'*!/
            }
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#333',
                    /!*format: '<b>{point.name}</b>: {point.percentage:.1f} %'*!/
                    /!*formatter:function(){
                        return '<b>' + this.series.name + '</b>: y=' + this.y + ', number='+this.point.number;
                    }*!/

                    formatter:function(){
                        return'<b style="margin-bottom:20px;font-size:14px;">'+this.point.name+'</b><br/>'+
                            '<b style="margin-bottom:20px;font-size:14px;">'+'数量:'+this.point.number+'</b><br/>'

                    }
                }
            }
        },
        style: {
            fontSize: '14px',
            fontFamily: 'Microsoft YaHei',
            fontWeight:'normal'
        },
        series: [{
            type: 'pie',
            data: [
                {y:29.7,'name':'eblog',number:60},
                {y:71.5,'name':'Jetty',number:20},
                {y:106.4,'name':'Tomcat',number:40},
                {y:129.2,'name':'Jar',number:30}
            ]
        }]
    });

}
*/

//绘制新增应用数量折线图
function drowspline_container(){
    chart= new Highcharts.Chart({
        chart:{
   renderTo:'spline_newAdd',
            backgroundColor:"#ECF0F5"
        },
        credits:{
            enabled:false // 禁用版权信息
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00','06:00', '07:00', '08:00', '09:00', '10:00', '11:00']
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        style: {
            fontSize: '14px',
            fontFamily: 'Microsoft YaHei',
            fontWeight:'normal'
        },
        legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0
        },
        series: [{
            name: 'Tomcat',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'Weblogic',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Jetty',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'Jar',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]

    });

}

//点击查询按钮后，根据筛选条件显示对于的数据，静态页面写死，后续需传对于的值
/*
function info_table_data(){

    //根据查询条件显示数据
    //$(".list_table").toggle();
}*/
