<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		<script type="text/javascript">
			$(function(){
				$(".currentNumType").click(function(){
					var deptId=$(this).attr("id");
					var typeCode=$(this).attr("name");
					showWindow("类别岗位现有编制人员查看","<%=request.getContextPath() %>/auth/authstatistics_currentlist_type.html?deptPost.deptId="+deptId+"&deptPost.postType="+typeCode,680, 300);
				});
				$(".currentNumLevel").click(function(){
					var deptId=$(this).attr("id");
					var level=$(this).attr("name");
					var typeCode=$(this).closest("td").attr("name");
					showWindow("具体岗位现有编制人员查看","<%=request.getContextPath() %>/auth/authstatistics_currentlist_level.html?deptPost.deptId="+deptId+"&deptPost.postType="+typeCode+"&deptPost.level="+level,680, 300);
				});
				$(".currentNumTypeShort").click(function(){
					var deptId=$(this).attr("id");
					var typeCode=$(this).attr("name");
					showWindow("类别岗位缺编岗位查看","<%=request.getContextPath() %>/auth/authstatistics_currentlist_type_short.html?deptPost.deptId="+deptId+"&deptPost.postType="+typeCode,600, 300);
				});
				$(".currentNumLevelShort").click(function(){
					var deptId=$(this).attr("id");
					var level=$(this).attr("name");
					var typeCode=$(this).closest("td").attr("name");
					showWindow("具体等级缺编岗位查看","<%=request.getContextPath() %>/auth/authstatistics_currentlist_level_short.html?deptPost.deptId="+deptId+"&deptPost.postType="+typeCode+"&deptPost.level="+level,600, 300);
				});
				
				$("#selectBtn").click(function(){
					location.href="<%=request.getContextPath() %>/auth/authstatistics_list.html?deptPost.deptId="+$("input[name='deptPost.deptId']").val()+
							"&deptPost.postType="+$("input[name='deptPost.postType']").val();
				});
				
				function nodeDisplayNone(nodeIndex,level){
					var nodes=$("tr[id='level_"+level+"'][name^='"+nodeIndex+"']");
					for(var i =0 ; i < nodes.length; i++){
						nodeDisplayNone(nodeIndex+"_"+i,parseInt(level)+1);
					}
					$(nodes).css("display","none");
				}
				
				function nodeDisplayBlock(nodeIndex,level){
					var nodes=$("tr[id='level_"+level+"'][name^='"+nodeIndex+"']");
					for(var i =0 ; i < nodes.length; i++){
						nodeDisplayBlock(nodeIndex+"_"+i,parseInt(level)+1);
					}
					for(var i =0 ; i < nodes.length; i++){
						if($(nodes[i]).attr("title")=="visable")
							$(nodes[i]).css("display","");
					}
				}
				
				$("a[class^=list_ico_]").toggle(
					function(){
						var classStr=$(this).attr("class");
						$(this).removeClass(classStr);
						$(this).addClass(classStr.replace("down","up"));
						var nodeIndex=$(this).closest("tr").attr("name");
						var level=$(this).closest("tr").attr("id").replace("level_","");
						$("tr[id='level_"+(parseInt(level)+1)+"'][name^='"+nodeIndex+"']").attr("title","");
						nodeDisplayNone(nodeIndex,parseInt(level)+1);
					},
					function(){
						var classStr=$(this).attr("class");
						$(this).removeClass(classStr);
						$(this).addClass(classStr.replace("up","down"));
						var nodeIndex=$(this).closest("tr").attr("name");
						var level=$(this).closest("tr").attr("id").replace("level_","");
						$("tr[id='level_"+(parseInt(level)+1)+"'][name^='"+nodeIndex+"']").attr("title","visable");
						nodeDisplayBlock(nodeIndex,parseInt(level)+1);
					}
				);
				
				fillRows("20", "", "", false);//填充空行
			});
		</script>
	</head>

	<body>
	<div class="formbox">
		<div class="toolbox">
		<!-- 按钮 -->
			<div class="buttonbox">
				<ul>
<%--					<li>--%>
<%--						<a id="btn_zj" class="btn_cx">查询</a>--%>
<%--					</li>--%>
				</ul>
		
			</div>
		</div>
		<div class="searchtab">
			<table width="100%" border="0" >
				<tbody>
					<tr>
						<th>
						   所属类别
						</th>
						<td>
							 <ct:codePicker name="deptPost.postType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST %>" code="${deptPost.postType }" />
						</td>
						<th>
						   部门
						</th>
						<td>
							 <ct:codePicker name="deptPost.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${deptPost.deptId }" />
						</td>
						<td>
							  <button id="selectBtn">查询</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h3 class="datetitle_01">
    	<span>编制统计列表<font color="#0457A7" style="font-weight:normal;"></font></span>
   		 </h3>
		<table summary="" class="datelist" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td>部门</td>
					<td>岗位类别/级别</td>
					<td>计划编制数</td>
					<td>现有编制数</td>
					<td>超编数</td>
					<td>缺编数</td>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${list}" var="bean" varStatus="levelone">
					<tr id="level_0" name="node_${levelone.index}" title="visable">
						<td>${bean.deptName}</td>
						<td name="id">
							<c:if test="${bean.childrenNum == 0}"><a class="list_ico_down">${bean.name}</a></c:if>
							<c:if test="${bean.childrenNum != 0}"><a class="list_ico_down">${bean.name}</a></c:if>
						</td>
						<td>${bean.planAuthNum}</td>
						<td>
							<c:if test="${bean.currentAuthNum > 0}"><a href="#" class="currentNumType" id="${bean.deptId }" name="${bean.typeCode }">${bean.currentAuthNum}</a></c:if>
							<c:if test="${bean.currentAuthNum <= 0}">${bean.currentAuthNum}</c:if></a>
						</td>
						<td>${bean.overNum}</td>
						<td>
							<c:if test="${bean.shortNum > 0}"><a href="#" class="currentNumTypeShort" id="${bean.deptId }" name="${bean.typeCode }">${bean.shortNum}</a></c:if>
							<c:if test="${bean.shortNum <= 0}">${bean.shortNum}</c:if></a>
						</td>
					</tr>
					<c:forEach items="${bean.children}" var="child" varStatus="leveltwo">
						<tr id="level_1" name="node_${levelone.index}_${leveltwo.index}">
							<td>${child.deptName}</td>
							<td name="id"><a class="list_ico1">${child.name}</a></td>
							<td>${child.planAuthNum}</td>
							<td name="${bean.typeCode}">
								<c:if test="${child.currentAuthNum > 0}"><a href="#" class="currentNumLevel" id="${bean.deptId }" name="${child.level }">${child.currentAuthNum}</a></c:if>
								<c:if test="${child.currentAuthNum <= 0}">${child.currentAuthNum}</c:if></a>
							</td>
							<td>${child.overNum}</td>
							<td name="${bean.typeCode}">
								<c:if test="${child.shortNum > 0}"><a href="#" class="currentNumLevelShort" id="${bean.deptId }" name="${child.level}">${child.shortNum}</a></c:if>
								<c:if test="${child.shortNum <= 0}">${child.shortNum}</c:if></a>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
	</div>
</html>
