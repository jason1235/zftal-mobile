<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/uicomm.js"></script>
  </head>
		<script type="text/javascript">
			//根据职工号加载用户已有角色信息,并显示选中状态
			function loadJsxx(){
				var cbvjsxxArray = $("input[name='cbvjsxx']");
				var jsdm = document.getElementById("jsdm").value;
				var data = jsdm.split(",");
					$.each(cbvjsxxArray,function(j,n) {
						for(var i=0;i<data.length;i++){
							if(data[i]==$(cbvjsxxArray[j]).attr("value")){
								cbvjsxxArray[j].checked = true;
							}
						}
					});
			}	
			// 设置数据授权
			function szSjsq(yh_id,js_id){
				var url="sjfw_szSjsq.html?yh_id="+yh_id+"&js_id="+js_id; 
				showWindow('设置数据授权',750,610,url);
			}
			
			// 全选			
			function checkAll(){
				var cbvjsxx = jQuery("input[name='cbvjsxx']");
				var allItem = jQuery('#allItem');
				if(jQuery(allItem).attr("checked")){
	 				jQuery(cbvjsxx).attr('checked',true);
				}else{
	 				jQuery(cbvjsxx).attr('checked',false); 
				}
			}
			
			//保存
			function saveForm(){
				var flag = false;
				var ids = document.getElementsByName("cbvjsxx");  
	            for (var j = 0; j < ids.length; j++) {  
	                if (ids.item(j).checked == true) {  
	                   flag = true;
	                   break; 
	                }  
	            }  
				
				if(flag){
					subForm('yhgl_szssjsSaveYh.html');
				}else{
					alert('请选择角色');
					return false;
				}
			}
			
			//查看更多数据范围
			function ckSj(jsdm){
			    var div_id = "id:"+jsdm;
				openTipsWindown("已授权范围",div_id,"500","200","true","","true","id")
			}
		</script>
 <body onload="loadJsxx();">
 <s:form method="post" theme="simple">
   <input type="hidden" name="doType" value="save"/>   
   <input type="hidden" id="jsdm" name="jsdm" value="${model.jsdm}"/>  
   <input type="hidden" id="zgh" name="zgh" value="${model.zgh}"/>
	<table width="100%" border="0" class="formlist">
	  <thead>
		<tr>
			<th colspan="4"><span>设置角色</span></th>
		</tr>
	  </thead> 
	  <tbody>
	  <tr>
	  	<th width="5%" class="title"><input type="checkbox" name="allItem" id="allItem" onclick="checkAll();"/></th>
	    <th width="15%" class="title">角色名称</th>
	    <th width="12%" class="title">数据授权</th>
	    <th width="68%" class="title">已授权范围</th>
	  </tr>
	  <s:iterator value="jsxxList" id="s" status="substa">
	   <tr>
	    <th style="text-align:center" >
	      <input type="checkbox" class="cbvclass" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="${s.jsdm}" />
	    </th>
	    <td>${s.jsmc}</td>
	    <td style="text-align:center"><a href="javascript:void(0);" onclick="szSjsq('${model.zgh}','${s.jsdm}')"><span style="text-decoration:underline;color:blue;">数据授权</span></a></td>
	    <td>
		  <s:if test="#s.sjfwzmc !=null && #s.sjfwzmc.length()> 48">
			 <s:property value="#s.sjfwzmc.substring(0,48)"/>...<a href="javascript:void(0);" onclick="ckSj('${s.jsdm}');return false;"><font style="color:blue;">查看更多>></font></a>
			<div id="${s.jsdm}" style="display: none">
				<div class="open_prompt">
					<table width="100%" border="0" class="table01">
						<tr>
							<td>
								${s.sjfwzmc}
							</td>
						</tr>
					</table>
				</div>
			</div>
		  </s:if>
		  <s:else>
			<s:property value="#s.sjfwzmc"/>
		  </s:else>
 	    </td>
	   </tr>
	  </s:iterator>
	  <tfoot>
		 <tr>
		    <td colspan="4">
		       <div class="btn">
		            <button onclick="saveForm();return false;" name="btn_tj">保 存</button>
					<button onclick="window.location.href='<%=jsPath %>/xtgl/yhgl_cxYhxx.html';return false;">返回</button>		            
		       </div>
		    </td>
		 </tr>
	  </tfoot>
	  </tbody>
	</table>
</s:form>
  <s:if test="result != null && result != ''">
  	<script>
  			alert('${result}','',{'clkFun':function(){refershParent()}});
  	</script>
  </s:if>
</body>
</html>