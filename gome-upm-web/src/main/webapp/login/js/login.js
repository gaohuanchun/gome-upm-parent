/**
 * Created by hanpan on 2015/11/3.
 */
$(function(){

    //添加回车事件
	$("input").keypress(function(event){
		if(event.which==13){
			$(".login_btn").click();
		}
		
	});
	
	
	
    //获得焦点的时候
    $('#accountName').focus(accountFocus);
    //
    $('#pwd').focus(pwdFocus);
    ////失去焦点
    $("#accountName").blur(acconNameBlur);
    $('#pwd').blur(pwdBlur);

    //登录 单击
    $(".login_btn").click(login_click);


    



});

//文本框获得焦点的时候
function accountFocus(){
    $(this).removeClass("errorInput").addClass("inputborder");
    $(".userName").find($(".error_span")).hide().text("");
}

//密码框获得焦点

function pwdFocus(){

    $(this).removeClass("errorInput").addClass("inputborder");
    $(".password").find($(".error_span")).hide().text("");
}


//用户名失去焦点
function acconNameBlur(){
        //失去焦点的时候，获得这个文本矿的值，然后进行校验
        if($(this).val()==""){
            $(".userName").find($(".error_span")).show().text("用户名不能为空");
            $(this).removeClass("inputborder").addClass("errorInput");
        }
}

//密码框失去焦点
function pwdBlur(){
        if($(this).val()==""){
            $(".password").find($(".error_span")).show().text("密码不能为空！");
            $(this).removeClass("inputborder").addClass("errorInput")
        }
}


//点击登录的校验


function login_click (){
	var content={};
	var accountName = $("#accountName").val();
	var pwd = $("#pwd").val();
//	var ischeck = $("#remberLogin").is(":checked");
//  if($("#accountName").val()==""&& $('#pwd').val()==""){
//
//      $("#accountName").removeClass("inputborder").addClass("errorInput");
//      $(".userName").find($(".error_span")).show().text("用户名不能为空");
//      $("#pwd").removeClass("inputborder").addClass("errorInput");
//      $(".password").find($(".error_span")).show().text("密码不能为空！");
//  }
  
  if(accountName==""){
      $("#accountName").removeClass("inputborder").addClass("errorInput");
      $(".userName").find($(".error_span")).show().text("用户名不能为空");
  }else if(pwd==""){
	  $("#pwd").removeClass("inputborder").addClass("errorInput");
      $(".password").find($(".error_span")).show().text("密码不能为空！");
  }else{
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
				  alert(data.attach);
				  $("#pwd").val("");
				  
			  }
		},
		  error:function(){
			  alert("操作失败");
		  }
	  });
  }

}