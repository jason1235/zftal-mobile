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
				loadJqGrid("#yfpTabGrid","#pagerYfp",yfpGrid);
				
			});
		</script>
</head>

	<body >
	 <div class="tab">
	  <table width="100%" class=" formlist" >
	        <tr>
	         <div class="toolbox">
				<div class="buttonbox">	
					<ul>
					<li><a href="#" class="btn_zj" id="btn_zj" onclick="saveJsfpyhxx();return false;">保存</a></li>
		          	<li><a href="#" class="btn_fh" id="btn_fh" onclick="window.location.href='jsgl_cxJsxx.html';return false;">返回</a></li>
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
	      <tr>
	      	<td colspan="2" >
	      		
	      		<div class="searchtab">
				<s:form name="form" method="post" action="/xtgl/yhgl_cxYhxx.html" theme="simple">
				 <input type="hidden" name="jsdm" value="${model.jsdm }"/>   
     			 <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }"/>
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
									<button class="btn_cx" id="search_go"
										onclick="searchResult();return false;">
										查 询
									</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</s:form>
			</div>
	      	</td>
	      </tr>
	    </tbody>
	  </table>
  </div>
  <div style="height: 30px;padding-top: 10px;font-size: 14px;color: red;">说明：可双击"未分配用户列表"中的记录至"已分配用户列表"</div>  
  <div style="width:800px">
	  <div  style="width:450px;float:left;">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
			<div width="10px;float:left;"></div>
		<div  style="width:300px;float:right;padding-right: 15px;">
				<table id="yfpTabGrid"></table>
				<div id="pagerYfp"></div>
		</div>
	</div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  		refreshParent($('#result').val());
  	</script>
  </s:if>
</body>
</html>