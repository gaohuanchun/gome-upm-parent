$(function() {
	var normal = $("#normal_id").text();
	var error = $("#error_id").text();
	draw_url_health_index(parseInt(normal), parseInt(error));
	// 删除url
	$("#btn-del").click(function() {
		allUrl.controller.delUrl();
	});
	$("#btn_search").click(function() {
		allUrl.controller.search();
	});
	$("#import_submit").click(function(){
	
		importExcel();
	})

	$("#defaultReturnCode").on("click", function() {
		allUrl.service.defaultReturnCode();
	});

	$("#customReturnCode").on("click", function() {
		allUrl.service.customReturnCode();
	});
	$("#inputContent").blur(function(){
		   var inputContent =$("#inputContent").val();
		   if(inputContent.length>0){
			   $("#incloude_id").show();
			   return ;
		   }
		   $("#incloude_id").hide();
	   });
	$("#inputContent").mouseout(function(){
		   var inputContent =$("#inputContent").val();
		   if(inputContent.length>0){
			   $("#incloude_id").show();
			   return ;
		   }
		   $("#incloude_id").hide();
	   });

	$("#returnCode").change(function(){
		regCode();
	});
	allUrl.service.init();

});
function regCode(){
	var returnCode= $("#returnCode").val();
		if(returnCode.length==0){
			alert("URL请求返回码不能为空");
			$("#returnCode").focus();
			return false;
		}else{
			var reg= /(([2-9]\d{2})+[,]?)+/;
			var out= reg.test(returnCode);
			if(!out){
				alert("URL请求返回码格式错误");
				$("#returnCode").focus();
				return false;
			}
			var s =returnCode.split(",")
			if(s.length>0){
				for(var i=0;i<s.length;i++){
					if(s[i]>=200&&s[i]<=600){
						
					}else{
						alert("URL请求返回码格式错误");
						$("#returnCode").focus();
						return false;
					}
				}
			}else{
				if(returnCode>=200&&returnCode<=600){
					
				}else{
					alert("URL请求返回码格式错误");
					$("#returnCode").focus();
					return false;
				}
			}
			return true;
		}
}
function change(count){
	if(count==1){
		$("#post_param").show();
	}else{
		$("#post_param").hide();
		$("#postParameter").text("");
	}
}
function importExcel(){
	var excel = $("#excel").val();
	if(isExcel(excel)){
		$("#import_close").click();
		$.ajaxFileUpload({
	                url: contextPath+'/url/import',     //需要链接到服务器地址
	                secureuri: false,
	                type:'post',
	                fileElementId: 'excel',              //文件选择框的id属性
	                contentType:'text/html',
	                dataType:'text',
	                success: function (obj) {          //相当于java中try语句块的用法
						alert(obj);
						window.location.href=contextPath+"/url/get";
	                },
	                error: function (data, status, e) {
	                    alert('server error!');
	                }
	            });
	            alert("导入中请等待...");
	            
	}
}
function isExcel(filepath){
		if(filepath.length==0){
			alert('请选择Excel导入');
			return false;
		}
        var extStart=filepath.lastIndexOf('.');
        var ext=filepath.substring(extStart,filepath.length).toUpperCase();
        if(ext!='.XLSX'&&ext!='.XLS'){
            alert('导入文件不是Excel');
            return false;
        }
        return true;
}
function quickSearch(evt) {
	evt = (evt) ? evt : ((window.event) ? window.event : "") // 兼容IE和Firefox获得keyBoardEvent对象
	var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
	if (key == 13) { // 判断是否是回车事件。
		// 根据需要执行某种操作。
		// 根据需要执行某种操作。
		allUrl.controller.search();
	}
}

function draw_url_health_index(normal, error) {
	$('#url_health_index').highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		credits : {
			enabled : false
		// 禁用版权信息
		},
		title : {
			text : ''
		},
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				size : 200,
				dataLabels : {
					enabled : false,
					color : '#000000',
					connectorColor : '#000000',
					format : '<b>{point.name}</b>: {point.percentage:.1f}%'
				}
			}
		},
		series : [ {
			type : 'pie',
			name : '健康指数',
			data : [ {
				name : '正常',
				y : normal,
				color : '#9c8ade'
			}, {
				name : '异常',
				y : error,
				sliced : true,
				selected : true,
				color : '#CFCFCF'
			} ]
		} ]
	});
	$("#h2").show();
	$("#div_id").show();
}

