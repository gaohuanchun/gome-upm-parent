Date.prototype.Format = function(formatStr)
{   
    var str = formatStr;
    var Week = ['日','一','二','三','四','五','六'];
    str=str.replace(/yyyy|YYYY/,this.getFullYear());
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));
    str=str.replace(/MM/,(this.getMonth()+1)>9?(this.getMonth()+1)+'':'0' + (this.getMonth()+1));
    str=str.replace(/M/g,this.getMonth());
    str=str.replace(/w|W/g,Week[this.getDay()]);
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());
    str=str.replace(/h|H/g,this.getHours());
    //分钟做处理，取5的整数倍
    str=str.replace(/m5/,(this.getMinutes()-this.getMinutes()%5)>9?(this.getMinutes()-this.getMinutes()%5)+'':'0' + (this.getMinutes()-this.getMinutes()%5));
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());
    str=str.replace(/m/g,this.getMinutes());
    //秒做处理，显示00
    str=str.replace(/s0|S0/,'00');
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());
    str=str.replace(/s|S/g,this.getSeconds());
    return str;   
}
//+---------------------------------------------------  
//| 日期计算  
//+---------------------------------------------------  
Date.prototype.DateAdd = function(strInterval, Number) {   
  var dtTmp=this;
  switch (strInterval){
      case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number));
      case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number));
      case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number));
      case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number));
      case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
      case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
      case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
      case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
  }  
}
Date.prototype.DateDiff = function(strInterval, Number) {
	var dtTmp = this;
	  switch (strInterval){
	      case 's' :return new Date(Date.parse(dtTmp) - (1000 * Number));
	      case 'n' :return new Date(Date.parse(dtTmp) - (60000 * Number));
	      case 'h' :return new Date(Date.parse(dtTmp) - (3600000 * Number));
	  }  
}