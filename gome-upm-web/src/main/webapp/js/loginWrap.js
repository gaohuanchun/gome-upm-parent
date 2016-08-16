/**
 * 由于ajax请求在遇到服务端重定向到登录页时，会导致登陆页嵌套在当前页中
 * 因此，在登陆页中引用该js，当登陆页检测到自身处于嵌套时，就强制浏览器访问登陆页，以避免嵌套的发生
 */
$(function(){
	var num = $("title").length;
	if(num>1){
		window.location.href=contextPath+"/login";
	}
});