var allUrl = {
	service : {
		init : function() {
			allUrl.service.checkAndNoCheck();
			allUrl.controller.useOrNoUse();
			allUrl.service.alarmCheckedVari();
			allUrl.controller.page();
			allUrl.controller.editUrl();
			allUrl.controller.editUrlSubmit();
		},
		getCheckedUrl : function() {
			var ids = "";
			$("input[name='checkbox']:checkbox").each(function() {
				if ($(this).is(":checked")) {
					ids += $(this).val() + ",";
				}
			});
			return ids.substring(0, ids.length - 1);
		},
		defaultReturnCode : function() {
			$("#urlBackCode").val("200");
			$('#urlBackCode').attr("disabled", "disabled");
		},
		customReturnCode : function() {
			$("#urlBackCode").val("200");
			$('#urlBackCode').attr("disabled", false);
		},
		checkAndNoCheck : function() {
			$("#checkAll").click(
					function() {
						// $("input[name='checkbox']:checkbox").prop("checked",this.checked);
						console.log($(this));
						$("input[name='checkbox']:checkbox").prop("checked",
								$(this).is(":checked"));
					});
			
			$("input[name='checkbox']:checkbox")
					.click(
							function() {
								var allBox = $("input[name='checkbox']:checkbox");
								$("#checkAll")
										.prop(
												"checked",
												allBox.length == allBox
														.filter(":checked").length ? true
														: false);
							});
		},
		getCheckedAlarm : function() {
			var ids = "";
			$("input[name='alarmWay']:radio").each(function() {
				if ($(this).is(":checked")) {
					ids += $(this).val() + ",";
				}
			});
			return ids.substring(0, ids.length - 1);
		},
		alarmCheckedVari : function() {
			$(".list_table").on("click", "#inlineCheckbox1", function() {
				$("#inlineCheckbox2").prop("checked", false);
			});

			$(".list_table").on("click", "#inlineCheckbox2", function() {
				$("#inlineCheckbox1").prop("checked", false);
			});

		}
	},
	controller : {
		createStep1 : function() {
			$.ajax({
				url : contextPath + '/url/create/step1',
				type : 'POST',
				dataType : 'html',
				data : {},
				success : function(data) {
					console.info(data);
					$(".content-wrapper").empty();
					$(".content-wrapper").append(data);
				},
				error : function() {
					alert("操作失败");

				}

			});
		},
		delUrl : function() {
			var ids = allUrl.service.getCheckedUrl();
			if (ids == "") {
				alert("请选择数据")
				return false;
			}
			if (confirm("确定要删除吗，删除后不可恢复")) {
				$.ajax({
					url : contextPath + '/url/del',
					type : 'POST',
					dataType : 'json',
					data : {
						"id" : ids
					},
					success : function(data) {
						console.info(data);
						if (data.code == 1) {
							alert("删除成功");
							window.location.href = contextPath + "/url/get";
						}
					},
					error : function() {
						alert("删除失败");

					}

				});
			}
		},
		useOrNoUse : function() {

			$(".list_table").on("click", "a[name='status']", function() {
				var urlId = $(this).attr("urlId");
				var status = $(this).attr("urlStatus");
				$.ajax({
					url : contextPath + '/url/changeStatus',
					type : 'POST',
					dataType : 'json',
					data : {
						"id" : urlId,
						"status" : status
					},
					success : function(data) {
						console.info(data);
						if (data.code == 1) {
							alert("操作成功");
							window.location.href = contextPath + "/url/get";
						}
					},
					error : function() {
						alert("操作失败");

					}

				});
			});
		},
		page : function() {
			$(".list_table").on("click", ".pageNumber", function() {
				var startTime = $("#hiddenStartTime").val();
				var endTime = $("#hiddenEndTime").val();
				var survival = $("#hiddenSurvival").val();
				var urlAddress = $("#hiddenUrlAddress").val();
				var pageNo = $(this).attr("pageNo");
				var pageSize = 10;
				// queryListForPageDetail(pageNo);
				$.ajax({
					url : contextPath + '/url/getUrlTable',
					type : 'POST',
					dataType : 'html',
					data : {
						"startTime" : startTime,
						"endTime" : endTime,
						"urlAddress" : urlAddress,
						"pageNo" : pageNo
					},
					success : function(data) {
						console.info(data);
						var bool = data.indexOf("sessionTimeOut");
						if (bool < 0) {
							$(".list_table").empty();
							$(".list_table").append(data);
							allUrl.service.checkAndNoCheck();
						} else {
							window.location.href = contextPath + "/home";
						}

					},
					error : function() {
						alert("操作失败");

					}

				});
			});
		},
		search : function() {

			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var survival = $("#selectSurvival").val();
			var urlAddress = $("#search_name").val();
			$.ajax({
				url : contextPath + '/url/getUrlTable',
				type : 'POST',
				dataType : 'html',
				data : {
					"startTime" : startTime,
					"endTime" : endTime,
					"survival" : survival,
					"urlAddress" : urlAddress
				},
				success : function(data) {
					console.info(data);
					var bool = data.indexOf("sessionTimeOut");
					if (bool < 0) {
						$(".list_table").empty();
						$(".list_table").append(data);
						allUrl.service.checkAndNoCheck();
						
					} else {
						window.location.href = contextPath + "/home";
					}

				},
				error : function() {
					alert("操作失败");

				}

			});

		},
		editUrl : function() {
			$(".list_table")
					.on(
							"click",
							"a[name='edit_table']",
							function() {
								
								var urlId = $(this).attr("urlId");
								$
										.ajax({
											url : contextPath + '/url/getById',
											type : 'POST',
											dataType : 'json',
											data : {
												"id" : urlId
											},
											success : function(data) {
												if (data.code == 1) {
													console.info(data);
													$("#hiddenUrlId").val(
															data.attach.id);
													$("#address").val(
															data.attach.url)
															.attr("disabled",
																	true);
													$("#urlFrc")
															.val(
																	data.attach.frequency);
													$("#timeout")
															.val(
																	data.attach.timeout);
													$("#time_number")
															.val(
																	data.attach.overtimes);
													$("#warn_time")
															.val(
																	data.attach.interval);
													$("#inputContent")
															.val(
																	data.attach.matchContent);
													if(data.attach.matchContent==null||data.attach.matchContent==''){
														$("#incloude_id").hide();
													}else{
														$("#incloude_id").show();
													}
													$(
															"input[name='method'][value="
																	+ data.attach.requestMethod
																	+ "]")
															.prop("checked",
																	true);
													if(data.attach.requestMethod=='POST'){
														$("#post_param").show();
														$("#postParameter").text(data.attach.postParameter);
													}
													$(
															"input[name='isContainsContent'][value="
																	+ data.attach.matchMethod
																	+ "]")
															.prop("checked",
																	true);
													$("#returnCode")
															.val(
																	data.attach.returnCode);
													var urlBackCodeValue = 1;
													/*if (data.attach.returnCode != "200") {
														urlBackCodeValue = 2;
														$("#urlBackCode").attr(
																"disabled",
																false);
													}*/
													$("input[name='alarmWay']:radio[value="+ data.attach.alarmMethod+ "]").prop("checked",true);


												}

											},
											error : function() {
												// alert("操作失败");

											}

										});
							});

		},
		editUrlSubmit : function() {
			$(".list_table")
					.on(
							"click",
							"#modal_btn_submit",
							function() {
								var content = {};
								content.id = $("#hiddenUrlId").val();
								;
								content.urlAddress = $("#address").val();
								content.accFre = $("#urlFrc").val();
								content.accTimeOut = $("#timeout").val();
								content.timeOutNum = $("#time_number").val();
								content.alarmInter = $("#warn_time").val();
								content.postParameter=$("#postParameter").val();
								content.method = $(
										"input[name='method']:checked").val();
								content.resContent = $("#inputContent").val();
								content.isContainsCon = $(
										"input[name='isContainsContent']:checked")
										.val();
								content.isDefaultCode = $(
										"input[name='isDefaultCode']:checked")
										.val();
								content.returnCode = $("#returnCode").val();
								if ($("#inlineCheckbox1").is(":checked")) {
									var checkedLength = $("input[name='alarmWay']:checked").length;
									if (checkedLength > 1) {
										alert("报警方式中不报警不能和其他同时选中");
										return false;
									}
								}
								content.alarmWay = allUrl.service
										.getCheckedAlarm();
								console.info(content.alarmWay);
								// if(content.key == ""){
								// alert("请返回第一步填写必选项");
								// return false;
								// }

								if (content.urlAddress == ""
										|| content.accTimeOut == ""
										|| content.timeOutNum == ""
										|| content.alarmInter == "") {
									alert("请输入必填项");
									return false;
								}
								var returnCode = $("#returnCode").val();
								if(!regCode()){
									return ;
								}
								$.ajax({
									url : contextPath + '/url/saveUpdate',
									type : 'POST',
									dataType : 'json',
									data : {
										'content' : JSON.stringify(content)
									},
									success : function(data) {
										if (data.code == 1) {
											// console.info(data.attach);
											alert("修改成功");
											window.location.href = contextPath
													+ "/url/get";
										}
										// $(".content-wrapper").empty();
										// $(".content-wrapper").append(data);
									},
									error : function() {
										alert("操作失败");

									}

								});
							});

		}
	}
};