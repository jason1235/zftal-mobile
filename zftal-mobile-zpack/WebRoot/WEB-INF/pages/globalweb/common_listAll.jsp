<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript">
		$(function(){
			initSelect("indexModel.gnmkdm","${indexModel.gnmkdm}");
			
		});
		</script>
	</head>
	<body>
		<div class="functionapply" style="height:auto">
        	<h2><span>添加应用</span><font style=" font-weight:normal; color:red;">（最多可应用10个常用图标）</font></h2>
			<div class="demo_list" style="width:84%">
			<div class="search" style="width:96.5%"><select name="indexModel.gnmkdm" id="parentId">
			<c:forEach var="cur" items="${topTypeResource}" begin="0" end="50" varStatus="vs">
			<option value="${cur.gnmkdm}">${cur.gnmkmc}</option>
            </c:forEach> 			
			</select><input type="text" name="indexModel.gnmkmc" value="${indexModel.gnmkmc }" id="label"><button onclick="doSearch();">搜索</button></div>
            <ul>  
			<%
			int k=1;
			String no;
			%>
			<c:forEach var="cur" items="${lstResource}" begin="0" end="178" varStatus="vs">
			<li style="width:31%">
			<%
			if(k==188){k=1;}
			if (k < 10)
			{
				no = "0" + k;
			}
			else
			{
				no = k + "";
			}
			%>
                	<p class="pic" style="width:20%;"><img src="<%=stylePath%>/images/blue/54/Function<%=no%>.png"></p>
                    <p class="con" style="width:30%"><em>${cur.gnmkmc}</em></p>
                    <p class="btn" id="used_${cur.gnmkdm}" style="display:none;width:35%" ><button onclick="cancelCommon('${cur.gnmkdm}','${cur.gnmkdm}');">取消</button><br><button disabled="disabled" class="cencel">已添加</button></p>
					<p class="btn" style="display:block;width:35%" id="use_${cur.gnmkdm}"  align="right"><button onclick="useCommon('${cur.gnmkdm}','${cur.gnmkdm}');">应用</button></p>
              </li>
			   <%
			  k++;
			  %>
			  
</c:forEach>                        
            </ul>
			</div>
        <!--功能列表-->
        <div class="chooselist" style="width:15%;height:auto" >
        	<h3><span>排序列表</span></h3>
			<div class="choosebtn"><button value="上移" class="chooseup" onclick="up();">上移</button><button value="下移" class="choosedown" onclick="down();">下移</button></div>
              <ul id="userCommonSortList">
			<%
			int i=0;
			%>			  
			<c:forEach var="cur" items="${userCommons}" begin="0" end="10" varStatus="vs"> 
			<%
			i++;
			%>
			  <li id="sortli_${vs.count}">
			  <input type="radio" name="sort" value="${vs.count}"  id="sort_${vs.count}"/>
			  <input type="hidden" name="resourceId" value="${cur.resourceId}">
			  <label>${cur.resourceName}</label> 
			  </li>
			</c:forEach>
              </ul>
					<button class="save" id="submitButton" style="margin-top:18px;margin-left:10px;width: 100px;border:1px solid #C6DAF4 !important; outline: 1px solid #4D93EA !important;color: #FFFFFF !important;">保存排序</button>
        </div>
        </div>          
	<script language="javascript">
		var maxnum=<%=i%>;
	jQuery(document).ready(function(){ 
<c:forEach var="cur" items="${userCommons}" begin="0" end="10" varStatus="vs"> 
		jQuery("#use_${cur.resourceId}").css('display','none');
		jQuery("#used_${cur.resourceId}").css('display','block');
</c:forEach>
	 });
	/*
	 取消应用
	*/
	function cancelCommon(sourceId,num){
	var usedId="#used_"+num;
	var useId="#use_"+num;
	 var params={};
	 var url="<%=request.getContextPath()%>/common/common_cancel.html?resourceId="+sourceId;
		 jQuery.post(
		 	    url,
		 	    params,
				function(data) {   
    				if (data.success) { 
	                   loadCommonsJson();
					   jQuery(useId).css('display','block');
					   jQuery(usedId).css('display','none');
	                  // alert("操作成功");
						}else {
							alert("操作失败，请与管理员联系!");
							return false;
						}                 
  					},
  					'json'
  				);
	}
	/*
	 使用应用
	*/
	function useCommon(sourceId,num){
	var usedId="#used_"+num;
	var useId="#use_"+num;
	if(maxnum>=10){
	alert("已将超过限定数量，不能再增加！");
	return false;
	}
	 var url="<%=request.getContextPath()%>/common/common_apply.html?resourceId="+sourceId;
		 jQuery.post(
		 	    url,
		 	    {},
				function(data) {   
    				if (data.success) { 
						jQuery(useId).css('display','none');
						jQuery(usedId).css('display','block');
						loadCommonsJson();
	                    //alert("操作成功,请重新登录！");
						}else{
							alert("操作失败，请与管理员联系!");
							return false;
						}                 
  					},
  					'json'
  				);

	}
