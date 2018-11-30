<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//Dtd XHTML 1.0 transitional//EN" "http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/roster/column.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/roster/roster.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <style type="text/css">
    .search_advanced .prop-item li{
    	float:left;
    }
	.search_advanced .selected-attr2 { background:#fcf7d9; border:1px solid #a7bdd3; float:left; width:99.81%; width:99.73%\9; *width:99.81%;
		margin-top:0; }
	.search_advanced .selected-attr2 h3 { margin-left:67px; display:inline; float:left; line-height:25px; padding:5px 0; font-weight:normal; }
	.search_advanced .selected-attr2 h4 { margin-left:5px; float:left; width:650px; line-height:25px; padding:5px 0; font-weight:normal; }
    </style>
    <script type="text/javascript">
    $(function(){
    	conditionAreaFoldingEvent();
    	conditionListHoverEvent();
    	backButtonEvent();
    	configPoolInit();
    	$("div.prop-item>dl").each(function(){
    		initSingleConfigHtmlEvent(this);
    		if($(this).find("a.selectedValue").length>0){
    			$(this).find("a.more_down").click();//包含选中项的条件，默认展开
    		}
    	});
    	$("div.prop-item a.selectedValue").click();//选中条件模拟点击事件
    	initPreviewEvent();
    	initSaveQueryEvent();
    	initSaveOtherEvent();
    	initConfigEvent();
    	initExportEvent();
    	//$("#preview").click();
    	$("div.prop-item").fadeIn();
    	initTooltips();
    	$("#comp_title ul>li").click(function(){
    		if(!$(this).hasClass("ha")){
    			$("#comp_title ul>li").removeClass("ha");
    			$(this).addClass("ha");
    		}
    		if($(this).is("#li-1")){
    			$("#query-con1").css("display","block");
    			$("#query-con2").css("display","none");
    			$("#config-cond").css("display","block");
    			$("div.selected-attr").css("display","block");
    			$("div.selected-attr2").css("display","none");
    		}else{
    			$("#query-con1").css("display","none");
    			$("#query-con2").css("display","block");
    			$("#config-cond").css("display","none");
    			$("div.selected-attr").css("display","none");
    			$("div.selected-attr2").css("display","block");
    		}
    	});
    	
    	$("#list_body>tr","#dataArea").live("dblclick",function(){
    		 var id = $(this).find("input[gh='gh']").val();
             goUrl(_path+"/normal/staffResume_listByGh.html?gh="+id+"&type=teacher");
    	});
    	
    });
    function pageCall(){
    	$("#preview").click();
    }
    function initTooltips(){
    	$("#actions a").each(function(){
    		tooltips(this);
    	});
    }
    
    function addCondition(ele){
    	var trObj=$(ele).closest("tr");
    	/*
    	if(trObj.find("input[name='fieldValue']").val()==''){
    		alert("条件值不能为空！");
    		return false;
    	}
    	*/
    	var tr2=$("#select-con-template tbody").html();
    	var trObj2=$(tr2);
    	trObj2.find("input[name='classId']").val(trObj.attr("classId"));
    	trObj2.find("input[name='configId']").val(trObj.attr("configId"));
    	trObj2.find("input[name='fieldValue']").val(trObj.find("input[name='fieldValue']").val());
    	trObj2.find("td[name='propertyName']").text(trObj.attr("propertyName"));
    	if(trObj.attr("fieldType")=='CODE'){
    		trObj2.find("td[name='fieldValue_view']").text(trObj.find("input[name='fieldValue']").next().val());
    	}else{
    		trObj2.find("td[name='fieldValue_view']").text(trObj.find("input[name='fieldValue']").val());
    	}
    	trObj2.find("select[name='operator']").val(trObj.find("select[name='operator']").val());
    	trObj2.find("select[name='logicalRel']").val(trObj.find("select[name='logicalRel']").val());
    	$("#selectedConds>table>tbody").append(trObj2);
    	assembleCondition();
    }
    function removeCondition(ele){
    	var trObj=$(ele).closest("tr");
    	trObj.remove();
    	assembleCondition();
    }
    function assembleCondition(){
    	var assembledConds_view="";
    	var len=$("#selectedConds>table>tbody tr").length;
    	$("#selectedConds>table>tbody tr").each(function(i){
    		var obj=$(this);
    		//拼接显示条件
    		assembledConds_view+="<font color=\"red\">"+obj.find("select[name='parenthesisBefore']").val()+"</font>";
    		assembledConds_view+=obj.find("td[name='propertyName']").text();
    		assembledConds_view+="&nbsp;<font color=\"blue\">"+obj.find("select[name='operator'] option:selected").text()+"</font>&nbsp;";
    		if("NUMBER"==obj.data("fieldType")){
    			assembledConds_view+=obj.find("td[name='fieldValue_view']").text();
    		}else{
    			assembledConds_view+="'"+obj.find("td[name='fieldValue_view']").text()+"'";
    		}
    		assembledConds_view+="<font color=\"red\">"+obj.find("select[name='parenthesisAfter']").val()+"</font>";
    		if(i<len-1){
    			assembledConds_view+="<font color=\"red\">&nbsp;"+obj.find("select[name='logicalRel'] option:selected").text()+"</font>&nbsp;";
    		}
    	});
    	$(".selected-attr2 h4").html(assembledConds_view);
    }
    </script>
  </head>
