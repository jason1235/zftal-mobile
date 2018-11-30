<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
  </head>
		<script type="text/javascript">
			$(function(){
				$("a[name='privillege']").click(function(){
					var id = $(this).closest("tr").attr("id");
					showWindow('选择部门',500,300,'<%=request.getContextPath()%>/dataprivilege/deptFilter_load.html?deptFilter.userId=${model.zgh}&deptFilter.roleId='+id+'&viewType=role');
				})
			})
		
			//根据职工号加载用户已有角色信息,并显示选中状态
			function loadJsxx(){
				var cbvjsxxArray = $("tr[name='jsxxtr']");
				var jsdm = document.getElementById("jsdm").value;
				var data = jsdm.split(",");
					$.each(cbvjsxxArray,function(j,n) {
						for(var i=0;i<data.length;i++){
							if(data[i]==$(cbvjsxxArray[j]).attr("id")){
								$(cbvjsxxArray[j]).css("display","");
								getSjfw(data[i]);
							}
						}
					});
			}	

			function getSjfw(jsdm){
				$.post( "<%=request.getContextPath() %>/dataprivilege/deptFilter_getOrgsTextForRow.html",{"deptFilter.userId":"${model.zgh}","deptFilter.roleId":jsdm},function(data){
	    	 		$("#sjfw_"+jsdm).html(data.simple);
	    	 		$("#sjfw_"+jsdm).attr("name",data.whole);
	    	 		$("#sjfw_"+jsdm).mousemove(function(){
	    	 			datatips($(this));
		    	 	})
	    	    });
			}
			function datatips(obj){
				var x = 0;  //设置偏移量
				var y = 20;
				var padding_right = 0;
				var t = jQuery(obj);
				var l = 100;
				t.mouseover(function(e){
				    var datatip = "<div id=\"datatip\" style=\"width:200px;z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
				    var tip = jQuery(datatip);
				    var data = jQuery(obj).attr("name");
				    jQuery(tip).append(data);
				    jQuery("body").append(tip);	//把它追加到文档中
					l = jQuery(tip).outerWidth();
					jQuery("#datatip")
						.css({
							"top": (e.pageY+y) + "px",
							"left": checkX(e.pageX)  + "px"
						}).show("fast");	  //设置x坐标和y坐标，并且显示
			    });
				t.mouseout(function(){		
					jQuery("#datatip").remove();   //移除 
			    });
			    t.mousemove(function(e){
			    	jQuery("#datatip")
						.css({
							"top": (e.pageY+y) + "px",
							"left": checkX(e.pageX)  + "px"
						});
				});
			    
			    function checkX(mouseX){
			    	var width = jQuery(document).width();
			    	var border = width-l-x-padding_right;
			    	if(mouseX+x<border){
			    		return mouseX+x;
			    	}else{
			    		return mouseX-l;
			    	}
			    }
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
	    <th width="20%" class="title">角色名称</th>
	    <th width="68%" class="title">已授权范围</th>
	    <th width="12%" class="title">数据授权</th>
	  </tr>
	  <s:iterator value="jsxxList" id="s" status="substa">
	   <tr name="jsxxtr" id="${s.jsdm}" style="display:none">
	    <td>${s.jsmc}</td>
	    <td id="sjfw_${s.jsdm }"></td>
	    <td style="text-align:center">
	    	<a href="#" name='privillege' style='color:blue;text-decoration:underline;'>数据授权</a>
	    </td>
	   </tr>
	  </s:iterator>
	  <tfoot>
		 <tr>
		    <td colspan="4">
		       <div class="btn">
		            <%--<button onclick="saveForm();return false;" name="btn_tj">保 存</button>--%>
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