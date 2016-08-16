function changeData(data) {
    var result = {
        traceTree: {
            traceId: "",
            totalTime: 0,
            beginTime: 0,
            endTime: 0,
            totalSize: 0,
            treeNodes: []
        }
    };

    if (data.nodes == undefined || data.nodes.length  == 0){
        return result;
    }

    result.traceTree.traceId = data.traceId;
    result.traceTree.totalTime = data.endTime - data.beginTime;
    var totalTime = result.traceTree.totalTime;
    result.traceTree.startTime = data.beginTime;
    result.traceTree.endTime = data.endTime;
    result.traceTree.totalSize = data.nodeSize;
    result.traceTree.showSize = data.nodes.length;
    result.traceTree.maxShowNodeSize = data.maxShowNodeSize;
    result.traceTree.maxQueryNodeSize = data.maxQueryNodeSize;
    result.traceTree.startTimeStr = convertDate(new Date(result.traceTree.startTime));
    result.traceTree.callIP = data.nodes[0].address;
    result.traceTree.hasCallChainTree = data.hasCallChainTree;
    result.traceTree.callChainTreeToken = data.callChainTreeToken;
    var tmpNode;
    var colId;
    for (var i = 0; i < data.nodes.length; i++) {
        tmpNode = data.nodes[i];
        colId = tmpNode.colId;
        tmpNode.modalId = colId.replace(/\./g,'');
        if (tmpNode.colId == "0") {
            tmpNode.isEntryNode = true;
        } else {
            tmpNode.isEntryNode = false;
        }
        if (tmpNode.spanTypeName == "") {
            tmpNode.spanTypeName = "UNKNOWN";
        }
        tmpNode.statusCodeName = tmpNode.statusCodeName;
        if (tmpNode.statusCodeName == "") {
            tmpNode.statusCodeName = "MISSING";
        }

        if (tmpNode.timeLineList.length == 1) {
            tmpNode.case = 1;
            tmpNode.cost = tmpNode.cost;
            tmpNode.totalLengthPercent = 90 * (tmpNode.startDate - result.traceTree.startTime) / result.traceTree.totalTime;
            tmpNode.spiltLengthPercent = 100 * tmpNode.cost / result.traceTree.totalTime;
        }

        if (tmpNode.timeLineList.length == 2) {
            if (tmpNode.timeLineList[1].startTime < tmpNode.timeLineList[0].startTime) {
                tmpNode.cost = tmpNode.timeLineList[1].cost;
                tmpNode.totalLengthPercent = 90 * (tmpNode.timeLineList[1].startTime - result.traceTree.startTime) / result.traceTree.totalTime;
                tmpNode.spiltLengthPercent = 90 * tmpNode.timeLineList[1].cost / result.traceTree.totalTime;
            } else if ((tmpNode.timeLineList[1].startTime >= tmpNode.timeLineList[0].startTime) &&
                ((tmpNode.timeLineList[1].startTime) <= (tmpNode.timeLineList[0].startTime + tmpNode.timeLineList[0].cost))) {
                if ((tmpNode.timeLineList[1].startTime + tmpNode.timeLineList[1].cost) <= (tmpNode.timeLineList[0].startTime + tmpNode.timeLineList[0].cost)) {
                    tmpNode.case = 3;
                    tmpNode.totalLengthPercent = 90 * (tmpNode.timeLineList[0].startTime - result.traceTree.startTime) / result.traceTree.totalTime;
                    tmpNode.clientCost = (tmpNode.timeLineList[1].startTime - tmpNode.timeLineList[0].startTime);
                    tmpNode.clientCostPercent = 90 * tmpNode.clientCost / result.traceTree.totalTime;
                    tmpNode.networkCost = (tmpNode.timeLineList[1].startTime + tmpNode.timeLineList[1].cost - tmpNode.timeLineList[1].startTime);
                    tmpNode.networkCostPercent = 90 * tmpNode.networkCost / result.traceTree.totalTime;
                    tmpNode.serverCost = (tmpNode.timeLineList[0].startTime + tmpNode.timeLineList[0].cost - tmpNode.timeLineList[1].startTime - tmpNode.timeLineList[1].cost);
                    tmpNode.serverCostPercent = 90 * tmpNode.serverCost / result.traceTree.totalTime;
                } else {
                    tmpNode.case = 4;
                    tmpNode.totalLength = (tmpNode.timeLineList[0].startTime - result.traceTree.startTime);
                    tmpNode.totalLengthPercent = 90 * tmpNode.totalLength / result.traceTree.totalTime;
                    tmpNode.clientCost = tmpNode.timeLineList[1].startTime - tmpNode.timeLineList[0].startTime;
                    tmpNode.clientCostPercent = 90 * tmpNode.clientCost / result.traceTree.totalTime;
                    tmpNode.serverCost = tmpNode.timeLineList[1].startTime + tmpNode.timeLineList[1].cost - tmpNode.timeLineList[1].startTime;
                    tmpNode.serverCostPercent = 90 * tmpNode.serverCost / result.traceTree.totalTime;
                }
            } else {
                tmpNode.case = 5;
                tmpNode.totalLength = (tmpNode.timeLineList[0].startTime - result.traceTree.startTime);
                tmpNode.totalLengthPercent = 90 * tmpNode.totalLength / result.traceTree.totalTime;
                tmpNode.clientCost = tmpNode.timeLineList[0].cost;
                tmpNode.clientCostPercent = 90 * tmpNode.clientCost / result.traceTree.totalTime;
                tmpNode.networkCost = tmpNode.timeLineList[1].startTime - tmpNode.timeLineList[0].startTime;
                tmpNode.networkCostPercent = 90 * tmpNode.networkCost / result.traceTree.totalTime;
                tmpNode.serverCost = tmpNode.timeLineList[1].cost;
                tmpNode.serverCost = 90 * tmpNode.serverCost / result.traceTree.totalTime;
            }
        }

        result.traceTree.treeNodes.push(tmpNode);
    }

    return result;
}