<body>
<!-- 
<div class="toolbox">
	<div class="buttonbox">
		<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
	</div>
</div>
-->
<div class="compTab">
	<div id="comp_title" class="comp_title" style="margin-top: 0px;">
		<ul style="width:90%">
			<li class="ha" id="li-1"><a href="#"><span>普通查询</span></a></li>
			<li id="li-2"><a href="#"><span>高级查询</span></a></li>
		</ul>
		<div class="buttonbox" style="width:10%;float: right;position: relative;">
			<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
		</div>
    </div>
	<div class="comp_con search_advanced">
		<div style="position:relative;z-index:500" class="selectbox" id="config-cond">
			<ul class="datetitle_01">
		    	<li class="ico_xl">查询条件配置</li>
		    </ul>
		    <div style="display: none;" id="tosel" class="tosel">
		    	<ul style="padding: 15px 10px;">
					<li>条件名称模糊查询</li>
		        	<li>
		            	<input style="width:300px;" class="text_nor text_nor_rs" name="" type="text" value="" />
						<button class="btn_common">搜索</button>
		            </li>
		        </ul>
		        <ul class="sel_tab">
		        </ul>
		        <ul class="sel_con1">
		        	<s:iterator value="configList" var="config">
		        	<li><input type="hidden" value="${config.guid }">
		        	<s:if test="selected">
		        	<a href="#" class="current" title="${config.infoProperty.name }">${config.infoProperty.name }</a>
		        	</s:if>
		        	<s:else>
		        	<a href="#" title="${config.infoProperty.name }">${config.infoProperty.name }</a>
		        	</s:else>
		        	</li>
		        	</s:iterator>
		        </ul>
		    </div>
		</div>
		<div class="selected-attr" id="conditionArea1">
			<h3>已选条件：</h3>
			<dl>
			</dl>
		</div>
		<div class="selected-attr2" style="display: none;">
			<h3>高级条件：</h3>
			<h4>
			</h4>
		</div>
		<div id="total">
  			<input type="hidden" id="guid" name="guid" value="${guid }"/>
	  		<div id="query-con1">
				<div class="prop-item" style="display:none;">
					${initConfigHtml }
				</div>
	  		</div>
			<div id="query-con2" style="display: none;">
				<div id="selectedConds" class="tab" style="clear: both;max-height: 150px;overflow: auto;">
					<table align="center" class="formlist"> 
						<tbody></tbody>
					</table>
				</div>
				<div class="tab" style="max-height: 250px;overflow: auto;">
					<table align="center" class="formlist"> 
						<tbody>
							<c:forEach items="${configList }" var="item">
								<tr classId="${item.classid }" configId="${item.guid }" propertyName="${item.infoProperty.name }" 
									fieldType="${item.infoProperty.fieldType }">
									<th>${item.infoProperty.name }</th>
									<td>
										<select name="operator">
											<option value="=">=</option>
											<option value="!=">!=</option>
											<option value="like">包含</option>
											<option value="not like">不包含</option>
											<option value="&gt;">&gt;</option>
											<option value="&gt;=">&gt;=</option>
											<option value="&lt;">&lt;</option>
											<option value="&lt;=">&lt;=</option>
											<!-- 
											<option value="not null">not null</option>
											<option value="null">null</option>
											 -->
										</select>
									</td>
									<td>
										<c:choose>
											<c:when test="${item.infoProperty.fieldType=='CODE' }">
												<ct:codePicker name="fieldValue" catalog="${item.infoProperty.codeId }" code=""/>
											</c:when>
											<c:when test="${item.infoProperty.fieldType=='DATE' ||item.infoProperty.fieldType=='MONTH' ||item.infoProperty.fieldType=='YEAR' }">
												<input type="text" name="fieldValue" style="width: 181px;" class="Wdate"
													onfocus="WdatePicker({dateFmt:'${item.infoProperty.typeInfo.format}'})"/>
											</c:when>
											<c:when test="${item.infoProperty.fieldType=='SIGLE_SEL'}">
												<select name="fieldValue" style="width: 110px;">
													<option value=""></option>
													<option value="1">是</option>
													<option value="0">否</option>
												</select>
											</c:when>
											<c:otherwise>
												<input type="text" name="fieldValue" style="width: 181px;"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<select name="logicalRel">
											<option value="and">并且</option>
											<option value="or">或者</option>
										</select>
									</td>
									<td class="buttonbox">
										<ul>
											<li>
												<a title="加入搜索条件列表" onclick="addCondition(this);" class="btn_zj" href="#"></a>
											</li>
										</ul>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- 伸缩按钮 -->
		<div class="more--item_bottom" style="clear:both;margin-bottom:5px"><p><a href="#" class="up">收 起</a></p></div>	
	</div>
