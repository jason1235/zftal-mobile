<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/commons/hrm/head.ini" %>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ztree/zTreeStyle.css" type="text/css">
	<style type="text/css">
		ul.ztree {border: 1px solid #617775;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
		div#rMenu {position:absolute; visibility:hidden; top:0; background-color: #555;text-align: left;padding: 2px;}
		div#rMenu ul li{
			margin: 1px 0;
			padding: 0 5px;
			cursor: pointer;
			list-style: none outside none;
			background-color: #DFDFDF;
			width: 60px;
		}
	</style>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/ztree/jquery.ztree.all-3.4.min.js"></script>
	<script type="text/javascript">
	var setting = {
		data: {
			key:{
				title:"remark",
				name:"nameAndId"
			}
		},
		view:{
			showTitle:false
		}
	};
	
	//按钮click初始化
	$(function(){
		$("#back").click(function(){//功能条返回按钮
			location.href="<%=request.getContextPath()%>/config/assessConfig_list.html";
		});
	});
	
	var zTree;
	$(document).ready(function(){
		$.ajax({
			url:'<%=request.getContextPath()%>/org/org_orgTree.html?query.type=${query.type}',
			type:'post',
			data:'pz_id=${model.pz_id}',
			success:function(data){
				$.fn.zTree.init($("#treeDemo"), setting, data);
				zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandAll(true);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("请求出错："+textStatus);
			}
		});
	});
	
	</script>
</head>
<BODY>
<!-- 
<div class="toolbox">
	<div class="buttonbox">
		<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
	</div>
</div>
-->

<div>
	<div style="float:left;width:100%">
		<ul id="treeDemo" class="ztree" style="width: 96%;height: 100%;border: 1px solid #B0CBE0;"></ul>
	</div>
</div>
</BODY>
</html>