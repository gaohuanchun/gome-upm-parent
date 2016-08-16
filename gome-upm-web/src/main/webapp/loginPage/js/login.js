/**
 * Created by hanpan on 2015/12/15.
 */
/*
* 登录操作的处理
* 校验用户名
* 校验密码
*
* */
  $("#usename").blur(function(){
    if($("#usename").val()==""){
        showMessage();
        $("#J_Message").text("用户名不能为空");
    }
})


$("#usepassword").blur(function(){
    if($("#usepassword").val()==""){
        showMessage();
        $("#J_Message").text("密码不能为空");
    }
})

$(".login-btn").click(checkinput);

function checkinput(){

    var userName=$("#usename").val(),
        usepassword = $("#usepassword").val();
    //非空校验
    if(userName==""||usepassword==""){
        showMessage()
        $("#J_Message").text("用户名或密码不能为空");
        return;
    }
    //密码长度限制
    if(usepassword.length>16||usepassword.length<4){
        showMessage()
        $("#J_Message").text("密码长度输入应在4~16之间");
        return;
    }

   //此处是属于校验成功的情况，发送ajax 请求，给后台，进行进一步的数据校验，需提供接口才行;
   $(".form-signin").submit();
}

//键盘的keydown事件

$(document).keydown(function(e){
    if(e.keyCode==13){
        checkinput();//校验文本框，如果成功则进入
    }
})


 $(function(){
	 var errorcodeval=$("#errorcode").val();
	 if(errorcodeval==""){
	       return;
	 }else if(errorcodeval==1){
	    //显示错误提示信息
	    showMessage()
	    $("#J_Message").text("用户名或密码有误！");
	 }else{
		 showMessage()
		 $("#J_Message").text("等待系统同步用户信息！");
	 }
 })





function showMessage(){
    $("#J_Message").show();
    setTimeout("atuohide('id')",2000);//3秒，可以改动

}

function atuohide(id){
    $("#J_Message").hide(200);
}