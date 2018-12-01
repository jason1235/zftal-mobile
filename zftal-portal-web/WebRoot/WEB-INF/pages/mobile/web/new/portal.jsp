<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<title></title>
<style type="text/css">
	.am-panel {
            margin-bottom: 0;
        }
        .am-panel-bd {
            background-color: #D4E5FA;
        }

        .am-avg-sm-2 li {
            height: 10rem;
        }
        .box {
            padding: 2rem;
            background-position: bottom right;
            border: 1px solid #eeeeee;
            cursor: pointer;
        }
        .box-1 {
              background-image: url("<%=request.getContextPath()%>/assets/i/portal/zzjf.png");
              background-repeat: no-repeat;
          }
        .box-2 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/kb.png");
            background-repeat: no-repeat;
        }
        .box-3 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/tsg.png");
            background-repeat: no-repeat;
        }
        .box-4 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/ykt.png");
            background-repeat: no-repeat;
        }
        .box-5 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/ky.png");
            background-repeat: no-repeat;
        }
        .box-6 {
            background-image: url("<%=request.getContextPath()%>/assets/i/portal/gz.png");
            background-repeat: no-repeat;
        }
</style>
</head>
<body>
	<script type="text/x-handlebars-template" id="amz-tpl">
    {{>header header}}

 <section class="am-panel am-panel-default">
        <main class="am-panel-bd">
            <div class="am-g">
                <div class="am-u-sm-4" style="text-align:right">
                    <img class="am-img-thumbnail am-circle" src="<%=request.getContextPath()%>/assets/i/avatar.jpg" width="100" height="100" />
                </div>
                <div class="am-u-sm-8">
                    <div><span class="am-text-xl">hbyzw</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-text-sm">移动事业部</span></div>
                    <div><span class="am-text-sm">2015-2016第一学期</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="am-text-sm">第十四教学周</span></div>
                </div>
            </div>

        </main>
    </section>
    <ul class="am-avg-sm-2">
        <li class="box box-1 am-text-lg">自助缴费</li>
        <li class="box box-2 am-text-lg">课表</li>
        <li class="box box-3 am-text-lg">图书馆</li>
        <li id="card" class="box box-4 am-text-lg">一卡通</li>
        <li class="box box-5 am-text-lg">科研</li>
        <li class="box box-6 am-text-lg">工资</li>
    </ul>   

    {{>navbar navbar}}
</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
	
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    
    var html = template(data);
    $tpl.before(html);
</script>	
</body>
</html>