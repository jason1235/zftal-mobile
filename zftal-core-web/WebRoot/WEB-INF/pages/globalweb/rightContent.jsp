<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
</head>

<s:form action="#"  theme="simple">
	<body>
            <div class="toolbox">
            	<div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								<th>
									新闻标题
								</th>
								<td>
									<s:textfield id="bt" name="bt" cssStyle="width:120px;" maxlength="100" onkeydown=""></s:textfield>
								</td>
								<th>
									发布人
								</th>
								<td>
									<s:textfield id="fbrxm" name="fbrxm" cssStyle="width:120px;" maxlength="100" onkeydown=""></s:textfield>
								</td>
								<th>
									发布时间
								</th>
								<td>
									<s:textfield id="fbkssj" name="fbkssj" cssStyle="width:80px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></s:textfield> -- 
									<s:textfield id="fbjssj" name="fbjssj" cssStyle="width:80px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></s:textfield>
								</td>
								 <td>
					                <div class="btn">
					                   <button type="button" id="search_go" onclick="">
											查询
										</button>
					                 </div>
					              </td>
							</tr>
						</tbody>
					</table>
            	</div>
            </div> 
            
            <div class="formbox">
				<h3 class="datetitle_01">
					<span> 新闻公告&nbsp;&nbsp; 
						<s:if
							test="tbodydata == null || tbodydata.size() <=0">
							<font color="red">未找到任何记录！</font>
						</s:if> 
					</span>
				</h3>
				
			</div>
	 
</body>
</s:form>
</html>