function convertDate(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;    //js从0开始取
    var date1 = date.getDate();
    var hour = date.getHours();
    var minutes = date.getMinutes();
    var second = date.getSeconds();
    return year + "-" + month + "-" + date1 + " " + hour + ":" + minutes + ":" + second;
}

function searchAll(name, value){
	if(value == 'traceId'){
		//var myReg = /^[a-zA-Z0-9_]{0,}$/; // 检测traceId格式是否正确
		var myReg = /[\u4e00-\u9fa5]+/;
		if(myReg.test(name)){
			 alert("格式不正确，请重新输入！");
			 return;
		}
		window.location.href = contextPath+"/app/main?traceId="+name.trim();
	}
	if(value == 'other'){
		loadAll(name);
	}
}

// 全文检索
function loadAll(content){
	var myReg = /[\u4e00-\u9fa5]+/; // 检测traceId格式是否正确
	if(myReg.test(content)){
		 alert("格式不正确，请重新输入！");
		 return;
	}
	window.location.href = contextPath+"/app/searchAll?content="+content.trim();
}

// 根据traceId查询
function searchByTraceId(){
	var searchKey = $("#key").val();
	if(searchKey != ''){
		var searchKey = $("#searchKey").val();
		if(searchKey != ''){
			var myReg = /[\u4e00-\u9fa5]+/; // 检测traceId格式是否正确
			if(myReg.test(searchKey)){
				 alert("格式不正确，请重新输入！");
				 return;
			}
			loadTree(searchKey);
		}else{
			window.location.href = contextPath+"/app/index";
		}
	}
}