</div>




<div class="toolbox">
<!-- 按钮 -->
	<div class="buttonbox">
		<ul id="actions">
			<li>
				<a id="preview" class="btn_yl" title="根据当前参数查询花名册">查询</a>
			</li>
			<li>
				<a id="saveQuery" class="btn_down" title="保存当前查询参数,成功后会自动刷新">保存</a>
			</li>
			<li>
				<a id="saveOther" class="btn_ccg" title="根据当前参数另存一份新的花名册">另存为</a>
			</li>
			<li>
				<a id="config" class="btn_sz" title="定义查询后显示的字段列">定义字段</a>
			</li>
			<li>
				<a id="export" class="btn_dc" title="根据当前参数导出花名册">导出</a>
			</li>
		</ul>
	</div>
	<p class="toolbox_fot">
		<em></em>
	</p>
</div>
<div class="formbox"> 
  <!--标题start-->
  <h3 class="datetitle_01"><span>查询结果列表<font color="#0457A7" style="font-weight:normal;"> (双击查看详细)</font></span></h3>
  <!--标题end-->
  <form>
  <div id="dataArea">
  <!-- 
  <table width="100%" class="dateline">
    <thead id="list_head">
      <tr>
        <td>序 号</td>
        <td>工 号</td>
        <td>姓 名</td>
    </thead>
    <tbody id="list_body">
    </tbody>
  </table>
   -->
  </div>
  </form>
</div>

<table id="select-con-template" style="display: none;">
	<tbody>
	<tr>
		<input type="hidden" name="classId"/>
		<input type="hidden" name="configId"/>
		<input type="hidden" name="fieldValue">
		<td>
			<select name="parenthesisBefore" onchange="assembleCondition()"> 
				<option value=" "></option> 
				<option value="(">&nbsp;(&nbsp;</option> 
				<option value="((">&nbsp;((&nbsp;</option> 
				<option value="(((">&nbsp;(((&nbsp;</option> 
				<option value="((((">&nbsp;((((&nbsp;</option> 
				<option value="(((((">&nbsp;(((((&nbsp;</option>
			</select>
		</td>
		<td name="propertyName"></td>
		<td>
			<select name="operator" onchange="assembleCondition()">
				<option value="=">=</option>
				<option value="!=">!=</option>
				<option value="like">包含</option>
				<option value="not like">不包含</option>
				<option value="&gt;">&gt;</option>
				<option value="&gt;=">&gt;=</option>
				<option value="&lt;">&lt;</option>
				<option value="&lt;=">&lt;=</option>
				<!-- 
				<option value="not null">not null</option>
				<option value="null">null</option>
				 -->
			</select>		
		</td>
		<td name="fieldValue_view" align="left"></td>
		<td>
			<select name="parenthesisAfter" onchange="assembleCondition()">
				<option value=" "></option> 
				<option value=")">&nbsp;)&nbsp;</option> 
				<option value="))">&nbsp;))&nbsp;</option> 
				<option value=")))">&nbsp;)))&nbsp;</option> 
				<option value="))))">&nbsp;))))&nbsp;</option> 
				<option value=")))))">&nbsp;)))))&nbsp;</option>
			</select>
		</td>
		<td>
			<select name="logicalRel" onchange="assembleCondition()">
				<option value="and">并且</option>
				<option value="or">或者</option>
			</select>
		</td>
		<td class="buttonbox">
			<ul>
				<li>
					<a class="btn_sc" href="#" onclick="removeCondition(this)" title="删除这个条件项"></a>
				</li>
			</ul>
		</td>
	</tr>
	</tbody>
</table>

</body>
</html>
