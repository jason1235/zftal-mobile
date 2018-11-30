<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<title></title>
<style type="text/css">
	.am-panel-bd ul li {
            padding: 0.5rem;
        }
</style>    
</head>
<body>
<div id="main">
    <div class="am-panel am-panel-default" style="margin-bottom: 0;border-bottom: none;">
        <div class="am-panel-hd">
            <h3 class="am-panel-title">已选应用&nbsp;<span class="am-text-sm">拖动排序/删除</span>

                <button type="button" id="submit" class="am-btn am-btn-warning am-btn-xs am-round" style="float: right;vertical-align: middle;">提交</button>
                <button type="button" id="cancel" class="am-btn am-btn-xs am-round" style="float: right;margin-right:0.5rem;vertical-align: middle;">取消</button>
            </h3>
        </div>
        <div class="am-panel-bd">
            <ul id="subUl" class="am-avg-sm-3" style="min-height: 3rem;">
                
            </ul>
        </div>
    </div>
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd">
            <h3 class="am-panel-title"><span class="am-text-sm">拖动添加更多栏目</span></h3>
        </div>
        <div class="am-panel-bd">
            <ul id="otherUl" class="am-avg-sm-3" style="min-height: 3rem;">
                
            </ul>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script type="text/javascript"> 
	
	var subs = ${subServices};
	var others = ${otherServices};

	var tpl = '{{#each this}}<li code="{{fwbm}}" class="item"><span class="am-btn am-btn-sm am-btn-default am-round">{{name}}</span></li>{{/each}}'
	var template = Handlebars.compile(tpl);
	$('#subUl').html(template(subs));
	$('#otherUl').html(template(others));

	$('#submit').on('touchstart',function() {
		var codes = [];
		$('#subUl li').each(function(){
			codes.push($(this).attr('code'));
		});
		$.post(_path + "/mobile/web_saveSubscribe.html",{serviceCode : codes.join(',')},function(data) {
			//history.go(-1);
			location.href = document.referrer;
		});
	});
	
	$('#cancel').on('touchstart',function() {
		history.go(-1);
	});

	var multi = document.getElementById("main");
    new Sortable(multi,{
        handle:'.am-panel-hd',
        animation:150
    });

    [].forEach.call(multi.getElementsByTagName('ul'), function (el){
        new Sortable(el, { group: 'items',draggable:'.item',animation:150 });
    });
    
</script>	
</body>
</html>