<#assign base = request.contextPath/>
<!DOCTYPE html>
<html lang="zh-CN"
    <head>
        <meta charset="UTF-8"/>

        <!-- x-ua-compatible是IE8的一个专有meta属性，告诉IE8采用何种IE版本去渲染网页 -->
        <meta http-equiv="x-ua-compatible" content="IE=edge"/>

        <!-- 以下4个是清浏览器缓存的 -->
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="Cache-Control" content="no-cache" />
        <meta http-equiv="Cache-Control" content="no-store"/>
        <meta http-equiv="Expires" content="0" />

        <!-- 一般用于手机页面的开发
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        -->

        <base id="base" href="${base}">
        <title>SpringBoot!</title>

        <link href="//cdn.jsdelivr.net/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
        <link href="${base}/css/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="page-header">
                <h1>SpringBoot Hello!</h1>
            </div>
            <div>
                Date: ${time?date}
                <br>
                Time: ${time?time}
                <br>
                Message: ${message}
            </div>
        </div>
        <footer class="footer">
            <div class="container">
                <p class="text-muted">@2017</p>
            </div>
        </footer>
        <script src="//cdn.jsdelivr.net/ie10-viewport/1.0.0/ie10-viewport.min.js"></script>
        <script src="//cdn.jsdelivr.net/jquery/1.12.1/jquery.min.js"></script>
        <script src="//cdn.jsdelivr.net/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>