<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini" %>
    <%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
    <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
    <script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xsxx/jcsj.js"></script>
  <script type="text/javascript">
  jQuery(function(){
		var newsGrid = new BjdmGrid();
		loadJqGrid("#tabGrid","#pager",newsGrid);
	});
	jQuery(function(){
		ancdBd_bjdm();
	});
  		
  </script>
  </head>
  
  <script type="text/javascript">

  </script>
  
  <body>
			<div class="tab_cur">
				<p class="location">
					<em>您的当前位置:</em><a>学生信息 - 基础数据初始化 - 学院专业班级数据维护 </a>
				</p>
			</div>
			<div class="comp_title">
		      <ul>
		        <li id="ha_bmdm"><a href="javascript:" onclick="contentSwitch('bmdm_id')"><span>部门</span></a></li>
		        <li id="ha_zydm"><a href="javascript:" onclick="contentSwitch('zydm')"><span>专业</span></a></li>
				<li id="ha_bjdm" class="ha"><a href="javascript:" onclick="contentSwitch('bjdm')"><span>班级</span></a></li>
		      </ul>
		    </div>
		    <!-- 班级数据维护 -->
		    <div id="xsxx_bm" class="xsxx_sjwh" style="display: block;" >
				<div class="toolbox">
					<!-- 加载当前菜单栏目下操作     -->
						<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
					<!-- 加载当前菜单栏目下操作 -->
	            </div> 
	            <div class="searchtab">
	            <s:form  action="/xsxx/bmdm_csJcsj.html?doType=query&yqbh=bjdm" theme="simple">
	            		
	            		<table width="100%" border="0">
							<tbody>
								<tr>
									<th>
										年级
									</th>
									<td>
										<s:textfield name="nj" id="search_nj" maxlength="15" cssStyle="width:120px"></s:textfield>
									</td>
									<th>
										学院
									</th>
									<td>
										<s:select name="bmdm_id" id="search_bmdm_id" list="bmdmModelList" listKey="bmdm_id" listValue="bmmc" cssStyle="width:154px"  headerKey="" headerValue=""></s:select>
									</td>
									<th>
										专业
									</th>
									<td>
										<s:select name="zydm" id="search_zydm" list="zydmModelList" listKey="zydm" listValue="zymc" cssStyle="width:154px"  headerKey="" headerValue=""></s:select> 
									</td>
								</tr>
								<tr>
									<th>
										班级代码
									</th>
									<td>
										<s:textfield name="bjdm" id="search_bjdm" maxlength="15" cssStyle="width:120px"></s:textfield>
									</td>
									<th>
										班级名称
									</th>
									<td>
										<s:textfield name="bjmc" id="search_bjmc" maxlength="15" cssStyle="width:120px"></s:textfield>
									</td>
									<th>
									 <div class="btn">
						                   <button type="button" id="search_go" onclick="searchResult_bjdm();return false;">
												查询
											</button>
						                 </div>
									</th>
									 <td>
						               
						              </td>
								</tr>
							</tbody>
						</table>
					</s:form>
	            	</div>
				<div class="formbox">
					<table id="tabGrid"></table>
					<div id="pager"></div>
				</div>
		</div>
		
		
		<script type="text/javascript">
			if('${result}'!=null && '${result}'!=''){
				alert('${result}');
			}
		</script>
 </body>
</html>