/*
动态获取常用应用
*/
function loadCommonsJson(){
				 var url="<%=request.getContextPath()%>/common/common_getOneList.html";
				jQuery.post(
					url,
					{},
					function(data){
					   //alert(data);
					   var len = jQuery(data).length; 
					   maxnum=len;
					   var insertHtml="";
					   //已选岗位步骤添加（产生排序效果）
							for(var i=0;i<len;i++){
								var obj = jQuery(data);
								insertHtml=insertHtml+"<li><input type='radio' name='sort'/><input type='hidden' name='resourceId' value='"+obj[i]['resourceId']+"'><label>"+obj[i]['resourceName']+"</label></li>";
							}	
							//alert(insertHtml);
						jQuery("#userCommonSortList").html(insertHtml);
					},
					"json"
				);
			} 

	function up(){
	 var selectedDx;
	  var selectObj=document.getElementsByName("sort");
	  var len=String(selectObj.length);
	  var selected=0;
	  for(i=0;i<len;i++){ 
	   if(selectObj[i].checked){selectedDx=selectObj[i];selected=i;}
	  }
	  if(selectedDx){
	   if(selected==0){alert("已经置顶，无法操作！");return false;          }
  //取得两行的内容
    var nextContent=jQuery("#userCommonSortList li").get(selected-1).innerHTML;
    var currentContent=jQuery("#userCommonSortList li").get(selected).innerHTML;
    jQuery("#userCommonSortList li").get(selected).innerHTML=nextContent;
  //交换当前行与上一行内容
    jQuery("#userCommonSortList li").get(selected-1).innerHTML=currentContent; 
  //设置单选框选择
  	var selecto=document.getElementsByName("sort"); 
	selecto[selected-1].checked=true;
	   }else{
	   alert("请先选择具体常用功能！");
	   return false;}
	}
	function down(){
	  var selectedDx;
	  var selectObj=document.getElementsByName("sort");
	  var len=String(selectObj.length);
	  var selected=0;
	  for(i=0;i<len;i++){ 
	   if(selectObj[i].checked){selectedDx=selectObj[i];selected=i;}
	  }
	  if(selectedDx){
	   if(selected==maxnum||(selected+1)==maxnum){alert("已经置底，无法操作！");return false;      }
  //取得两行的内容
    var nextContent=jQuery("#userCommonSortList li").get(selected+1).innerHTML;
    var currentContent=jQuery("#userCommonSortList li").get(selected).innerHTML;
    jQuery("#userCommonSortList li").get(selected).innerHTML=nextContent;
  //交换当前行与上一行内容
    jQuery("#userCommonSortList li").get(selected+1).innerHTML=currentContent; 
  //设置单选框选择
  	var selecto=document.getElementsByName("sort"); 
	selecto[selected+1].checked=true;
	   }else{
	   alert("请先选择具体常用功能！");
	   return false;}
	   
	}
	jQuery("#submitButton").click(function(){ 
	   var url = "<%=request.getContextPath()%>/common/common_save.html";
	   var resourceIdObjArr=document.getElementsByName("resourceId");
	   var resourceIds;
	   for(i=0;i<resourceIdObjArr.length;i++){ 
	   	if(i==0){
		resourceIds=resourceIdObjArr[i].value;
		}else{
		resourceIds=resourceIds+","+resourceIdObjArr[i].value;
		}
	  }
	  var params={};
	  params['resourceIds']=resourceIds;
		 jQuery.post(
		 	    url,
		 	    params,
				function(data) {   
    				if (data.success) { 
	                     alert("保存成功");
						}else{
							alert("保存失败，请与管理员联系!");
							return false;
						}                 
  					},
  					'json'
  				);
	});	
	function doSearch(){
	 var label = jQuery("#label").val();
	 var parentId=jQuery("#parentId").val();	
	 var content = '<form id="form" method="post" target="framecon" action="<%=request.getContextPath()%>/common/common_listAll.html">';
		content +=' 	<input type="hidden" name="indexModel.gnmkdm" value="' +parentId+'" />';
		content +=' 	<input type="hidden" name="indexModel.gnmkmc" value="' +label+'" />';
		content +='    </form>';
		$('body').append(content);
		$('#form').submit();
		$('#form').remove();
	}
	</script>
		
	</body>
</html>
	