function loadTree(traceId){
	$.ajax({
		type: 'POST',
        url: contextPath+'/app/search',
        dataType: 'json',
        data: {traceId: traceId},
        success: function (data) {
        		var changedData = changeData(jQuery.parseJSON(data.result));
        		if(changedData.traceTree.totalSize==0){
        			alert("内容不存在，修改后重新输入");
        			return;
        		}
        		var desc = "<h5><font style=\"font-size: 13px;font-family:'宋体';font-family:'微软雅黑';font-weight:bold !important;margin-left:20px;\">"+changedData.traceTree.traceId+"</font><br>"
        				 + "<font style=\"font-size: 12px;font-family:'宋体';font-family:'微软雅黑';margin-left:50px;\">调度入口IP："+changedData.traceTree.callIP+"，开始时间："+changedData.traceTree.startTimeStr+"，";
        				 if(changedData.traceTree.totalSize > changedData.traceTree.maxQueryNodeSize){
        					 desc += "调用超过"+changedData.traceTree.maxQueryNodeSize+"个节点，仅展示入口调用，";
        				 }else if(changedData.traceTree.totalSize > changedData.traceTree.maxShowNodeSize){
        					 desc += "共"+changedData.traceTree.totalSize+"个调用节点，仅展示前"+changedData.traceTree.showSize+"个调用节点，";
        				 }else{
        					 desc += changedData.traceTree.totalSize+"个调用节点，";
        				 }
        				 desc += "消耗总时长："+changedData.traceTree.totalTime+" ms。</font>"
        				 // 目前不可用，暂时注释
        				 /*if(changedData.traceTree.hasCallChainTree){
        					 desc += "<a href=\"javascript:void(0);\" onclick=\"javascript:gotoCallChainTree(this);\" value=''>"
        						  + "<font style=\"font-size: 13px;font-family:宋体;font-family:微软雅黑;color:#299BD4;font-weight:bold !important;\">查看调用树</font>"
        						  +"</a>";
        				 }*/
        				 desc += "</h5>";
        				 
        		$(".description").empty();
        		$(".description").append(desc);
        		
        		// 加载调用链
        		var html = template('traceTree', changedData.traceTree);
        		document.getElementById('list_table').innerHTML = html;
        		$("#traceTreeTable").treetable({expandable: true, indent: 10, clickableNodeNames: true});
        		$("#traceTreeTable").treetable("expandAll");
                $("tr[name='log']").each(function () {
                    var code = $(this).attr("statusCodeStr");
                    if (code != 0 || code == '') {
                        var node = $(this).attr("data-tt-id");
                        $(this).css("color", "red");
                    }
                });
                
                // 记载tracelog
        		var html = template('tracelog', changedData.traceTree);
        		document.getElementById('tab_tracelog').innerHTML = html;
        },
        error: function () {
        	
        }
	});
}

$(".list_table").on("click",".pageNumber", function(){
	var pageNo = $(this).attr("pageNo");
	var pageSize = 10;
	var businessKey = $("#key").val();

    $.ajax({
		url:contextPath+'/app/getTable',
		type:'POST',
		dataType : 'html',	
		data:{"pageNo":pageNo, "pageSize":pageSize, "businessKey":businessKey},
		success:function(data){
			console.info(data);
			var bool = data.indexOf("sessionTimeOut");
			if(bool < 0){
				$(".list_table").empty();
				$(".list_table").append(data);
			}else{
				window.location.href=contextPath+"/home";
			}
		},
		error:function(){
			alert("操作失败");
		}
	 });
});

$(function(){
	$("#searchBtn").click(function(){
		var pageNo = 0;
		var pageSize = 10;
		var businessKey = $("#searchKey").val();
	    $.ajax({
			url:contextPath+'/app/getTable',
			type:'POST',
			dataType : 'html',	
			data:{"pageNo":pageNo, "pageSize":pageSize, "businessKey":businessKey},
			success:function(data){
				console.info(data);
				var bool = data.indexOf("sessionTimeOut");
				if(bool < 0){
					$(".list_table").empty();
					$(".list_table").append(data);
				}else{
					window.location.href=contextPath+"/home";
				}
			},
			error:function(){
				alert("操作失败");
			}
		 });
	});
});

