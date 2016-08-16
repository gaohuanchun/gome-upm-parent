/**
 * Created by hanpan on 2015/12/15.
 */
/*
* 登录操作的处理
* 校验用户名
* 校验密码
*
* */
  $("#accountName").blur(function(){
    if($("#accountName").val()==""){
        showMessage();
        $("#J_Message").text("用户名不能为空");
    }
})


$("#pwd").blur(function(){
    if($("#pwd").val()==""){
        showMessage();
        $("#J_Message").text("密码不能为空");
    }
})

$(".login-btn").click(checkinput);

function checkinput(){

    var accountName=$("#accountName").val();
    var pwd = $("#pwd").val();
    //非空校验
    if(accountName==""||pwd==""){
        showMessage()
        $("#J_Message").text("用户名或密码不能为空");
        return;
    }
    //密码长度限制
//    if(usepassword.length>16||usepassword.length<4){
//        showMessage()
//        $("#J_Message").text("密码长度输入应在4~16之间");
//        return;
//    }
    var content={};
    content.userName=accountName;
	content.passwd=pwd;//$.md5(pwd, 'gome.com');;
	  $.ajax({
		  url:contextPath+"/toLogin",
		  type:"POST",
		  dataType:"json",
		  data:{"content":JSON.stringify(content)},
		  success:function(data){
			 if(data.code==1){
				 window.location.href=contextPath+"/index";
			  }else{
				//显示错误提示信息
			    showMessage()
			    $("#J_Message").text("用户名或密码有误！");
			    $("#pwd").val("");
			  }
		},
		  error:function(){
			  alert("操作失败");
		  }
	  });
}

//键盘的keydown事件

$(document).keydown(function(e){
    if(e.keyCode==13){
        checkinput();//校验文本框，如果成功则进入
    }
})

function showMessage(){
    $("#J_Message").show();
    setTimeout("autohide('id')",2000);//2秒，可以改动

}

function autohide(id){
    $("#J_Message").hide(200);
}