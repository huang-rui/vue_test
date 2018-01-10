<#assign base = request.contextPath/>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="UTF-8"/>
        <meta http-equiv="x-ua-compatible" content="IE=edge"/>
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="Cache-Control" content="no-cache" />
        <meta http-equiv="Cache-Control" content="no-store"/>
        <meta http-equiv="Expires" content="0" />

        <base id="base" href="${base}">
        <title>登录页面</title>

        <link href="${base}/css/login.css" rel="stylesheet" type="text/css"/>

        <script src="${base}/js/utils/jquery.min.js" type="text/javascript"></script>
        <script src="${base}/js/utils/bootstrap.min.js" type="text/javascript"></script>
        <script src="${base}/js/login/login.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="mainBox">
            <div class="leftBox">
                <div>
                    <form id="loginForm" action="/framework/login" method="post">
                        <div class="loginTit png"></div>
                        <ul class="infList">
                            <li class="grayBox">
                                <label for="username" class="username-icon"></label>
                                <input id="username" class="username" name="username" type="text" placeholder="请输入用户名"/>
                                <div class="close png hide"></div>
                            </li>
                            <li class="grayBox">
                                <label class="pwd-icon" id="pwd"></label>
                                <input id="password" name="password" class="pwd" type="password" placeholder="请输入密码"/>
                                <div class="close png hide"></div>
                            </li>
                            <li>
                                <label class="validateLabel"></label>
                                <input id="checkCode" name="checkCode" class="checkCode" type="text" placeholder="验证码" />
                                <img onclick="reloadImg();" style="cursor: pointer" src="${base}/images/imagecode" id="validateImg" alt="验证码" class="codePic" title="验证码。点击此处更新验证码。"/>
                                <a class="getOther" href="javascript:void(0);" onclick="reloadImg();" title="点击此处可以更新验证码。">刷新</a>
                            </li>
                        </ul>
                        <ul class="infList reloadBtn" style="display: none;">
                            <li>
                                <a href="javascript:void(0);" onclick="tologin();">本页面已经失效。请点击此处重新登录。</a>
                            </li>
                        </ul>
                        <div class="loginBtnBox">
                            <div class="check-box">
                                <input type="hidden" value="0" id="remember-me" name="remember-me" onclick="if(this.checked){this.value = 1}else{this.value=0}" />
                                <span class="toggleCheck no-check" id="repwd"></span>
                                记住我
                            </div>
                            <input type="button" id="loginBtn" onclick="verSubmit()" value="登 录" class="loginBtn png" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>

</html>