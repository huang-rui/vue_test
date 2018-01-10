$(function() {
	$(":text,:password").keyup(function(e){
		var keyCode= e.keyCode;
		/*keyCode=13 enter.*/
		if(keyCode!=13){
			return false;
		}
		var thisName=this.name;
		if(thisName=="username"){
		    $("[name='password']").focus();
		}
		if(thisName=="pwd"){
			$("[name='checkCode']").focus();
		}
		if(thisName=="checkCode"){
			  verSubmit();
		}
	});

    /*记住我之前的复选框的样式切换.*/
    $('.check-box span').click(function () {
        var checkVal = $('#remember-me') , val = checkVal.val() , $this = $(this);
        if (val == 1) {
            if ($this.hasClass('is-check')) {
                $this.removeClass('is-check');
            }
            $this.addClass('no-check');
            checkVal.val(0);
            $('#remember-me').removeAttr("checked");
        } else {
            if ($this.hasClass('no-check')) {
                $this.removeClass('no-check');
            }
            $this.addClass('is-check');
            checkVal.val(1);
            $('#remember-me').attr("checked", "checked");
        }
    });

    /*针对IE浏览器.*/
    $('.close').click(function(){
        $(this).parent('li').find('input').val('').focus();
    });

    if ($("#errorMsg").val() != '') {
        alert($("#errorMsg").val());
    }

    $(".username").focus();

});

/*登录按钮.*/
function verSubmit()
{
    var uname = $.trim($("#username").val());
    var passwd = $.trim($("#password").val());
    if(uname == "" || uname == null || uname == "请输入用户名")
    {

        alert("用户名不能为空,请输入用户名!");
        document.getElementById("username").focus();
        return false;
    }
    if(passwd == "" || passwd == null || passwd == "请输入密码")
    {
        alert("密码不能为空,请输入密码!");
        $("#password").focus();
        return false;
    }
    var vilidcode = $.trim($("#checkCode").val());
    if(vilidcode == "" || vilidcode == null || vilidcode == "验证码")
    {
        alert("验证码不能为空,请输入验证码!")
        $("#checkCode").focus();
        return false;
    }

    checkcode(vilidcode);

}

/*刷新验证码.*/
function reloadImg(){
    $("#validateImg").attr("src",$("#validateImg").attr("src").split("?")[0]+"?"+new Date().getTime())
}

// function tologin(){
// 	window.location.href="/";
// }

/*校验验证码.*/
function checkcode(vilidcode){
    var baseHref = document.getElementById("base").href;
    var basePath = baseHref.split("/")[baseHref.split("/").length-1];
    $.get(basePath+"/checkcode?t="+new Date().getTime(),{checkCode:vilidcode},function(data) {
       if(data != 1){
           alert(data);
       }else{
           $("#loginForm").submit();
       }
    });
}
