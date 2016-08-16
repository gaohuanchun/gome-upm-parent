//获取当前时间---间隔时间为intervalTime   小时
function getSearchHour(intervalTime){
	var obj=new Object();
	var endTime=new Date();
	obj.endTime=endTime.Format('yyyy-MM-dd HH:m5:s0');
	var startTime=endTime.DateDiff('h',intervalTime);
	obj.startTime=startTime.Format('yyyy-MM-dd HH:m5:s0');
	return obj;
}
function copy(p,c){
	for(var key in c){
		p[key] = c[key];
	}
}