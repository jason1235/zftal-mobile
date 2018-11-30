<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl.js"></script>
		<script type="text/javascript">
			var jsdm="${jsdm}";	
		</script>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl_fpyh.js"></script>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript">

			//为select option 增加title属性提示
			jQuery(function(){
				var jsdm="${jsdm}";
				addOptionTitle();
				wfpGrid= new WfpGrid();
				loadJqGrid("#tabGrid","#pager",wfpGrid);

				yfpGrid=new YfpGrid();
				loadJqGrid("#yfpTabGrid",null,yfpGrid);

				jQuery("a[name='privillege']").live('click',function(){
					var id = jQuery(this).attr("id");
					showWindow('选择部门',600,400,'<%=request.getContextPath()%>/dataprivilege/deptFilter_load.html?deptFilter.userId='+id+'&deptFilter.roleId='+jsdm+'&viewType=user');
				});

			});
			
			function back(){
				
	            var doType  = jQuery('#doType').val();
	            var sffpyh  = jQuery('#sffpyh_qry').val();
				var jsmc  = jQuery('#jsmc_qry').val();
				var gnmkdm = jQuery('#gnmkdm_qry').val();
	            var content = '<form id="form3" method="post" action="jsgl_cxJsxx.html">';
	            content +='        <input type="hidden" name="doType" value="" />';
	            content +='        <input type="hidden" name="sffpyh" value="' + sffpyh + '" />';
	            content +='        <input type="hidden" name="jsmc" value="' + jsmc + '" />';
	            content +='        <input type="hidden" name="gnmkdm" value="' + gnmkdm + '" />';
	            content +='   </form>';
	            jQuery('body').append(content);
	            jQuery('#form3').submit();
	            jQuery('#form3').remove();
	        } 
			
		</script>
</head>

<s:form action="" method="post" theme="simple">
	<body >

     <input type="hidden" name="jsdm" value="${model.jsdm }"/>   
     <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }"/>
	 <div class="tab">
	  <table width="100%" class=" formlist" >
	        <tr>
	         <div class="toolbox">
				<div class="buttonbox">	
					<ul>
					<%--<li><a href="#" class="btn_zj" id="btn_zj" onclick="saveJsfpyhxx();return false;">保存</a></li>--%>
		          	<li><a href="#" class="btn_fh" id="btn_fh" onclick="back();">返回</a></li>
					</ul>
				</div> 
			</div>	
	      </tr>
	    <tbody>
	      <tr>
	        <th width="15%">角色名称</th>
	        <td width="85%">${model.jsmc }</td>
	      </tr>
	        <th>角色说明</th>
	        <td style="word-wrap:break-word">${model.jssm }</td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <div id="searchField">
		<input type="hidden" name="doType" id="doType" value="${model.doType }"/>
		<input type="hidden" name="sffpyh" id="sffpyh" value="${model.sffpyh }"/>
		<input type="hidden" name="jsmc" id="jsmc" value="${model.jsmc }"/>
		<input type="hidden" name="gnmkdm" id="gnmkdm" value="${model.gnmkdm }"/>
		
		<input type="hidden" name="sffpyh_qry" id="sffpyh_qry" value="${sffpyh_qry }"/>
		<input type="hidden" name="jsmc_qry" id="jsmc_qry" value="${jsmc_qry }"/>
		<input type="hidden" name="gnmkdm_qry" id="gnmkdm_qry" value="${gnmkdm_qry }"/>
  </div>
  <div class="searchtab">
	<s:form name="form" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
		<table width="100%" border="0" id="searchTab">
			<tbody>
				<tr>
					<th>用户名</th>
					<td>
						<input type="text" name="zgh" id="zgh"/>
					</td>
					<th>姓名</th>
					<td>
						<input type="text" name="xm" id="xm"/>
					</td>
					<td>
						<div class="btn">
							<button class="btn_cx" id="search_go" onclick="searchResult();return false;">查 询</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>
  </div>
  <div class="searchtab">
  	<table width="100%">
  		<thead>
  			<tr>
  				<td><span><font color="#0457A7">双击信息进行新增和删除</font></span></td>
  			</tr>
  		</thead>
  	</table>
  </div>
  <div style="width:800px">
	  <div  style="width:290px;float:left;">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
			<div width="10px;float:left;"></div>
		<div  style="width:500px;float:right">
				<table id="yfpTabGrid"></table>
		</div>
	</div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  		refreshParent($('#result').val());
  	</script>
  </s:if>
</body>
</s:form>
</html>